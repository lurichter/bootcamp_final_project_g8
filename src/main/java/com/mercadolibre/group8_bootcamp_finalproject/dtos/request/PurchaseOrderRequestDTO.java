package com.mercadolibre.group8_bootcamp_finalproject.dtos.request;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.PurchaseOrderDTO;
import lombok.Data;

import javax.validation.Valid;

@Data
public class PurchaseOrderRequestDTO {

    @Valid
    private PurchaseOrderDTO purchaseOrderDTO;

}