package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.model.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {

    @Query(value = "SELECT p FROM PurchaseOrderItem p WHERE p.batch.id = :batchId")
    List<PurchaseOrderItem> findByBatchId (@Param("batchId") Long batchId);

    @Query(value = "SELECT p FROM PurchaseOrderItem p WHERE p.purchaseOrder.id = :purchaseOrderId")
    List<PurchaseOrderItem> findAllByPurchaseOrder(@Param("purchaseOrderId") Long purchaseOrderId);
}
