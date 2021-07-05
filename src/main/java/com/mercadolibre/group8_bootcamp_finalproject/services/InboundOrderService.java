package com.mercadolibre.group8_bootcamp_finalproject.services;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.BatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderRequest;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchResponseListDTO;

public interface InboundOrderService {

    BatchResponseListDTO createInboundOrder (InboundOrderRequest inboundOrderRequest);

    BatchResponseDTO updateInboundOrder (InboundOrderRequest inboundOrderRequest);
}
