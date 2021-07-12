package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseSectionDTO {

    @NotNull(message = "section code is required")
    private Long sectionCode;
// TODO não serve pra nada mas está no teste
    @NotNull(message = "warehouse code is required")
    private Long warehouseCode;
}