package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductBatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.SectionDTO;
import com.mercadolibre.group8_bootcamp_finalproject.service.IBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;

@RestController
@RequestMapping("/api/v1/fresh-products/batch")
@RequiredArgsConstructor
public class BatchController {

    private final IBatchService batchService;

    @GetMapping(path = "/list/{productId}")
    public ResponseEntity<ProductBatchDTO> listProductPerBatch(@PathVariable Long productId) {
        return ResponseEntity.ok(batchService.listProductBatches(productId));
    }

    @GetMapping(path = "/list/{productId}/{orderType}")
    public ResponseEntity<ProductBatchDTO> listProductPerBatchOrderedByQuantityOrDueDate
            (@PathVariable String productId, @PathVariable String orderType) {
        return ResponseEntity.ok(createProductsForTest());
    }

    public ProductBatchDTO createProductsForTest(){
       return ProductBatchDTO.builder()
                .section(SectionDTO.builder().sectionCode(123L).warehouseCode(456L).build())
                .productId(789L)
                .batchStock(Collections.singletonList(BatchStockDTO.builder().batchNumber(123L).currentQuantity(2).dueDate(LocalDate.now()).build()))
                .build();
    }
}
