package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.service.IWarehouseService;
import com.mercadolibre.group8_bootcamp_finalproject.service.impl.WarehouseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final IWarehouseService warehouseService;

    @GetMapping(path = "/{productId}")
    public WarehouseProductListDTO findAllProductsFromWarehouseById (@PathVariable Long productId) {
        return warehouseService.findAllProductsFromWarehouseById(productId);
    }
}
