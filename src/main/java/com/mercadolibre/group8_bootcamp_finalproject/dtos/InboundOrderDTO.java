package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InboundOrderDTO {

    Long inboundOrderId;

    @Valid
    private WarehouseSectionDTO section;

    @Valid
    private List<BatchDTO> batchStock;
}