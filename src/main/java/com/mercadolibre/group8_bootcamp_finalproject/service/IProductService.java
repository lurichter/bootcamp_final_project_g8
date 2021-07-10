package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;

import java.util.Set;

public interface IProductService {

    Set<ProductDTO> getAllProducts();

    Set<ProductDTO> getAllProductsByCategory(ProductCategoryEnum category);
}
