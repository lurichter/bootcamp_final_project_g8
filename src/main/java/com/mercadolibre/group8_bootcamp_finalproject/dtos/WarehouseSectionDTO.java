package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class WarehouseSectionDTO {

    @NotNull(message = "section code is required")
    private Integer sectionCode;

    @NotNull(message = "warehouse code is required")
    private Integer warehouseCode;
}