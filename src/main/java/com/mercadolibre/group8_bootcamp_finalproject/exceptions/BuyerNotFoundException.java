package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class BuyerNotFoundException extends NotFoundException {
    private static final String DESCRIPTION = "Buyer not found";

    public BuyerNotFoundException(String detail) {
        super(detail);
    }

    public BuyerNotFoundException() {
        super(DESCRIPTION);
    }

}

