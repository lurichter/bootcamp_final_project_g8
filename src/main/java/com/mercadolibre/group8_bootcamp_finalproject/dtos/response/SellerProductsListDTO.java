package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerProductsListDTO {
    private Long sellerId;
    private List<SellerProductDTO> products;
}
