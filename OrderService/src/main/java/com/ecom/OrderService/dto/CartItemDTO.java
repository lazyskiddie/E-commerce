package com.ecom.OrderService.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDTO {
    private Long id;
    private String userId;
    private String productId;
    private Integer quantity;
    private BigDecimal price;
}
