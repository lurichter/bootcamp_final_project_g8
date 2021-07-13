package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.PurchaseOrderPriceResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.service.impl.PurchaseOrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fresh-products/orders")
@RequiredArgsConstructor
public class PurchaseOrderController {

    private final PurchaseOrderServiceImpl purchaseOrderServiceImpl;

    @PostMapping
    public ResponseEntity<PurchaseOrderPriceResponseDTO> newPurchaseOrder(@RequestBody @Valid PurchaseOrderRequestDTO purchaseOrderRequestDTO) {
        return new ResponseEntity<>(purchaseOrderServiceImpl.savePurchaseOrder(purchaseOrderRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{idOrder}")
    public ResponseEntity<ProductListDTO> getOrders(@PathVariable Long idOrder) {
        return ResponseEntity.ok(purchaseOrderServiceImpl.getAllProductsFromPurchaseOrder(idOrder));
    }

    @PutMapping(path = "/{idOrder}")
    public ResponseEntity<PurchaseOrderPriceResponseDTO> editOrder(@RequestBody @Valid PurchaseOrderRequestDTO purchaseOrderRequestDTO, @PathVariable Long idOrder) {
        return new ResponseEntity<>(purchaseOrderServiceImpl.updatePurchaseOrder(purchaseOrderRequestDTO, idOrder), HttpStatus.CREATED);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(path = "/cancel/{idOrder}")
    public void cancelOrder(@PathVariable Long idOrder) {
        purchaseOrderServiceImpl.cancelPurchaseOrder(idOrder);
    }
}