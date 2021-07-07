package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchResponseDTO {
    private String batchNumber;
    private Integer productId;
    private Double currentTemperature;
//    private Double minimumTemperature;
    private Integer quantity;
    private LocalDate manufacturingDate;
    private LocalTime manufacturingTime;
    private LocalDate dueDate;

}