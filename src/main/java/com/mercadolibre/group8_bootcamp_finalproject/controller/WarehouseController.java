package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.service.impl.WarehouseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products/warehouse")
public class WarehouseController {

    @Autowired
    WarehouseServiceImpl warehouseService;

    @GetMapping
    public WarehouseProductListDTO findAllProductsFromWarehouseById (@RequestParam Integer querytype) {
        return warehouseService.findAllProductsFromWarehouseById(querytype);
    }
}
