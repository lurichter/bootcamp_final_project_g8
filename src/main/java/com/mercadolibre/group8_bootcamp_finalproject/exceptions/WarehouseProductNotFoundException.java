package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class WarehouseProductNotFoundException extends NotFoundException {
    private static final String DESCRIPTION = "Cannot find this product any Warehouse";

    public WarehouseProductNotFoundException(String detail) {
        super(detail);
    }

    public WarehouseProductNotFoundException() {
        super(DESCRIPTION);
    }

}

