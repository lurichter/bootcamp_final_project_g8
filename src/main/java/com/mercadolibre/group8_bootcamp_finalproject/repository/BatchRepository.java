package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.Batch;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface BatchRepository extends JpaRepository<Batch, Long> {
    Set<Batch> findBatchByProductId(Long productId, Sort sort);

    @Query("SELECT " +
            " new com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateDTO(ws.warehouse.id, b.number, b.product.id, pc.name, b.dueDate, b.quantity) " +
            "FROM WarehouseOperator wo " +
            "JOIN WarehouseSection ws " +
            "ON ws.warehouse.id = wo.warehouse.id " +
            "JOIN Batch b " +
            "ON b.warehouseSection.id = ws.id " +
            "JOIN Product p ON b.product.id = p.id " +
            "JOIN ProductCategory pc " +
            "ON pc.id = p.productCategory.id " +
            "WHERE wo.operator.id = :operatorId " +
            "AND b.dueDate <= :dueDateFuture")
    List<BatchStockDueDateDTO> findAllByWarehouseSectionWhereDueDateLessThanParam(@Param("operatorId") Long operatorId, @Param("dueDateFuture") LocalDate dueDateFuture);
}
