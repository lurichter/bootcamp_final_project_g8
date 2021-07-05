package com.mercadolibre.group8_bootcamp_finalproject.dtos.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ProductQuantityRequestDTO {

    @NotNull(message = "O ID do produto não pode ser null.")
    private Long productId;

    @NotNull
    @Min(value = 1, message = "A quantidade do produto deve ser maior do que 0.")
    private Integer quantity;

}