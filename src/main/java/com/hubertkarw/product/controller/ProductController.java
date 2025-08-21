package com.hubertkarw.product.controller;


import com.hubertkarw.product.model.ProductCreateDTO;
import com.hubertkarw.product.model.ProductDTO;
import com.hubertkarw.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    @GetMapping
    List<ProductDTO> getProducts(@RequestParam(required = false) String type) {
        return service.getProducts(type);
    }

    @PostMapping
    ProductDTO addProduct(@RequestBody ProductCreateDTO productDTO) {
        return service.addProduct(productDTO);
    }

    @PutMapping("/{id}")
    ProductDTO updateProduct(@PathVariable("id") long id, @RequestBody ProductCreateDTO productDTO) {
        return service.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    void deleteProduct(@PathVariable("id") long id) {
        service.deleteProduct(id);
    }

}
