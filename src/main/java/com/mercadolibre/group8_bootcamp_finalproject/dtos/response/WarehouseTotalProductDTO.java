package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseTotalProductDTO {
    private Long warehouseCode;
    private Long totalQuantity;
}
