package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.model.Warehouse;
import com.mercadolibre.group8_bootcamp_finalproject.model.WarehouseSection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface WarehouseRepository extends JpaRepository<Warehouse, Long> {
}
