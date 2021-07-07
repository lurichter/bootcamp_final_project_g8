package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import com.mercadolibre.group8_bootcamp_finalproject.service.IDueDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products/due-date")
@RequiredArgsConstructor
public class DueDateController {

    private final IDueDateService dueDateService;

    @GetMapping(path = "/list/{daysQuantity}")
    public ResponseEntity<BatchStockDueDateListDTO> listBatchPerProductCategoryOrderedByDueDate
            (@PathVariable Integer daysQuantity, @RequestParam(required = false) ProductCategoryEnum productCategory,
             @RequestParam(defaultValue = "dueDate_desc") String[] order) {
        return ResponseEntity.ok(dueDateService.listBatchesOrderedByDueDate(daysQuantity, productCategory, order));
    }
}
