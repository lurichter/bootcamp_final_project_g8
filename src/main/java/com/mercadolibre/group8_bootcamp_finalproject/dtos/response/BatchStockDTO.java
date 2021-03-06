package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchStockDTO {
    private Long wareHouseId;
    private Long sectionId;

    private Long batchId;
    private String batchNumber;
    private int currentQuantity;
    private LocalDate dueDate;
}
