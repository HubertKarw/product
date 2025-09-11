package com.hubertkarw.product.controller;


import com.hubertkarw.product.model.CartItemRequest;
import com.hubertkarw.product.model.CustomizationDTO;
import com.hubertkarw.product.model.ProductCreateDTO;
import com.hubertkarw.product.model.ProductDTO;
import com.hubertkarw.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product", description = "Product operations")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    @Operation(summary = "Get products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found products", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))})
    })
    @GetMapping
    List<ProductDTO> getProducts(@Parameter(description = "Filtering by type") @RequestParam(required = false) String type) {
        log.info(type == null ? "GET /products requested" : "GET /products?type={} requested", type);
        return service.getProducts(type);
    }

    @Operation(summary = "Get product by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found product", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))})
    })
    @GetMapping("/{id}")
    ProductDTO getProduct(@PathVariable("id") long id) {
        log.info("GET /products/{} requested", id);
        return service.getProduct(id);
    }

    @Operation(summary = "Create product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created product", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))})
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ProductDTO addProduct(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Product to create",
            required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductCreateDTO.class),
                    examples = @ExampleObject(value =
                            """
                                    {
                                    "name": "Laptop XYZ",
                                    "type": "computer",
                                    "price": 123.12,
                                    "customizations": {}
                                    }
                                    """)))
                          @RequestBody ProductCreateDTO productDTO) {
        log.info("POST /products requested body={}", productDTO);
        return service.addProduct(productDTO);
    }

    @Operation(summary = "Update product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated product", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))})
    })
    @PutMapping("/{id}")
    ProductDTO updateProduct(@PathVariable("id") long id,
                             @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                     description = "Product changed",
                                     required = true,
                                     content = @Content(mediaType = "application/json",
                                             schema = @Schema(implementation = ProductCreateDTO.class),
                                             examples = @ExampleObject(value =
                                                     """
                                                             {
                                                             "name": "Laptop XYZ",
                                                             "type": "computer",
                                                             "price": 123.12,
                                                             "customizations": {}
                                                             }
                                                             """)))
                             @RequestBody ProductCreateDTO productDTO) {
        log.info("PUT /products/{} requested body={}", id, productDTO);
        return service.updateProduct(id, productDTO);
    }

    @Operation(summary = "Delete product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed product")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProduct(@PathVariable("id") long id) {
        log.info("DELETE /products/{} requested", id);
        service.deleteProduct(id);
    }

    @Operation(summary = "Get all customizations for given product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Customizations", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CustomizationDTO.class))})
    })
    @GetMapping("/{id}/customization")
    public List<CustomizationDTO> getProductCustomizations(@PathVariable("id") long id) {
        log.info("GET /product/{}/customization requested", id);
        return service.getProductCustomizations(id);
    }

    @Operation(summary = "Add customization for given product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added Customization", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))})
    })
    @PutMapping("/{id}/customization/{customizationId}")
    ProductDTO addProductCustomization(@PathVariable("id") long id, @PathVariable("customizationId") long customizationId) {
        log.info("PUT /products/{}/customization/{} requested", id, customizationId);
        return service.addProductCustomization(id, customizationId);
    }

    @Operation(summary = "Delete customization from product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Removed customization from product")
    })
    @DeleteMapping("/{id}/customization/{customizationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteProductCustomization(@PathVariable("id") long id, @PathVariable("customizationId") long customizationId) {
        log.info("DELETE /products/{}/customization/{} requested", id, customizationId);
        service.deleteProductCustomization(id, customizationId);
    }

    @Operation(summary = "Add customization as product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added Customization as product", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))})
    })
    @PostMapping("/customization/{id}")
    ProductDTO assignCustomizationAsProduct(@PathVariable("id") long id) {
        log.info("POST /products/customization/{} requested", id);
        return service.assignCustomizationAsProduct(id);
    }

    @Operation(summary = "Add product to Cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Added product to Cart", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CartItemRequest.class))})
    })
    @PostMapping("/{id}/add-to-cart/{cartId}")
    CartItemRequest addProductToCart(@PathVariable("id") long id,
                                     @PathVariable("cartId") long cartId,
                                     @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                             description = "List of customizations chosen",
                                             required = true,
                                             content = @Content(mediaType = "application/json"))
                                     @RequestBody List<Long> customizationIds){
        return service.addProductToCart(id,cartId,customizationIds);
    }

}
