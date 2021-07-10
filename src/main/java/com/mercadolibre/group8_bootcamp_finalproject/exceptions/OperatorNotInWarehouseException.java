package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class OperatorNotInWarehouseException extends BadRequestException {
    private static final String DESCRIPTION = "The operator is not in the Warehouse.";

    public OperatorNotInWarehouseException() {
        super(DESCRIPTION);
    }

    public OperatorNotInWarehouseException(String detail) {
        super(detail);
    }

}
