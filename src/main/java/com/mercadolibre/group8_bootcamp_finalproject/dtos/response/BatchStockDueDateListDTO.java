package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BatchStockDueDateListDTO {
    private List<BatchStockDueDateDTO> batchStock;
}
