package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import lombok.Data;

import javax.validation.Valid;

@Data
public class InboundOrderRequest {
    @Valid
    private InboundOrderDTO inboundOrder;
}
