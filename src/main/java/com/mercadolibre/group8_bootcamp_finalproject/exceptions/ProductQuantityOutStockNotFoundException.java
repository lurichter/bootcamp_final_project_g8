package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class ProductQuantityOutStockNotFoundException extends NotFoundException {
    public ProductQuantityOutStockNotFoundException(String detail) {
        super(detail);
    }
}

