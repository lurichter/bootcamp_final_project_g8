package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class WarehouseSectionDTO {

    @NotNull(message = "section code is required")
    private Long sectionCode;
// TODO não serve pra nada mas está no teste
    @NotNull(message = "warehouse code is required")
    private Long warehouseCode;
}