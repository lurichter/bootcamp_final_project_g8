package com.mercadolibre.group8_bootcamp_finalproject.dtos.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProductQuantityRequestDTO {

    @NotNull(message = "Product ID cannot be null.")
    private Long productId;

    @NotNull
    @Min(value = 1, message = "The product quantity must be greater than 0.")
    private Integer quantity;

}