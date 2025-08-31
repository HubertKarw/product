package com.hubertkarw.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubertkarw.product.model.Customization;
import com.hubertkarw.product.model.CustomizationCreateDTO;
import com.hubertkarw.product.model.CustomizationDTO;
import com.hubertkarw.product.service.CustomizationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomizationControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockitoBean
    CustomizationService service;

    @Test
    void shouldGetCustomizations() throws Exception {
        List<CustomizationDTO> customizations = List.of(
                new CustomizationDTO(1L, "Red", "color", BigDecimal.ZERO, null),
                new CustomizationDTO(2L, "Blue", "color", BigDecimal.ZERO, null),
                new CustomizationDTO(3L, "Black", "color", BigDecimal.ZERO, null)
        );

        when(service.getCustomizations(null)).thenReturn(customizations);

        mockMvc.perform(MockMvcRequestBuilders.get("/customs"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Red"))
                .andExpect(jsonPath("$[1].name").value("Blue"))
                .andExpect(jsonPath("$[2].name").value("Black"));
    }

    @Test
    void shouldGetCustomization() throws Exception {
        CustomizationDTO customization = new CustomizationDTO(1L, "Red", "color", BigDecimal.ZERO, null);

        when(service.getCustomization(any())).thenReturn(customization);

        mockMvc.perform(MockMvcRequestBuilders.get("/customs/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Red"))
                .andExpect(jsonPath("$.type").value("color"))
                .andExpect(jsonPath("$.price").value(BigDecimal.ZERO))
                .andExpect(jsonPath("$.productId").isEmpty());
    }

    @Test
    void shouldAddCustomization() throws Exception {
        CustomizationCreateDTO customizationCreateDTO = new CustomizationCreateDTO("Red", "color", BigDecimal.ZERO);
        CustomizationDTO customization = new CustomizationDTO(1L, "Red", "color", BigDecimal.ZERO, null);

        when(service.addCustomization(any())).thenReturn(customization);

        mockMvc.perform(MockMvcRequestBuilders.post("/customs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(customizationCreateDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Red"))
                .andExpect(jsonPath("$.type").value("color"))
                .andExpect(jsonPath("$.price").value(BigDecimal.ZERO))
                .andExpect(jsonPath("$.productId").isEmpty());
    }

    @Test
    void shouldUpdateCustomization() throws Exception {
        CustomizationCreateDTO modifyCustomizationCreateDTO = new CustomizationCreateDTO("Red", "color", BigDecimal.ZERO);
        CustomizationDTO modifyCustomization = new CustomizationDTO(1L, "Red", "color", BigDecimal.ZERO, null);

        when(service.updateCustomization(eq(1L), any())).thenReturn(modifyCustomization);

        mockMvc.perform(MockMvcRequestBuilders.put("/customs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(modifyCustomizationCreateDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Red"))
                .andExpect(jsonPath("$.type").value("color"))
                .andExpect(jsonPath("$.price").value(BigDecimal.ZERO))
                .andExpect(jsonPath("$.productId").isEmpty());

    }

    @Test
    void shouldDeleteCustomization() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/customs/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(service).deleteCustomization(1L);
    }
}
