package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
