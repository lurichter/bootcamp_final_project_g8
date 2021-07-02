package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.BatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderRequest;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/fresh-products/inboundorder")
public class InboundOrderController {

    @PostMapping
    public ResponseEntity<BatchResponseDTO> createInboundOrder(@RequestBody @Valid InboundOrderRequest inboundOrder) {
        return null;
    }
}