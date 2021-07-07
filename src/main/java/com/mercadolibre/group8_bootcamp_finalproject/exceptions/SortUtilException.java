package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class SortUtilException extends BadRequestException {

    public SortUtilException(String detail) {
        super(detail);
    }
}