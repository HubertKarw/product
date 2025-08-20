package com.hubertkarw.product.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "CUSTOMIZATION")
public class Customization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String type;
    private BigDecimal price;
}
