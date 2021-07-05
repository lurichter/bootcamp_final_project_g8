package com.mercadolibre.group8_bootcamp_finalproject.dtos.request;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderDTO;
import lombok.Data;

import javax.validation.Valid;

@Data
public class InboundOrderRequestDTO {
    @Valid
    private InboundOrderDTO inboundOrder;
}
