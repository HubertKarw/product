package com.hubertkarw.product.service;

import com.hubertkarw.product.exception.ProductAppException;
import com.hubertkarw.product.mapper.CustomizationMapper;
import com.hubertkarw.product.mapper.CustomizationMapperImpl;
import com.hubertkarw.product.mapper.ProductMapper;
import com.hubertkarw.product.mapper.ProductMapperImpl;
import com.hubertkarw.product.model.Customization;
import com.hubertkarw.product.model.Product;
import com.hubertkarw.product.model.ProductCreateDTO;
import com.hubertkarw.product.model.ProductDTO;
import com.hubertkarw.product.repository.CustomizationRepository;
import com.hubertkarw.product.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProductServiceTest {
    ProductService service;
    CustomizationRepository customizationRepository;
    ProductRepository repository;
    CustomizationMapper customizationMapper = new CustomizationMapperImpl();
    ProductMapper mapper = new ProductMapperImpl();

    @BeforeEach
    void setup() {
        this.repository = Mockito.mock(ProductRepository.class);
        this.customizationRepository = Mockito.mock(CustomizationRepository.class);
        this.service = new ProductService(repository,customizationRepository, mapper, customizationMapper);
    }

    @Test
    void getProductsWithNoType_productsExist_productsReturned(){
        //given
        List<Product> products = List.of(
                new Product(1L, "Laptop", "Computer", BigDecimal.valueOf(1000L),null),
                new Product(2L, "Laptop", "Computer", BigDecimal.valueOf(1000L),null),
                new Product(3L, "Laptop", "Computer", BigDecimal.valueOf(1000L),null)
        );
        when(repository.findAll()).thenReturn(products);
        //when
        List<ProductDTO> result = service.getProducts(null);
        //then
        assertAll(
                ()->assertEquals(3, result.size()),
                ()->assertEquals(1L, result.get(0).getId()),
                ()->assertEquals(2L, result.get(1).getId()),
                ()->assertEquals(3L, result.get(2).getId()),
                ()->assertEquals("Laptop", result.get(0).getName()),
                ()->assertEquals("Laptop", result.get(1).getName()),
                ()->assertEquals("Laptop", result.get(2).getName()),
                ()->assertEquals("Computer", result.get(0).getType()),
                ()->assertEquals("Computer", result.get(1).getType()),
                ()->assertEquals("Computer", result.get(2).getType()),
                ()->assertEquals(BigDecimal.valueOf(1000L), result.get(0).getPrice()),
                ()->assertEquals(BigDecimal.valueOf(1000L), result.get(1).getPrice()),
                ()->assertEquals(BigDecimal.valueOf(1000L), result.get(2).getPrice())
        );
    }
    @Test
    void getProductsWithType_productsExist_productsReturned(){
        //given
        List<Product> products = List.of(
                new Product(1L, "Laptop", "Computer", BigDecimal.valueOf(1000L),null),
                new Product(2L, "Laptop", "Computer", BigDecimal.valueOf(1000L),null),
                new Product(3L, "Laptop", "Computer", BigDecimal.valueOf(1000L),null)
        );
        when(repository.findByType("Computer")).thenReturn(products);
        //when
        List<ProductDTO> result = service.getProducts("Computer");
        //then
        assertAll(
                ()->assertEquals(3, result.size()),
                ()->assertEquals(1L, result.get(0).getId()),
                ()->assertEquals(2L, result.get(1).getId()),
                ()->assertEquals(3L, result.get(2).getId()),
                ()->assertEquals("Laptop", result.get(0).getName()),
                ()->assertEquals("Laptop", result.get(1).getName()),
                ()->assertEquals("Laptop", result.get(2).getName()),
                ()->assertEquals("Computer", result.get(0).getType()),
                ()->assertEquals("Computer", result.get(1).getType()),
                ()->assertEquals("Computer", result.get(2).getType()),
                ()->assertEquals(BigDecimal.valueOf(1000L), result.get(0).getPrice()),
                ()->assertEquals(BigDecimal.valueOf(1000L), result.get(1).getPrice()),
                ()->assertEquals(BigDecimal.valueOf(1000L), result.get(2).getPrice())
        );
    }

    @Test
    void getProduct_productExists_productReturned(){
        //given
        Product product = new Product(1L, "Laptop", "Computer", BigDecimal.valueOf(1000L),null);
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        //when
        ProductDTO result = service.getProduct(1L);
        //then
        assertAll(
                ()-> assertEquals(product.getId(), result.getId()),
                ()-> assertEquals(product.getName(), result.getName()),
                ()-> assertEquals(product.getType(), result.getType()),
                ()-> assertEquals(product.getPrice(), result.getPrice()),
                ()-> assertEquals(product.getCustomizations(), result.getCustomizations())
        );
    }

    @Test
    void getProduct_productNotExists_throwException(){
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());
        //then
        ProductAppException exception = Assertions.assertThrows(ProductAppException.class, ()->service.getProduct(1L));
        assertEquals("product not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void addProduct_productViable_productAdded(){
        //given
        Product productSaved = new Product(1L, "Laptop", "Computer", BigDecimal.valueOf(1000L),null);
        ProductCreateDTO product = new ProductCreateDTO( "Laptop", "Computer", BigDecimal.valueOf(1000L),null);
        when(repository.save(any())).thenReturn(productSaved);
        //when
        ProductDTO result = service.addProduct(product);
        //then
        assertEquals(productSaved.getId(), result.getId());
        assertEquals(productSaved.getName(), result.getName());
        assertEquals(productSaved.getType(), result.getType());
        assertEquals(productSaved.getPrice(), result.getPrice());
        assertEquals(productSaved.getCustomizations(), result.getCustomizations());
    }

    @Test
    void removeProduct_productExists_productRemoved(){
        //given
        Product product = new Product(1L, "Laptop", "Computer", BigDecimal.valueOf(1000L),null);
        when(repository.findById(any())).thenReturn(Optional.of(product));
        //when
        service.deleteProduct(1L);
        //then
        verify(repository).delete(product);
    }

    @Test
    void removeProduct_productNotExists_throwException(){
        //given
        when(repository.findById(1L)).thenReturn(Optional.empty());
        //then
        ProductAppException exception = Assertions.assertThrows(ProductAppException.class, ()->service.deleteProduct(1L));
        assertEquals("product not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void updateProduct_productExists_productUpdated(){
        //given
        Product oldProduct = new Product(1L, "Laptop", "Computer", BigDecimal.valueOf(1000L),null);
        Product updatedProduct = new Product(1L, "Laptop", "Computer", BigDecimal.valueOf(3000L),null);
        ProductCreateDTO product = new ProductCreateDTO( "Laptop", "Computer", BigDecimal.valueOf(3000L),null);
        when(repository.findById(1L)).thenReturn(Optional.of(oldProduct));
        when(repository.save(any())).thenReturn(updatedProduct);
        //when
        ProductDTO result = service.updateProduct(1L,product);
        //then
        assertEquals(updatedProduct.getId(), result.getId());
        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getType(), result.getType());
        assertEquals(updatedProduct.getPrice(), result.getPrice());
        assertEquals(updatedProduct.getCustomizations(), result.getCustomizations());
    }

    @Test
    void updateProduct_productNotExists_throwException(){
        //given
        ProductCreateDTO product = new ProductCreateDTO( "Laptop", "Computer", BigDecimal.valueOf(3000L),null);
        when(repository.findById(1L)).thenReturn(Optional.empty());
        //then
        ProductAppException exception = Assertions.assertThrows(ProductAppException.class, ()->service.updateProduct(1L,product));
        assertEquals("product not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void addProductCustomization_productAndCustomizationExists_productReturned(){
        //given
        Product product = new Product(1L, "Laptop", "Computer", BigDecimal.valueOf(1000L),new ArrayList<>());
        Customization customization = new Customization(1L, "Red", "color", BigDecimal.ZERO, null);
        Product productWithCustomization= new Product(1L, "Laptop", "Computer", BigDecimal.valueOf(1000L),List.of(customization));
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(customizationRepository.findById(1L)).thenReturn(Optional.of(customization));
        when(repository.save(any())).thenReturn(productWithCustomization);
        //when
        ProductDTO result = service.addProductCustomization(1L,1L);
        //then
        assertAll(
                ()->assertEquals(1L,result.getId()),
                ()->assertEquals("Laptop",result.getName()),
                ()->assertEquals("Computer",result.getType()),
                ()->assertEquals(BigDecimal.valueOf(1000L),result.getPrice()),
                ()->assertEquals(1L,result.getCustomizations().get(0).getId()),
                ()->assertEquals("Red",result.getCustomizations().get(0).getName()),
                ()->assertEquals("color",result.getCustomizations().get(0).getType())
        );
    }

    @Test
    void addProductCustomization_productNotExists_throwException(){
        //given
        Customization customization = new Customization(1L, "Red", "color", BigDecimal.ZERO, null);
        when(repository.findById(1L)).thenReturn(Optional.empty());
        when(customizationRepository.findById(1L)).thenReturn(Optional.of(customization));
        //then
        ProductAppException exception = Assertions.assertThrows(ProductAppException.class, ()->service.addProductCustomization(1L,1L));
        assertEquals("product not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void addProductCustomization_customizationNotExists_throwException(){
        //given
        Product product = new Product(1L, "Laptop", "Computer", BigDecimal.valueOf(1000L),new ArrayList<>());
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(customizationRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        ProductAppException exception = Assertions.assertThrows(ProductAppException.class, ()->service.addProductCustomization(1L,1L));
        assertEquals("customization not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void deleteProductCustomization_productAndCustomizationExists_productDeleted(){
        //given
        Customization customization = new Customization(1L, "Red", "color", BigDecimal.ZERO, null);
        List<Customization> customizations = new ArrayList<>();
        customizations.add(customization);
        Product productWithCustomization= new Product(1L, "Laptop", "Computer", BigDecimal.valueOf(1000L),customizations);
        Product product = new Product(1L, "Laptop", "Computer", BigDecimal.valueOf(1000L),new ArrayList<>());
        when(repository.findById(1L)).thenReturn(Optional.of(productWithCustomization));
        when(customizationRepository.findById(1L)).thenReturn(Optional.of(customization));
        when(repository.save(any())).thenReturn(product);
        //when
        service.deleteProductCustomization(1L,1L);
        //then
        verify(repository).save(product);
    }

    @Test
    void deleteProductCustomization_productNotExists_throwException(){
        //given
        Customization customization = new Customization(1L, "Red", "color", BigDecimal.ZERO, null);
        when(repository.findById(1L)).thenReturn(Optional.empty());
        when(customizationRepository.findById(1L)).thenReturn(Optional.of(customization));
        //then
        ProductAppException exception = Assertions.assertThrows(ProductAppException.class, ()->service.deleteProductCustomization(1L,1L));
        assertEquals("product not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void deleteProductCustomization_customizationNotExists_throwException(){
        //given
        Product product = new Product(1L, "Laptop", "Computer", BigDecimal.valueOf(1000L),new ArrayList<>());
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(customizationRepository.findById(1L)).thenReturn(Optional.empty());
        //then
        ProductAppException exception = Assertions.assertThrows(ProductAppException.class, ()->service.deleteProductCustomization(1L,1L));
        assertEquals("customization not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    void assignCustomizationAsProduct_customizationExists_CustomizationAdded(){
        //given
        Customization customization = new Customization(1L, "Ram 32GB", "Ram", BigDecimal.valueOf(1000L), null);
        Product product = new Product(1L, "Ram 32GB", "Ram", BigDecimal.valueOf(1000L),new ArrayList<>());
        when(customizationRepository.findById(1L)).thenReturn(Optional.of(customization));
        when(repository.save(any())).thenReturn(product);
        //when
        ProductDTO result = service.assignCustomizationAsProduct(1L);
        //then
        assertAll(
                () -> assertEquals(1L, result.getId()),
                ()->assertEquals("Ram 32GB",result.getName()),
                ()->assertEquals("Ram",result.getType()),
                ()->assertEquals(BigDecimal.valueOf(1000L),result.getPrice())
        );
    }

    @Test
    void assignCustomizationAsProduct_customizationNotExists_throwException(){
        when(customizationRepository.findById(1L)).thenReturn(Optional.empty());

        ProductAppException exception = Assertions.assertThrows(ProductAppException.class, ()->service.assignCustomizationAsProduct(1L));
        assertEquals("customization not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }
}
