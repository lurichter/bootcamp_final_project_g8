package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BatchResponseDTO {
    private String batchNumber;
    private Integer productId;
    private Double currentTemperature;
    private Double minimumTemperature;
    private Integer quantity;
    private String manufacturingDate;
    private String manufacturingTime;
    private String dueDate;
}