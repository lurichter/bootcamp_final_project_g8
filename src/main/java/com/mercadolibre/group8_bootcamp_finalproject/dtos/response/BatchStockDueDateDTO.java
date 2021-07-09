package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class BatchStockDueDateDTO {
    private Long sectionId;
    private String batchNumber;
    private Long productId;
    private String productCategory;
    private LocalDate dueDate;
    private Integer quantity;

    public BatchStockDueDateDTO(Long sectionId, String batchNumber, Long productId, ProductCategoryEnum productType, LocalDate dueDate, Integer quantity) {
        this.sectionId = sectionId;
        this.batchNumber = batchNumber;
        this.productId = productId;
        this.productCategory = productType.getLabel();
        this.dueDate = dueDate;
        this.quantity = quantity;
    }
}
