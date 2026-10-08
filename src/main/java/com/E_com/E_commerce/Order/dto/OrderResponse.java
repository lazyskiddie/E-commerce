package com.E_com.E_commerce.Order.dto;

import com.E_com.E_commerce.Order.OrderItem;
import com.E_com.E_commerce.Order.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;
}
