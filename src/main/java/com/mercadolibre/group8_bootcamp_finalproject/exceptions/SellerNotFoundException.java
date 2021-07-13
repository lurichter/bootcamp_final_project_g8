package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class SellerNotFoundException extends NotFoundException {
    private static final String DESCRIPTION = "Seller not found";

    public SellerNotFoundException(String detail) {
            super(detail);
        }

    public SellerNotFoundException() {
            super(DESCRIPTION);
        }
}
