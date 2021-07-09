package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class UserIsNotAOperatorException extends RuntimeException {
    private static final String DESCRIPTION = "The user is not an Operator.";

    public UserIsNotAOperatorException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

    public UserIsNotAOperatorException() {
        super(DESCRIPTION);
    }

}
