package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BatchDTO {

    @NotNull(message = "batch number is required")
    private String batchNumber;

    @NotNull(message = "product id is required")
    private Integer productId;

    @NotNull(message = "current temperature is required")
    private Double currentTemperature;

    @NotNull(message = "minimum temperature is required")
    private Double minimumTemperature;

    @NotNull(message = "quantity is required")
    private Integer quantity;

    @PastOrPresent(message = "manufacturing date is in the future")
    private LocalDate manufacturingDate;

    @PastOrPresent(message = "manufacturing date is in the future")
    private LocalDateTime manufacturingTime;

    @FutureOrPresent(message = "due date is in the past")
    private LocalDate dueDate;
}