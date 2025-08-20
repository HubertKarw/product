package com.hubertkarw.product.mapper;

import com.hubertkarw.product.model.Product;
import com.hubertkarw.product.model.ProductCreateDTO;
import com.hubertkarw.product.model.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(ProductCreateDTO productDTO);
}
