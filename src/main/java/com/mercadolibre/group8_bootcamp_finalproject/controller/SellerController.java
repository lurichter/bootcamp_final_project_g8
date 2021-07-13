package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.SellerProductsListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.service.ISellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fresh-products/seller")
@RequiredArgsConstructor
public class SellerController {

    private final ISellerService sellerService;

    @GetMapping(path = "/list/{sellerId}")
    public ResponseEntity<SellerProductsListDTO> listAllProductsFromSeller
            (@PathVariable Long sellerId) {
        return ResponseEntity.ok(sellerService.listAllProductsFromSeller(sellerId));
    }
}