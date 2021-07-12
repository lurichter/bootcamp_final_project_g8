package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.InboundOrderResponseDTO;

public interface InboundOrderService {

    InboundOrderResponseDTO createInboundOrder (InboundOrderRequestDTO inboundOrderRequest);

    InboundOrderResponseDTO updateInboundOrder (InboundOrderRequestDTO inboundOrderRequest);
}
