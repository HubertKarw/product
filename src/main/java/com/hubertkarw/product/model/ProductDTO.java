package com.hubertkarw.product.model;

import java.math.BigDecimal;
import java.util.List;

public class ProductDTO {
    private String name;
    private String type;
    private BigDecimal price;
    private List<CustomizationDTO> customizations;
}
