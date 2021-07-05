package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.PurchaseOrderPriceResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/fresh-products/orders")
public class PurchaseOrderController {

    @PostMapping
    public ResponseEntity<PurchaseOrderPriceResponseDTO> newPurchaseOrder(@RequestBody @Valid PurchaseOrderRequestDTO purchaseOrderRequestDTO) {
        return ResponseEntity.ok(PurchaseOrderPriceResponseDTO.builder().totalPrice(10.).build());
    }

    @GetMapping(path = "/{idOrder}")
    public ResponseEntity<List<ProductDTO>> getOrders(@PathVariable Long idOrder) {

        System.out.println("==Retornar todos os produtos==");
        return null;
    }

    @PutMapping(path = "/{idOrder}")
    public ResponseEntity<Void> editOrder(@PathVariable Long idOrder) {

        System.out.println("==Retornar todos os produtos==");
        return null;
    }
}