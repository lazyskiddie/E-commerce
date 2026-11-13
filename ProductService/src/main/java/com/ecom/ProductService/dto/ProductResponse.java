package com.E_com.E_commerce.Product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String category;
    private String price;
    private String blockQuantity;
    private String imageurl;
    private Boolean active;
}
