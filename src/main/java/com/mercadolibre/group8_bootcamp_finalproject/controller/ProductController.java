package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/")
public class ProductController {

    @GetMapping
    public ResponseEntity<List<ProductDTO>> listAllProducts() {
        return new ResponseEntity<>(createProductsForTest(), HttpStatus.OK);
    }

    @GetMapping(path = "/list")
    public ResponseEntity<List<ProductDTO>> listProducts(@RequestParam String productCategory) {
        return new ResponseEntity<>(createProductsForTest(), HttpStatus.OK);
    }

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
}