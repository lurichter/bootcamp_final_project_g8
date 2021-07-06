package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.PurchaseOrderPriceResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.Product;
import com.mercadolibre.group8_bootcamp_finalproject.service.FreshProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/fresh-products/orders")
public class PurchaseOrderController {

    @Autowired
    private FreshProductsService freshProductsService;

    @PostMapping
    public ResponseEntity<PurchaseOrderPriceResponseDTO> newPurchaseOrder(@RequestBody @Valid PurchaseOrderRequestDTO purchaseOrderRequestDTO) {
        return ResponseEntity.ok(freshProductsService.savePurchaseOrder(purchaseOrderRequestDTO));
    }

    @GetMapping(path = "/{idOrder}")
    public ResponseEntity<Set<ProductDTO>> getOrders(@PathVariable Long idOrder) { //Alterar p/ ProductDTO

        return ResponseEntity.ok(freshProductsService.getAllProductsFromPurchaseOrder(idOrder));
    }

    @PutMapping(path = "/{idOrder}")
    public ResponseEntity<Void> editOrder(@PathVariable Long idOrder) {

        System.out.println("==Retornar todos os produtos==");
        return null;
    }
}