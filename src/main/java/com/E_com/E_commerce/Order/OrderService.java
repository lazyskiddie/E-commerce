package com.E_com.E_commerce.Order;

import com.E_com.E_commerce.Cart.Cart;
import com.E_com.E_commerce.Cart.CartService;
import com.E_com.E_commerce.Order.dto.OrderResponse;
import com.E_com.E_commerce.User.User;
import com.E_com.E_commerce.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.E_com.E_commerce.Order.dto.OrderItemDTO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(String userId) {
        List<Cart> cart = cartService.getCart(userId);
        if(cart.isEmpty()) {
            return Optional.empty();
        }


        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if(userOptional.isEmpty()){
            return Optional.empty();
        }
        User user = userOptional.get();

        BigDecimal totalPrice = cart.stream()
                .map(Cart::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cart.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProduct(),
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
            dto.setProductId(oi.getProduct().getId());
            dto.setQuantity(oi.getQuantity());
            dto.setPrice(oi.getPrice());
            return dto;
        }).toList();

        response.setItems(itemDTOs);

        return Optional.of(response);
    }
}
