package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.model.InboundOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboudOrderRepository extends JpaRepository<InboundOrder, Long> {
}
