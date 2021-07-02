package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.model.PurchaseOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderItemRepository extends JpaRepository<PurchaseOrderItem, Long> {
}
