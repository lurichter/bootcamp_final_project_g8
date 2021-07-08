package com.mercadolibre.group8_bootcamp_finalproject.model.enums;

import lombok.Getter;

@Getter
public enum ProductCategoryEnum {
    FS("Fresh"),
    RF("Chilled"),
    FF("Frozen");

    public final String label;

    ProductCategoryEnum(String label) {
        this.label = label;
    }
}
