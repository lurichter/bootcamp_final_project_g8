package com.mercadolibre.group8_bootcamp_finalproject.util;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.BatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.WarehouseSectionDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.InboundOrderRequestDTO;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InboundOrderCreator {

    public static InboundOrderRequestDTO createValidInboundOrderCreateRequest(){
        return new InboundOrderRequestDTO(createInboundOrderToSave());
    }

    public static InboundOrderDTO createInboundOrderToSave(){
        return new InboundOrderDTO(null,
                new WarehouseSectionDTO(1L,1L),
                createValidBatchStockList());
    }

    public static BatchDTO createValidBatchStock(){
        return BatchDTO.builder()
                .batchNumber("BATCH001")
                .productId(1L)
                .currentTemperature(10.)
                .quantity(2)
                .manufacturingDate(LocalDate.now().minusDays(1))
                .manufacturingTime(LocalTime.now())
                .dueDate(LocalDate.now().plusDays(15))
                .build();
    }

    public static BatchDTO createValidBatchStock2(){
        return BatchDTO.builder()
                .batchNumber("BATCH002")
                .productId(7L)
                .currentTemperature(15.)
                .quantity(3)
                .manufacturingDate(LocalDate.now().minusDays(1))
                .manufacturingTime(LocalTime.now())
                .dueDate(LocalDate.now().plusDays(15))
                .build();
    }

    public static List<BatchDTO> createValidBatchStockList(){
        return Arrays.asList(createValidBatchStock(), createValidBatchStock2());
    }

    public static InboundOrderRequestDTO createInvalidSectionCategoryInboundOrderRequest(){
        InboundOrderRequestDTO inboundOrder = new InboundOrderRequestDTO(createInboundOrderToSave());
        inboundOrder.getInboundOrder().getSection().setSectionCode(2L);
        return inboundOrder;
    }

    public static InboundOrderRequestDTO createInvalidSectionCapabilityInboundOrderRequest(){
        InboundOrderRequestDTO inboundOrderRequestDTO = new InboundOrderRequestDTO(createInboundOrderToSave());
        inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(0).setQuantity(999999999);
        return inboundOrderRequestDTO;
    }

    public static InboundOrderRequestDTO createValidInboundOrderUpdateRequest(){
        InboundOrderRequestDTO inboundOrderRequestDTO = new InboundOrderRequestDTO(createInboundOrderToSave());
        InboundOrderDTO inboundOrderDTO  = inboundOrderRequestDTO.getInboundOrder();
        inboundOrderDTO.setInboundOrderId(2L);
        BatchDTO batchDTOToUpdate = createValidBatchStock();
        batchDTOToUpdate.setBatchId(4L);
        batchDTOToUpdate.setQuantity(13);
        inboundOrderDTO.setBatchStock(Collections.singletonList(batchDTOToUpdate));
        return inboundOrderRequestDTO;
    }

    public static InboundOrderRequestDTO createInvalidInboundOrderUpdateRequestBatchWithPurchaseOrder(){
        InboundOrderRequestDTO inboundOrderRequestDTO = createValidInboundOrderUpdateRequest();
        inboundOrderRequestDTO.getInboundOrder().setInboundOrderId(1L);
        inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(0).setBatchId(1L);
        return inboundOrderRequestDTO;
    }

}
