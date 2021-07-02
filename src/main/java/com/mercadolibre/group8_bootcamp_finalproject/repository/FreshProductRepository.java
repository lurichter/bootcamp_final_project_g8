package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.model.FreshProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FreshProductRepository extends JpaRepository<FreshProduct, Long> {
}
