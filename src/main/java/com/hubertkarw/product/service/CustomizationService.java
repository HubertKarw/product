package com.hubertkarw.product.service;

import com.hubertkarw.product.exception.CustomizationNotFountException;
import com.hubertkarw.product.mapper.CustomizationMapper;
import com.hubertkarw.product.model.Customization;
import com.hubertkarw.product.model.CustomizationCreateDTO;
import com.hubertkarw.product.model.CustomizationDTO;
import com.hubertkarw.product.repository.CustomizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomizationService {
    private final CustomizationRepository repository;
    private final CustomizationMapper mapper;

    public List<CustomizationDTO> getCustomizations(String type) {
        return type==null?mapper.toDTOList(repository.findAll()):mapper.toDTOList(repository.findByType(type));
    }

    public CustomizationDTO getCustomization(Long id) {
        Customization customization = repository.findById(id)
                .orElseThrow(()-> new CustomizationNotFountException("customization not found"));
        return mapper.toDTO(customization);
    }

    public CustomizationDTO addCustomization(CustomizationCreateDTO customizationCreateDTO) {
        return mapper.toDTO(repository.save(mapper.toEntity(customizationCreateDTO)));
    }

    public CustomizationDTO updateCustomization(Long id, CustomizationCreateDTO customizationCreateDTO) {
        Customization customization = repository.findById(id)
                .orElseThrow(()-> new CustomizationNotFountException("customization not found"));
        customization.updateCustomization(customizationCreateDTO);
        return mapper.toDTO(repository.save(customization));
    }

    public void deleteCustomization(Long id) {
        Customization customization = repository.findById(id)
                .orElseThrow(()-> new CustomizationNotFountException("customization not found"));
        repository.delete(customization);
    }
}
