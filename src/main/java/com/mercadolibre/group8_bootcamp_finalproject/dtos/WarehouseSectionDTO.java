package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WarehouseSectionDTO {

    @NotNull(message = "section code is required")
    private Integer sectionCode;

    @NotNull(message = "warehouse code is required")
    private Integer warehouseCode;
}