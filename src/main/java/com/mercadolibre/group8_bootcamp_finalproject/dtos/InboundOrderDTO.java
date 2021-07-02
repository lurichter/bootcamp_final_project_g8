package com.mercadolibre.group8_bootcamp_finalproject.dtos;

import lombok.Data;

import javax.validation.Valid;
import java.util.List;

@Data
public class InboundOrderDTO {

//    @NotNull(message = "order number is required")
//    private Integer orderNumber;
//    private LocalDate orderDate;
    @Valid
    private WarehouseSectionDTO section;

    @Valid
    private List<BatchDTO> batchStock;
}