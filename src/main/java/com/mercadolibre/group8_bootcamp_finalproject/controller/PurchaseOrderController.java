package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.PurchaseOrderPriceResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.service.impl.PurchaseOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/fresh-products/orders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderServiceImpl purchaseOrderServiceImpl;

    @PostMapping
    public ResponseEntity<PurchaseOrderPriceResponseDTO> newPurchaseOrder(@RequestBody @Valid PurchaseOrderRequestDTO purchaseOrderRequestDTO) {
        return new ResponseEntity<>(purchaseOrderServiceImpl.savePurchaseOrder(purchaseOrderRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{idOrder}")
    public ResponseEntity<Set<ProductDTO>> getOrders(@PathVariable Long idOrder) { //Alterar p/ ProductDTO

        return ResponseEntity.ok(purchaseOrderServiceImpl.getAllProductsFromPurchaseOrder(idOrder));
    }

    @PutMapping(path = "/{idOrder}")
    public ResponseEntity<PurchaseOrderPriceResponseDTO> editOrder(@RequestBody @Valid PurchaseOrderRequestDTO purchaseOrderRequestDTO, @PathVariable Long idOrder) {

        return new ResponseEntity<>(purchaseOrderServiceImpl.updatePurchaseOrder(purchaseOrderRequestDTO, idOrder), HttpStatus.CREATED);
    }
}