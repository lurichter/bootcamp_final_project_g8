package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BatchStockDueDateListDTO {
    private List<BatchStockDueDateDTO> batchStock;
}
