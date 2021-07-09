package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BatchStockDTO {
    private String batchNumber;
    private int currentQuantity;
    private LocalDate dueDate;
}
