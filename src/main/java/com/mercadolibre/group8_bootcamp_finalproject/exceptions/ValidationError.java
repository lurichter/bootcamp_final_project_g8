package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

import lombok.Getter;

@Getter
public class ValidationError {

    private final String field;
    private final String message;

    public ValidationError(String field, String message) {
        super();
        this.field = field;
        this.message = message;
    }

}