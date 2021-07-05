package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import com.mercadolibre.group8_bootcamp_finalproject.model.enums.OrderStatusEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class OrderStatusDTO {

    @NotNull
    private OrderStatusEnum statusCode;
}