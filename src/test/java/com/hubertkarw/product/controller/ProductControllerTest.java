package com.hubertkarw.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubertkarw.product.model.CustomizationDTO;
import com.hubertkarw.product.model.ProductCreateDTO;
import com.hubertkarw.product.model.ProductDTO;
import com.hubertkarw.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockitoBean
    ProductService service;

    @Test
    void shouldGetProducts() throws Exception {
        List<ProductDTO> products = List.of(
                new ProductDTO(1L, "Laptop", "Laptop", BigDecimal.ZERO, null),
                new ProductDTO(2L, "Smartphone", "Smartphone", BigDecimal.ZERO, null),
                new ProductDTO(3L, "Electronics", "Electronics", BigDecimal.ZERO, null)
        );

        when(service.getProducts(null)).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$[1].name").value("Smartphone"))
                .andExpect(jsonPath("$[2].name").value("Electronics"));
    }

    @Test
    void shouldGetProduct() throws Exception {
        ProductDTO product = new ProductDTO(3L, "Electronics", "Electronics", BigDecimal.ZERO, null);

        when(service.getProduct(any())).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Electronics"))
                .andExpect(jsonPath("$.type").value("Electronics"))
                .andExpect(jsonPath("$.price").value(BigDecimal.ZERO));
    }

    @Test
    void shouldAddProduct() throws Exception {
        ProductCreateDTO productDTO = new ProductCreateDTO("Electronics", "Electronics", BigDecimal.ZERO, null);
        ProductDTO product = new ProductDTO(3L, "Electronics", "Electronics", BigDecimal.ZERO, null);

        when(service.addProduct(any())).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Electronics"))
                .andExpect(jsonPath("$.type").value("Electronics"))
                .andExpect(jsonPath("$.price").value(BigDecimal.ZERO));
    }

    @Test
    void shouldUpdateProduct() throws Exception {
        ProductCreateDTO productDTO = new ProductCreateDTO("Electronics", "Electronics", BigDecimal.ZERO, null);
        ProductDTO product = new ProductDTO(3L, "Electronics", "Electronics", BigDecimal.ZERO, null);

        when(service.updateProduct(any(), any())).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Electronics"))
                .andExpect(jsonPath("$.type").value("Electronics"))
                .andExpect(jsonPath("$.price").value(BigDecimal.ZERO));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(service).deleteProduct(1L);
    }

    @Test
    void shouldGetProductCustomizations() throws Exception {
        List<CustomizationDTO> customizations = List.of(
                new CustomizationDTO(1L, "Red", "color", BigDecimal.ZERO, null),
                new CustomizationDTO(2L, "Blue", "color", BigDecimal.ZERO, null),
                new CustomizationDTO(3L, "Black", "color", BigDecimal.ZERO, null)
        );

        when(service.getProductCustomizations(any())).thenReturn(customizations);

        mockMvc.perform(MockMvcRequestBuilders.get("/products/1/customization"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Red"))
                .andExpect(jsonPath("$[1].name").value("Blue"))
                .andExpect(jsonPath("$[2].name").value("Black"));
    }

    @Test
    void shouldAddProductCustomization() throws Exception {
        ProductDTO product = new ProductDTO(3L, "Electronics", "Electronics", BigDecimal.ZERO, List.of(new CustomizationDTO(1L, "Red", "color", BigDecimal.ZERO, null)));

        when(service.addProductCustomization(any(), any())).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/3/customization/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Electronics"))
                .andExpect(jsonPath("$.type").value("Electronics"))
                .andExpect(jsonPath("$.price").value(BigDecimal.ZERO))
                .andExpect(jsonPath("$.customizations").isArray())
                .andExpect(jsonPath("$.customizations[0].name").value("Red"));
    }

    @Test
    void shouldDeleteProductCustomization() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/products/1/customization/1"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(service).deleteProductCustomization(1L, 1L);
    }

    @Test
    void shouldAssignCustomizationAsProduct() throws Exception {
        ProductDTO product = new ProductDTO(3L, "Ram 32 GB", "Ram", BigDecimal.ZERO, null);

        when(service.assignCustomizationAsProduct(any())).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.post("/products/customization/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ram 32 GB"))
                .andExpect(jsonPath("$.type").value("Ram"))
                .andExpect(jsonPath("$.price").value(BigDecimal.ZERO));
    }
}
