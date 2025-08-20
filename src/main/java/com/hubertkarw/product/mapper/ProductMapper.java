package com.hubertkarw.product.mapper;

import com.hubertkarw.product.model.Product;
import com.hubertkarw.product.model.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDto(Product product);
    Product toEntity(ProductDTO productDTO);
}
