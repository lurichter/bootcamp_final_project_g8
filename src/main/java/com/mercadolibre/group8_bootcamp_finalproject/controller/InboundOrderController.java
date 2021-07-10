package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.service.InboundOrderService;
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
    public ResponseEntity<InboundOrderResponseDTO> createInboundOrder(@RequestBody @Valid InboundOrderRequestDTO inboundOrder) {
        return new ResponseEntity<>(inboundOrderService.createInboundOrder(inboundOrder), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<InboundOrderResponseDTO> updateInboundOrder (@RequestBody @Valid InboundOrderRequestDTO inboundOrder) {
        return new ResponseEntity<>(inboundOrderService.updateInboundOrder(inboundOrder), HttpStatus.CREATED);
    }
}