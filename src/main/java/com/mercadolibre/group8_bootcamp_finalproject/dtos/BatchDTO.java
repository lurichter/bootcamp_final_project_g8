package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class BatchDTO {

    private Long batchId;

    @NotNull(message = "batch number is required")
    private String batchNumber;

    @NotNull(message = "product id is required")
    private Long productId;

    @NotNull(message = "current temperature is required")
    private Double currentTemperature;

//    @NotNull(message = "minimum temperature is required")
//    private Double minimumTemperature;

    @NotNull(message = "quantity is required")
    @Min(value = 1, message = "The inbound batch stock quantity must be greater than 0.")
    private Integer quantity;

    @PastOrPresent(message = "manufacturing date is in the future")
    private LocalDate manufacturingDate;

//    precisa checar se é no futuro junto com a data
    private LocalTime manufacturingTime;

    @FutureOrPresent(message = "due date is in the past")
    private LocalDate dueDate;
}