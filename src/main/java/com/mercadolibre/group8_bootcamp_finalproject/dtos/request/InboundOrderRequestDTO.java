package com.mercadolibre.group8_bootcamp_finalproject.dtos.request;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderDTO;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;

@Data
@Builder
public class InboundOrderRequestDTO {
    @Valid
    private InboundOrderDTO inboundOrder;
}
