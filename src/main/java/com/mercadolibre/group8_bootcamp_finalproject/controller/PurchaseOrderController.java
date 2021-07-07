package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.PurchaseOrderPriceResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.service.PurchaseOrderService;
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
    private PurchaseOrderService purchaseOrderService;

    @PostMapping
    public ResponseEntity<PurchaseOrderPriceResponseDTO> newPurchaseOrder(@RequestBody @Valid PurchaseOrderRequestDTO purchaseOrderRequestDTO) {
        return new ResponseEntity<>(purchaseOrderService.savePurchaseOrder(purchaseOrderRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{idOrder}")
    public ResponseEntity<Set<ProductDTO>> getOrders(@PathVariable Long idOrder) { //Alterar p/ ProductDTO

        return ResponseEntity.ok(purchaseOrderService.getAllProductsFromPurchaseOrder(idOrder));
    }

    @PutMapping(path = "/{idOrder}")
    public ResponseEntity<PurchaseOrderPriceResponseDTO> editOrder(@RequestBody @Valid PurchaseOrderRequestDTO purchaseOrderRequestDTO, @PathVariable Long idOrder) {

        return new ResponseEntity<>(purchaseOrderService.updatePurchaseOrder(purchaseOrderRequestDTO, idOrder), HttpStatus.CREATED);
    }
}