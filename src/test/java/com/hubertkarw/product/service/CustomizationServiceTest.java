package com.hubertkarw.product.service;

import com.hubertkarw.product.exception.ProductAppException;
import com.hubertkarw.product.exception.ProductNotFoundException;
import com.hubertkarw.product.mapper.CustomizationMapper;
import com.hubertkarw.product.mapper.CustomizationMapperImpl;
import com.hubertkarw.product.model.Customization;
import com.hubertkarw.product.model.CustomizationCreateDTO;
import com.hubertkarw.product.model.CustomizationDTO;
import com.hubertkarw.product.repository.CustomizationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomizationServiceTest {
    CustomizationService service;
    CustomizationRepository repository;
    CustomizationMapper mapper = new CustomizationMapperImpl();

    @BeforeEach
    void setup() {
        this.repository = Mockito.mock(CustomizationRepository.class);
        this.service = new CustomizationService(repository, mapper);
    }

    @Test
    void getCustomizationsWithNoType_customizationsExist_customizationsReturned() {
        //given
        List<Customization> customizations = List.of(
                new Customization(1L, "Red", "color", BigDecimal.ZERO, null),
                new Customization(2L, "Blue", "color", BigDecimal.ZERO, null),
                new Customization(3L, "Black", "color", BigDecimal.ZERO, null)
        );
        when(repository.findAll()).thenReturn(customizations);
        //when
        List<CustomizationDTO> result = service.getCustomizations(null);
        //then
        assertAll(
                ()->assertEquals(3, result.size()),
                ()->assertEquals(1L, result.get(0).getId()),
                ()->assertEquals(2L, result.get(1).getId()),
                ()->assertEquals(3L, result.get(2).getId()),
                ()->assertEquals("Red", result.get(0).getName()),
                ()->assertEquals("Blue", result.get(1).getName()),
                ()->assertEquals("Black", result.get(2).getName()),
                ()->assertEquals("color", result.get(0).getType()),
                ()->assertEquals("color", result.get(1).getType()),
                ()->assertEquals("color", result.get(2).getType()),
                ()->assertEquals(BigDecimal.ZERO, result.get(0).getPrice()),
                ()->assertEquals(BigDecimal.ZERO, result.get(1).getPrice()),
                ()->assertEquals(BigDecimal.ZERO, result.get(2).getPrice())

        );
    }

    @Test
    void getCustomizationsWithType_customizationsExist_customizationsReturned() {
        //given
        List<Customization> customizations = List.of(
                new Customization(1L, "Red", "color", BigDecimal.ZERO, null),
                new Customization(2L, "Blue", "color", BigDecimal.ZERO, null),
                new Customization(3L, "Black", "color", BigDecimal.ZERO, null)
        );
        when(repository.findByType("color")).thenReturn(customizations);
        //when
        List<CustomizationDTO> result = service.getCustomizations("color");
        //then
        assertAll(
                ()->assertEquals(3, result.size()),
                ()->assertEquals(1L, result.get(0).getId()),
                ()->assertEquals(2L, result.get(1).getId()),
                ()->assertEquals(3L, result.get(2).getId()),
                ()->assertEquals("Red", result.get(0).getName()),
                ()->assertEquals("Blue", result.get(1).getName()),
                ()->assertEquals("Black", result.get(2).getName()),
                ()->assertEquals("color", result.get(0).getType()),
                ()->assertEquals("color", result.get(1).getType()),
                ()->assertEquals("color", result.get(2).getType()),
                ()->assertEquals(BigDecimal.ZERO, result.get(0).getPrice()),
                ()->assertEquals(BigDecimal.ZERO, result.get(1).getPrice()),
                ()->assertEquals(BigDecimal.ZERO, result.get(2).getPrice())

        );
    }

    @Test
    void getCustomization_customizationExist_customizationReturned(){
        //given
        Customization customization = new Customization(1L, "Red", "color", BigDecimal.ZERO, null);
        when(repository.findById(1L)).thenReturn(Optional.of(customization));
        //when
        CustomizationDTO result = service.getCustomization(1L);
        //then
        assertAll(
                ()->assertEquals(customization.getId(), result.getId()),
                ()->assertEquals(customization.getName(), result.getName()),
                ()->assertEquals(customization.getType(), result.getType()),
                ()->assertEquals(customization.getPrice(), result.getPrice()),
                ()->assertEquals(customization.getProductId(), result.getProductId())
        );
    }

    @Test
    void getCustomization_customizationNotExist_thrownException() {
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());
        //then
        ProductAppException exception = Assertions.assertThrows(ProductAppException.class, ()-> service.getCustomization(1L));
        assertEquals("customization not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void addCustomization_validCustomization_customizationAdded(){
        //given
        Customization customizationSaved = new Customization(1L, "Red", "color", BigDecimal.ZERO, null);
        CustomizationCreateDTO customization = new CustomizationCreateDTO("Red", "color", BigDecimal.ZERO);
        when(repository.save(any())).thenReturn(customizationSaved);
        //when
        CustomizationDTO result = service.addCustomization(customization);
        //then
        assertEquals(result.getId(), customizationSaved.getId());
        assertEquals(result.getName(), customizationSaved.getName());
        assertEquals(result.getType(), customizationSaved.getType());
        assertEquals(result.getPrice(), customizationSaved.getPrice());
        assertEquals(result.getProductId(), customizationSaved.getProductId());
    }

    @Test
    void removeCustomization_customizationExists_customizationRemoved(){
        //given
        Customization customization = new Customization(1L, "Red", "color", BigDecimal.ZERO, null);
        when(repository.findById(any())).thenReturn(Optional.of(customization));
        //when
        service.deleteCustomization(customization.getId());
        //then
        verify(repository).delete(customization);
    }

    @Test
    void removeCustomization_customizationNotExists_throwException(){
        //given
        when(repository.findById(any())).thenReturn(Optional.empty());
        //then
        ProductAppException exception = Assertions.assertThrows(ProductAppException.class, ()-> service.getCustomization(1L));
        assertEquals("customization not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void updateCustomization_customizationExists_customizationUpdated(){
        //given
        Customization oldCustomization = new Customization(1L, "Blue", "color", BigDecimal.ZERO, null);
        Customization updatedCustomization = new Customization(1L, "Red", "color", BigDecimal.ZERO, null);
        CustomizationCreateDTO customization = new CustomizationCreateDTO("Red", "color", BigDecimal.ZERO);
        when(repository.findById(any())).thenReturn(Optional.of(oldCustomization));
        when(repository.save(any())).thenReturn(updatedCustomization);
        //when
        CustomizationDTO result = service.updateCustomization(1L, customization);
        //then
        assertEquals(updatedCustomization.getName(),result.getName());
        assertEquals(updatedCustomization.getType(),result.getType());
        assertEquals(updatedCustomization.getPrice(),result.getPrice());

    }

    @Test
    void updateCustomization_customizationNotExists_throwException(){
        //given
        CustomizationCreateDTO customization = new CustomizationCreateDTO("Red", "color", BigDecimal.ZERO);
        when(repository.findById(any())).thenReturn(Optional.empty());
        //when
        //then
        ProductAppException exception = Assertions.assertThrows(ProductAppException.class, ()-> service.updateCustomization(1L,customization));
        assertEquals("customization not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

}
