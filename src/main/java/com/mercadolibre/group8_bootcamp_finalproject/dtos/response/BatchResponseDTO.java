package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class BatchResponseDTO {

    private String batchNumber;
    private Integer productId;
    private Double currentTemperature;
    private Double minimumTemperature;
    private Integer quantity;
    private LocalDate manufacturingDate;
    private LocalDateTime manufacturingTime;
    private LocalDate dueDate;

}