package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class BatchStockDueDateDTO {
    private Long sectionId;
    private Long batchNumber;
    private Long productId;
    private String productTypeId;
    private LocalDate dueDate;
    private Integer quantity;
}
