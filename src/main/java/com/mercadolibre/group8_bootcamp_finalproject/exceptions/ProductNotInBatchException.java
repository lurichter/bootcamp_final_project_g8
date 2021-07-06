package com.mercadolibre.group8_bootcamp_finalproject.exceptions;

public class ProductNotInBatchException extends RuntimeException {
    private static final String DESCRIPTION = "The Product isn't in a Batch";

    public ProductNotInBatchException(String detail) {
        super(DESCRIPTION + ". " + detail);
    }

    public ProductNotInBatchException() {
        super(DESCRIPTION + ". ");
    }

}
