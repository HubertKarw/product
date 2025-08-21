package com.hubertkarw.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PRODUCT")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String type;
    private BigDecimal price;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "PRODUCT_CUSTOMIZATION",
            joinColumns = @JoinColumn(name = "PRODUCT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CUSTOMIZATION_ID"))
    private List<Customization> customizations;

    public void updateProduct(ProductCreateDTO updatedProduct) {
        this.name = updatedProduct.getName();
        this.type = updatedProduct.getType();
        this.price = updatedProduct.getPrice();
    }
}
