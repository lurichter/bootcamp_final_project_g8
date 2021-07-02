package com.mercadolibre.group8_bootcamp_finalproject.controller;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/fresh-products/inboundorder")
public class InboundOrderController {

    @PostMapping
    public void createInboundOrder(@RequestBody @Valid InboundOrderDTO inboundOrder) {
        System.out.println(inboundOrder);
    }
}