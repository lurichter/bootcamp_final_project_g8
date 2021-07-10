package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import com.mercadolibre.group8_bootcamp_finalproject.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/fresh-products/")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<Set<ProductDTO>> listAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping(path = "/list/{productCategory}")
    public ResponseEntity<Set<ProductDTO>> listProductsByCategory(@PathVariable ProductCategoryEnum productCategory) {
        return ResponseEntity.ok(productService.getAllProductsByCategory(productCategory));
    }

}