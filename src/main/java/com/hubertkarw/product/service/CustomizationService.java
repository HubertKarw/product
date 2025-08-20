package com.hubertkarw.product.service;

import com.hubertkarw.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomizationService {
    private final ProductRepository repository;
}
