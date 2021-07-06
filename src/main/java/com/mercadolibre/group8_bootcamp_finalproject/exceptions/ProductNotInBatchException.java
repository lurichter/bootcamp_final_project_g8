package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class ProductNotInBatchException extends BadRequestException {

    public ProductNotInBatchException(String detail) {
        super(detail);
    }

}

