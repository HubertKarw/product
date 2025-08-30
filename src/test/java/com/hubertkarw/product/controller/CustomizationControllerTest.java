package com.hubertkarw.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubertkarw.product.model.CustomizationDTO;
import com.hubertkarw.product.service.CustomizationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

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
                new CustomizationDTO(1l,"Red","color", BigDecimal.ZERO,null),
                new CustomizationDTO(2l,"Blue","color", BigDecimal.ZERO,null),
                new CustomizationDTO(3l,"Black","color", BigDecimal.ZERO,null)
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
}
