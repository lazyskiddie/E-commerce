package com.ecom.OrderService.Services;

import com.ecom.OrderService.Kafka.OrderEventPublisher;
import com.ecom.OrderService.Models.Order;
import com.ecom.OrderService.Models.OrderItem;
import com.ecom.OrderService.Models.OrderStatus;
import com.ecom.OrderService.Repositories.OrderRepository;
import com.ecom.OrderService.client.CartClient;
import com.ecom.OrderService.dto.CartItemDTO;
import com.ecom.OrderService.dto.OrderItemDTO;
import com.ecom.OrderService.dto.OrderResponse;
import com.ecom.OrderService.events.InventoryResultEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartClient cartClient;
    private final OrderEventPublisher eventPublisher;
    private final TransactionTemplate transactionTemplate;

    /**
     * Step 1 of the order saga: store the order as PENDING and announce it on Kafka.
     * ProductService reserves the stock and answers on "inventory.result" (see handleInventoryResult).
     * The HTTP call to CartService is made outside the DB transaction on purpose.
     */
    public Optional<OrderResponse> createOrder(String userId) {
        List<CartItemDTO> cart = cartClient.getCart(userId);
        if (cart == null || cart.isEmpty()) {
            return Optional.empty();
        }

        // BUG FIX: the old code summed the unit prices and ignored quantity (3 x $10 was billed as $10)
        BigDecimal totalPrice = cart.stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.PENDING); // CONFIRMED only after stock is really reserved
        order.setTotalAmount(totalPrice);
        order.setItems(cart.stream()
                .map(item -> new OrderItem(null, item.getProductId(), item.getQuantity(), item.getPrice(), order))
                .toList());

        Order saved = transactionTemplate.execute(tx -> orderRepository.save(order));

        // sent only after the DB commit, so consumers can never see an order that does not exist yet
        eventPublisher.publishOrderCreated(saved);
        return Optional.of(toResponse(saved));
    }

    // Step 2 of the saga: ProductService told us whether the stock could be reserved.
    public void handleInventoryResult(InventoryResultEvent event) {
        OrderStatus target = event.success() ? OrderStatus.CONFIRMED : OrderStatus.CANCELLED;

        Order updated = transactionTemplate.execute(tx -> {
            Order order = orderRepository.findById(event.orderId()).orElse(null);
            if (order == null) {
                log.warn("inventory.result for unknown order {}", event.orderId());
                return null;
            }
            if (order.getStatus() == OrderStatus.PENDING) {
                order.setStatus(target);
                return orderRepository.save(order);
            }
            // duplicate delivery: same outcome again is fine (re-announce it), a conflicting one is ignored
            return order.getStatus() == target ? order : null;
        });

        if (updated != null) {
            eventPublisher.publishOrderStatus(updated);
        }
    }

    public Optional<OrderResponse> getOrder(String userId, Long orderId) {
        return orderRepository.findByIdAndUserId(orderId, userId).map(this::toResponse);
    }

    public List<OrderResponse> getOrders(String userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(this::toResponse).toList();
    }

    private OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setTotalAmount(order.getTotalAmount());
        response.setStatus(order.getStatus());
        response.setCreatedAt(order.getCreatedAt());
        response.setItems(order.getItems().stream().map(oi -> {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setId(oi.getId());
            dto.setProductId(oi.getProductId());
            dto.setQuantity(oi.getQuantity());
            dto.setPrice(oi.getPrice());
            return dto;
        }).toList());
        return response;
    }
}

