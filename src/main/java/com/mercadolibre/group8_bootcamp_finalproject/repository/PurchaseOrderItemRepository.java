package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.model.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {

//    @Query(value = "SELECT * FROM fresh.purchase_order_item WHERE batch_id = :batchId", nativeQuery = true)
    @Query(value = "SELECT p FROM PurchaseOrderItem.batch p WHERE p.id = :batchId")
    List<PurchaseOrderItem> findByBatchId (@Param("batchId") Integer batchId);
}
