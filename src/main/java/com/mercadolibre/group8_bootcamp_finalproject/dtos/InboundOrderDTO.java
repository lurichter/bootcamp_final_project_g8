package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
@Builder
public class InboundOrderDTO {

    @Valid
    private WarehouseSectionDTO section;

    @Valid
    private List<BatchDTO> batchStock;
}