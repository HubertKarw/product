package com.hubertkarw.product.mapper;

import com.hubertkarw.product.model.Customization;
import com.hubertkarw.product.model.CustomizationDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomizationMapper {
    CustomizationDTO toDTO(Customization customization);
    Customization toEntity(CustomizationDTO customizationDTO);
    List<CustomizationDTO> toDTOList(List<Customization> customizations);
    List<Customization> toEntityList(List<CustomizationDTO> customizationsDTO);
}
