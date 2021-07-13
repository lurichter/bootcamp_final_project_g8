package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductBatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.service.IBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fresh-products/batch")
@RequiredArgsConstructor
public class BatchController {

    private final IBatchService batchService;

    @GetMapping(path = "/list/{productId}")
    public ResponseEntity<ProductBatchDTO> listProductPerBatch(@PathVariable Long productId, @RequestParam(defaultValue = "dueDate_desc") String[] order) {
        return ResponseEntity.ok(batchService.listProductBatches(productId, order));
    }


    @DeleteMapping(path = "/{batchId}/delete")
    public BatchStockDTO deleteExpiredBatch(@PathVariable Long batchId){
        return batchService.removeExpiredBatch(batchId);
    }
}
