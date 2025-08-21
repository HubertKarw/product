package com.hubertkarw.product.controller;

import com.hubertkarw.product.model.CustomizationDTO;
import com.hubertkarw.product.service.CustomizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customs")
public class CustomizationController {

    private final CustomizationService service;

    @GetMapping
    List<CustomizationDTO> getCustomizations(@RequestParam(required = false) String type){
        return service.getCustomizations(type);
    }

    @PostMapping
    CustomizationDTO addCustomization(){
        return null;
    }
}
