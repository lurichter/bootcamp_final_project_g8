package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class UnauthorizedException extends RuntimeException {
    private static final String DESCRIPTION = "User or password incorrect.";

    public UnauthorizedException() {
        super(DESCRIPTION);
    }

}
