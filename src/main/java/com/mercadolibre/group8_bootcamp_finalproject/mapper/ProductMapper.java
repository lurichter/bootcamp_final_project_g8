package com.mercadolibre.group8_bootcamp_finalproject.mapper;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.Product;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public abstract class ProductMapper {

    public static Set<ProductDTO> convertProductListToProductDTOList(List<Product> products){
        Set<ProductDTO> productDTOS = new LinkedHashSet<>();
        for(Product product: products){
            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .minTemperature(product.getMinTemperature())
                    .maxTemperature(product.getMaxTemperature())
                    .price(product.getPrice())
                    .build();
            productDTOS.add(productDTO);
        }

        return productDTOS;
    }
}
