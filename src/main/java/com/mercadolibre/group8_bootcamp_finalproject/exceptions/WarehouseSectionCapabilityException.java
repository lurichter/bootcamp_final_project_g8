package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class WarehouseSectionCapabilityException extends BadRequestException {
    private static final String DESCRIPTION = "WarehouseSection current capability is less than all quantity products from batch stock";

    public WarehouseSectionCapabilityException(String detail) {
        super(detail);
    }
    public WarehouseSectionCapabilityException() {
        super(DESCRIPTION);
    }


}

