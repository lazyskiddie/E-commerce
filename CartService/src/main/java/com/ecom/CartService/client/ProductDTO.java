package com.ecom.CartService.client;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer blockQuantity;
    private Boolean active;
}
