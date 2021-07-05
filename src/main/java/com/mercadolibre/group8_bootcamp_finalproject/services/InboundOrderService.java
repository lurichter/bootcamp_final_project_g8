package com.mercadolibre.group8_bootcamp_finalproject.services;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchResponseListDTO;

public interface InboundOrderService {

    BatchResponseListDTO createInboundOrder (InboundOrderRequestDTO inboundOrderRequest);

    BatchResponseListDTO updateInboundOrder (InboundOrderRequestDTO inboundOrderRequest);
}
