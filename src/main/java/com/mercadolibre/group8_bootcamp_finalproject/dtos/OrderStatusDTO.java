package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import com.mercadolibre.group8_bootcamp_finalproject.model.enums.OrderStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderStatusDTO {

    @NotNull
    private OrderStatusEnum statusCode;
}