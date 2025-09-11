package com.hubertkarw.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemRequest {
    private String name;
    private String type;
    private BigDecimal price;
    private List<CartItemCustomizationRequest> customizations;
}
