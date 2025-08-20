package com.hubertkarw.product.repository;

import com.hubertkarw.product.model.Customization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomizationRepository extends JpaRepository<Customization, Long> {
}
