package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SectionDTO {
    private Long sectionCode;
    private Long warehouseCode;
}
