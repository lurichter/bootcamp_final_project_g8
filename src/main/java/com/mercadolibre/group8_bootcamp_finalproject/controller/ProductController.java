package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import com.mercadolibre.group8_bootcamp_finalproject.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products/")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;

    @GetMapping
    public ResponseEntity<ProductListDTO> listAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping(path = "/list/{productCategory}")
    public ResponseEntity<ProductListDTO> listProductsByCategory(@PathVariable ProductCategoryEnum productCategory) {
        return ResponseEntity.ok(productService.getAllProductsByCategory(productCategory));
    }

    @PatchMapping("/promo/{productId}/{percentValue}")
    public ResponseEntity<ProductDTO> updateProductPrice (@PathVariable Long productId, @PathVariable Double percentValue) {
        return ResponseEntity.ok(productService.updateProductPrice(productId, percentValue));
    }

}