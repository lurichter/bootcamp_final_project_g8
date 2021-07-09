package com.mercadolibre.group8_bootcamp_finalproject.service.impl;

import com.mercadolibre.group8_bootcamp_finalproject.exceptions.UserIsNotAOperatorException;
import com.mercadolibre.group8_bootcamp_finalproject.model.Operator;
import com.mercadolibre.group8_bootcamp_finalproject.repository.OperatorRepository;
import com.mercadolibre.group8_bootcamp_finalproject.security.SecurityUtil;
import com.mercadolibre.group8_bootcamp_finalproject.service.IOperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperatorService implements IOperatorService {

    private final OperatorRepository operatorRepository;

    @Override
    public Operator getLoggedUOperator() {
        return operatorRepository.findOperatorByUserName(SecurityUtil.getLoggedUser()).orElseThrow(UserIsNotAOperatorException::new);
    }

    @Override
    public Long getLoggedUserOperatorId() {
        return getLoggedUOperator().getId();
    }

    @Override
    public boolean isLoggedOperatorInWarehouse(Long wareHouseId) {
        return operatorRepository.existsByIdAndWarehouseOperatorsWarehouseId(getLoggedUserOperatorId(), wareHouseId);
    }

    @Override
    public boolean isOperatorInWarehouse(Long operatorId, Long wareHouseId) {
        return operatorRepository.existsByIdAndWarehouseOperatorsWarehouseId(operatorId, wareHouseId);
    }
}
