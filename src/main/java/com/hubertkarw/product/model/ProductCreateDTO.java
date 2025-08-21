package com.hubertkarw.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateDTO {
    private String name;
    private String type;
    private BigDecimal price;
    private List<CustomizationCreateDTO> customizations;
}
