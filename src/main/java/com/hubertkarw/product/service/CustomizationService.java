package com.hubertkarw.product.service;

import com.hubertkarw.product.mapper.CustomizationMapper;
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
        if(type==null){
            return repository.findAll().stream()
                    .map(mapper::toDTO)
                    .toList();
        }
        return repository.findByType(type).stream()
                .map(mapper::toDTO)
                .toList();
    }
}
