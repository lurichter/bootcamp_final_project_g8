package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class WarehouseProductListDTO {
    private Long productId;
    private List<WarehouseTotalProductDTO> warehouses;
}
