package com.hubertkarw.product.service;

import com.hubertkarw.product.mapper.ProductMapper;
import com.hubertkarw.product.model.ProductCreateDTO;
import com.hubertkarw.product.model.ProductDTO;
import com.hubertkarw.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public List<ProductDTO> getProducts(String type) {
        if(type==null){
            return repository.findAll().stream()
                    .map(mapper::toDTO)
                    .toList();
        }
        return repository.findByType(type).stream()
                .map(mapper::toDTO)
                .toList();
    }

    public ProductDTO addProduct(ProductCreateDTO productDTO) {
        return mapper.toDTO(repository.save(mapper.toEntity(productDTO)));
    }
}
