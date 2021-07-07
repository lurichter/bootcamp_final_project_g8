package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.model.Batch;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BatchRepository extends JpaRepository<Batch, Long> {

    Set<Batch> findBatchByProductId(Long productId, Sort sort);
}
