package com.hubertkarw.product.service;

import com.hubertkarw.product.exception.ProductAppException;
import com.hubertkarw.product.mapper.ProductMapper;
import com.hubertkarw.product.model.Product;
import com.hubertkarw.product.model.ProductCreateDTO;
import com.hubertkarw.product.model.ProductDTO;
import com.hubertkarw.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final ProductMapper mapper;

    public List<ProductDTO> getProducts(String type) {
        if (type == null) {
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

    public ProductDTO updateProduct(Long id, ProductCreateDTO productDTO) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductAppException("product not found", HttpStatus.NOT_FOUND));
        product.updateProduct(productDTO);
        return mapper.toDTO(repository.save(product));
    }

    public void deleteProduct(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductAppException("product not found", HttpStatus.NOT_FOUND));
        repository.delete(product);
    }
}
