package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.model.Batch;
import com.mercadolibre.group8_bootcamp_finalproject.model.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {

    @Query(value = "SELECT p FROM PurchaseOrderItem p WHERE p.batch.id = :batchId")
    List<PurchaseOrderItem> findByBatchId (@Param("batchId") Long batchId);

    List<PurchaseOrderItem> findPurchaseOrderItemByBatchIn(List<Batch> batches);

    @Query(value = "SELECT p FROM PurchaseOrderItem p WHERE p.purchaseOrder.id = :purchaseOrderId")
    List<PurchaseOrderItem> findAllByPurchaseOrder(@Param("purchaseOrderId") Long purchaseOrderId);

    //@Query("SELECT COUNT(PurchaseOrderItem) FROM PurchaseOrderItem f JOIN Batch b ON b.id = f.id WHERE b.product.id = :productId AND f.purchaseOrder.id = :purchaseOrderId")
    Long countByBatch_ProductIdAndPurchaseOrderId(Long productId, Long purchaseOrderId);

    //@Query("SELECT f.id, f.quantity, f.totalPrice, f.batch, f.purchaseOrder FROM PurchaseOrderItem f JOIN Batch b ON b.id = f.id WHERE b.product.id = :productId AND f.purchaseOrder.id = :purchaseOrderId")
    List<PurchaseOrderItem> findPurchaseOrderItemByBatch_ProductIdAndPurchaseOrderId(Long productId, Long purchaseOrderId);

    PurchaseOrderItem findPurchaseOrderItemByBatch_IdAndPurchaseOrderId(Long batchId, Long purchaseOrderId);
}
