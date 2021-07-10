package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.model.Operator;

public interface IOperatorService {

    Operator getLoggedUOperator();
    Long getLoggedUserOperatorId();
    boolean isLoggedOperatorInWarehouse(Long wareHouseId);
    boolean isOperatorInWarehouse(Long operatorId, Long wareHouseId);
    void validateOperatorInWarehouse(Long operatorId, Long wareHouseId);
}