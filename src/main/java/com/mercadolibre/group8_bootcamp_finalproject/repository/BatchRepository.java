package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, Long> {
    @Query(value = "SELECT b FROM Batch b WHERE b.product.id = :productId")
    List<Batch> findAllByProduct(@Param("productId") Long productId);
}
