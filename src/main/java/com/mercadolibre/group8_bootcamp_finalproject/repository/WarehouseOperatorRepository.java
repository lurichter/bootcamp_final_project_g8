package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.model.WarehouseOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarehouseOperatorRepository extends JpaRepository<WarehouseOperator, Long> {

    @Query("select w from WarehouseOperator w where w.warehouse.id = :id")
    List<WarehouseOperator> findByWarehouseCode(@Param("id") Long id);
}
