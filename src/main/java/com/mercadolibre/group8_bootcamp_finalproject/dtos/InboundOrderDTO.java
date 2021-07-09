package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InboundOrderDTO {

    Long inboundOrderId;

    @Valid
    private WarehouseSectionDTO section;

    @Valid
    private List<BatchDTO> batchStock;
}