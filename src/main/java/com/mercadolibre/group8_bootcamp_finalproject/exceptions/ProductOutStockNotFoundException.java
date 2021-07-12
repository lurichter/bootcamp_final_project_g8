package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class ProductOutStockNotFoundException extends NotFoundException {
    public ProductOutStockNotFoundException(String detail) {
        super(detail);
    }
}

