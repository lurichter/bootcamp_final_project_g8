package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double minTemperature;
    private Double maxTemperature;
    private Double price;
}