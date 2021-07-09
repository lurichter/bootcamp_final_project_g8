package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.model.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperatorRepository extends JpaRepository<Operator, Long> {
    Operator findOperatorByUserId(Long userId);

    Optional<Operator> findOperatorByUserName(String userName);

    boolean existsByIdAndWarehouseOperatorsWarehouseId(Long id, Long wareHouseId);
}
