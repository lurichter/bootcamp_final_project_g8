package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WarehouseSectionDTO {

    @NotNull(message = "section code is required")
    private Integer sectionCode;
// TODO não serve pra nada mas está no teste
    @NotNull(message = "warehouse code is required")
    private Integer warehouseCode;
}