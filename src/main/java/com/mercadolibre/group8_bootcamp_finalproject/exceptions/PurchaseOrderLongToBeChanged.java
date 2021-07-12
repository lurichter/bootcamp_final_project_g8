package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class PurchaseOrderLongToBeChanged extends BadRequestException{
    public PurchaseOrderLongToBeChanged(String detail) {
        super(detail);
    }
}
