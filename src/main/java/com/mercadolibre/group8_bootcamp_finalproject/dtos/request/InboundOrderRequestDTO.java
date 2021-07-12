package com.mercadolibre.group8_bootcamp_finalproject.dtos.request;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InboundOrderRequestDTO {

    @Valid
    @NotNull(message = "inboundOrder field must not be null.")
    private InboundOrderDTO inboundOrder;
}
