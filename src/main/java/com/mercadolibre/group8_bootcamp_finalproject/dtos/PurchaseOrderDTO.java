package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.ProductQuantityRequestDTO;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PurchaseOrderDTO {

    @NotNull
    private Long buyerId;

    @Valid
    @NotNull
    private OrderStatusDTO orderStatus;

    @Valid
    @NotNull
    private List<ProductQuantityRequestDTO> products;
}