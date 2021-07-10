package com.mercadolibre.group8_bootcamp_finalproject.dtos.request;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderDTO;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class InboundOrderRequestDTO {

    @Valid
    @NotNull(message = "inboundOrder field must not be null.")
    private InboundOrderDTO inboundOrder;
}
