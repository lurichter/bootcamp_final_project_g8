package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderRequest;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.services.InboundOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fresh-products/inboundorder")
@RequiredArgsConstructor
public class InboundOrderController {

    private final InboundOrderService inboundOrderService;

    @PostMapping
    public ResponseEntity<BatchResponseDTO> createInboundOrder(@RequestBody @Valid InboundOrderRequest inboundOrder) {
        return new ResponseEntity(inboundOrderService.createInboundOrder(inboundOrder), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BatchResponseDTO> updateInboundOrder (@RequestBody @Valid InboundOrderRequest inboundOrder) {

        return new ResponseEntity(inboundOrderService.updateInboundOrder(inboundOrder), HttpStatus.CREATED);
    }
}