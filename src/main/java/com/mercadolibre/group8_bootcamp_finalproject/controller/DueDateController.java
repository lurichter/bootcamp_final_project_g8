package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.service.IDueDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/fresh-products/due-date")
@RequiredArgsConstructor
public class DueDateController {

    private final IDueDateService dueDateService;

// Obtenha todos os lotes armazenados em um setor de um armazém ordenados por sua data de vencimento.
//    warehouse code
//    sectionId no DTO
    @GetMapping(path = "/{daysQuantity}")
    public ResponseEntity<BatchStockDueDateListDTO> listBatchPerWarehouseSectionOrderedByDueDate
            (@PathVariable Integer daysQuantity,
             @RequestParam(defaultValue = "dueDate_desc") String[] order) {
        return ResponseEntity.ok(dueDateService.listBatchPerWarehouseSectionOrderedByDueDate(daysQuantity, order));
    }

//Obtenha uma lista de lotes ordenados por data de validade, que pertencem a uma determinada categoria de produto.
//    request days, category, order
    @GetMapping(path = "/list/{daysQuantity}/{productCategory}/")
    public ResponseEntity<BatchStockDueDateListDTO> listBatchPerProductCategoryOrderedByDueDate
            (HttpServletRequest request, @PathVariable Integer daysQuantity, @PathVariable String productCategory,
             @RequestParam(defaultValue = "dueDate_desc") String[] order) {
//        HttpServletRequest req = request.getHeader("Authorization");
        return ResponseEntity.ok(dueDateService.listBatchPerProductCategoryOrderedByDueDate(daysQuantity, productCategory, order));
    }
}
