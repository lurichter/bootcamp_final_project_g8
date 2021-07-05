package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.model.WarehouseOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarehouseOperatorRepository extends JpaRepository<WarehouseOperator, Long> {

    @Query(value = "SELECT * FROM fresh.warehouse_operator WHERE warehouse_id = :id", nativeQuery = true)
    List<WarehouseOperator> findByWarehouseCode(@Param("id") Long id);
}
