package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class ProductNotFoundException extends NotFoundException {
    private static final String DESCRIPTION = "Product not found";

    public ProductNotFoundException(String detail) {
        super(detail);
    }

    public ProductNotFoundException() {
        super(DESCRIPTION);
    }

}

