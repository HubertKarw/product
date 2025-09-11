package com.hubertkarw.product.mapper;

import com.hubertkarw.product.model.Customization;
import com.hubertkarw.product.model.Product;
import com.hubertkarw.product.model.ProductCreateDTO;
import com.hubertkarw.product.model.ProductDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(ProductCreateDTO productDTO);
    List<ProductDTO> toDTOList(List<Product> products);
    List<Product> toEntityList(List<ProductDTO> productsDTO);
    ProductCreateDTO toProductCreateDTO(Customization customization);
}
