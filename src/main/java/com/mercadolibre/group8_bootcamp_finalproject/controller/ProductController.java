package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import com.mercadolibre.group8_bootcamp_finalproject.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/fresh-products/")
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;

    @GetMapping
    public ResponseEntity<Set<ProductDTO>> listAllProducts() {
        return new ResponseEntity<>(productServiceImpl.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping(path = "/list/{productCategory}")
    public ResponseEntity<Set<ProductDTO>> listProductsByCategory(@PathVariable ProductCategoryEnum productCategory) {
        return new ResponseEntity<>(productServiceImpl.getAllProductsByCategory(productCategory), HttpStatus.OK);
    }
    /*
    public List<ProductDTO> createProductsForTest(){
        ProductDTO product = ProductDTO.builder()
                .id(1L)
                .batchNumber(24L)
                .description("AAAAA")
                .dueDate(LocalDateTime.now())
                .maxTemperature(2.)
                .minTemperature(1.)
                .price(50.25)
                .build();

        ProductDTO product2 = ProductDTO.builder()
                .id(2L)
                .batchNumber(24L)
                .description("AAAAA")
                .dueDate(LocalDateTime.now())
                .maxTemperature(2.)
                .minTemperature(1.)
                .price(50.25)
                .build();

        return Arrays.asList(product, product2);
    }
     */
}