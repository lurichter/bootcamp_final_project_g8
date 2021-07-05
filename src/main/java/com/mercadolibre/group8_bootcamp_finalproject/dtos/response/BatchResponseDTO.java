package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mercadolibre.group8_bootcamp_finalproject.model.Batch;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class BatchResponseDTO {
    private String batchNumber;
    private Integer productId;
    private Double currentTemperature;
    private Double minimumTemperature;
    private Integer quantity;
    private LocalDate manufacturingDate;
    private LocalTime manufacturingTime;
    private LocalDate dueDate;


}