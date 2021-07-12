package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseTotalProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.WarehouseSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarehouseSectionRepository extends JpaRepository<WarehouseSection, Long> {

    @Query("SELECT " +
            "   new com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseTotalProductDTO(section.warehouse.id, SUM(batch.quantity)) " +
            "FROM WarehouseSection section " +
            "   LEFT JOIN Batch batch " +
            "       ON batch.warehouseSection.id = section.id " +
            "WHERE batch.product.id = :productId " +
            "GROUP BY section.warehouse.id")
    List<WarehouseTotalProductDTO> findAllProductsFromWarehouse (@Param("productId") Long productId);

}
