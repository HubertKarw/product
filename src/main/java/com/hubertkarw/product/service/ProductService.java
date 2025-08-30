package com.hubertkarw.product.service;

import com.hubertkarw.product.exception.CustomizationNotFountException;
import com.hubertkarw.product.exception.ProductNotFoundException;
import com.hubertkarw.product.mapper.CustomizationMapper;
import com.hubertkarw.product.mapper.ProductMapper;
import com.hubertkarw.product.model.*;
import com.hubertkarw.product.repository.CustomizationRepository;
import com.hubertkarw.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository repository;
    private final CustomizationRepository customizationRepository;
    private final ProductMapper mapper;
    private final CustomizationMapper customizationMapper;

    public List<ProductDTO> getProducts(String type) {
        return type==null?mapper.toDTOList(repository.findAll()):mapper.toDTOList(repository.findByType(type));
    }
    public ProductDTO getProduct(Long id){
        Product product = repository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("product not found"));
        return mapper.toDTO(product);
    }

    @Transactional
    public ProductDTO addProduct(ProductCreateDTO productDTO) {
        return mapper.toDTO(repository.save(mapper.toEntity(productDTO)));
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductCreateDTO productDTO) {
        Product product = repository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("product not found"));

        product.updateProduct(productDTO);
        return mapper.toDTO(repository.save(product));
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("product not found"));
        repository.delete(product);
    }
    public List<CustomizationDTO> getProductCustomizations(Long id){
        Product product = repository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("product not found"));;
        return customizationMapper.toDTOList(product.getCustomizations());
    }

    @Transactional
    public ProductDTO addProductCustomization(Long productID, Long customizationId){
        Product product = repository.findById(productID)
                .orElseThrow(()-> new ProductNotFoundException("product not found"));
        Customization customization = customizationRepository.findById(customizationId)
                .orElseThrow(()-> new CustomizationNotFountException("customization not found"));
        product.getCustomizations().add(customization);
        return mapper.toDTO(repository.save(product));
    }

    @Transactional
    public void deleteProductCustomization(Long productID, Long customizationId){
        Product product = repository.findById(productID)
                .orElseThrow(()-> new ProductNotFoundException("product not found"));
        Customization customization = customizationRepository.findById(customizationId)
                .orElseThrow(()-> new CustomizationNotFountException("customization not found"));
        product.getCustomizations().remove(customization);
        repository.save(product);
    }

    @Transactional
    public ProductDTO assignCustomizationAsProduct(Long customizationId){
        Customization customization = customizationRepository.findById(customizationId)
                .orElseThrow(()-> new CustomizationNotFountException("customization not found"));
        ProductCreateDTO product = mapper.toProductCreateDTO(customization);
       return mapper.toDTO(repository.save(mapper.toEntity(product)));
    }
}
