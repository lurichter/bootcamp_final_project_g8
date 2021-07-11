package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class ProductListDTO {
    Set<ProductDTO> products;
}
