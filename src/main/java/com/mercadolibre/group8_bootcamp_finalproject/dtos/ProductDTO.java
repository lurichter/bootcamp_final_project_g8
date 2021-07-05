package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double minTemperature;
    private Double maxTemperature;
    private Long batchNumber;
    private LocalDateTime dueDate;
    private Double price;
}