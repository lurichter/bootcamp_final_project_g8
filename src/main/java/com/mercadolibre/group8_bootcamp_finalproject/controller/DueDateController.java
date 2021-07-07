package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductBatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.service.IBatchService;
import com.mercadolibre.group8_bootcamp_finalproject.service.IDueDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products/due-date")
@RequiredArgsConstructor
public class DueDateController {

    private final IDueDateService dueDateService;

    @GetMapping(path = "/{daysQuantity}")
    public ResponseEntity<ProductBatchDTO> listBatchPerWarehouseSectionOrderedByDueDate
            (@RequestParam Integer daysQuantity) {
        return ResponseEntity.ok(dueDateService.listBatchPerWarehouseSectionOrderedByDueDate(daysQuantity));
    }

    @GetMapping(path = "/list/{daysQuantity}")
    public ResponseEntity<ProductBatchDTO> listBatchPerProductCategoryOrderedByDueDate
            (@PathVariable Long productId, @RequestParam(defaultValue = "dueDate_desc") String[] order) {
        return ResponseEntity.ok(dueDateService.listBatchPerProductCategoryOrderedByDueDate(productId, order));
    }
}
