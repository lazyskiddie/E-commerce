package com.ecom.OrderService.Services;

import com.ecom.OrderService.Models.Order;
import com.ecom.OrderService.Models.OrderItem;
import com.ecom.OrderService.Models.OrderStatus;
import com.ecom.OrderService.Repositories.OrderRepository;
import com.ecom.OrderService.dto.CartItemDTO;
import com.ecom.OrderService.dto.OrderItemDTO;
import com.ecom.OrderService.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public Optional<OrderResponse> createOrder(String userId) {
        // Fetch cart items from CartService via HTTP (update port if CartService runs on a different port)
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-User-ID", userId);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<List<CartItemDTO>> cartResponse = restTemplate.exchange(
                "http://localhost:8083/api/cart",
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<CartItemDTO>>() {}
        );

        List<CartItemDTO> cart = cartResponse.getBody();
        if (cart == null || cart.isEmpty()) {
            return Optional.empty();
        }
        BigDecimal totalPrice = cart.stream()
                .map(CartItemDTO::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cart.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProductId(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                ))
                .toList();
        order.setItem(orderItems);
        Order savedOrder = orderRepository.save(order);

        OrderResponse response = new OrderResponse();
        response.setId(savedOrder.getId());
        response.setTotalAmount(savedOrder.getTotalAmount());
        response.setStatus(savedOrder.getStatus());
        response.setCreatedAt(savedOrder.getCreatedAt());

        List<OrderItemDTO> itemDTOs = savedOrder.getItem().stream().map(oi -> {
            OrderItemDTO dto = new OrderItemDTO();
            dto.setId(oi.getId());
            dto.setProductId(oi.getProductId());
            dto.setQuantity(oi.getQuantity());
            dto.setPrice(oi.getPrice());
            return dto;
        }).toList();
        response.setItems(itemDTOs);
        return Optional.of(response);
    }
}
