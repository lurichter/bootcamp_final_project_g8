package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class BatchNotFoundException extends BadRequestException {
    public BatchNotFoundException(String detail) {
        super(detail);
    }
}
