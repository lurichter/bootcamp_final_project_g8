package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import com.mercadolibre.group8_bootcamp_finalproject.model.Batch;
import com.mercadolibre.group8_bootcamp_finalproject.model.InboundOrder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InboundOrderResponseDTO {

    private Long inboundOrderId;

    private List<BatchResponseDTO> batchStock;

    public InboundOrderResponseDTO(InboundOrder inboundOrder) {

        this.inboundOrderId = inboundOrder.getId();

        List<BatchResponseDTO> batchResponseDTOList = new ArrayList<>();

        for ( Batch batch : inboundOrder.getBatches() ) {

            BatchResponseDTO batchResponseDTO = BatchResponseDTO.builder()
                    .batchId(batch.getId())
                    .batchNumber(batch.getNumber())
                    .productId(batch.getProduct().getId().intValue())
                    .currentTemperature(batch.getCurrentTemperature())
                    .minimumTemperature(batch.getProduct().getMinTemperature())
                    .quantity(batch.getQuantity())
                    .manufacturingDate(batch.getManufacturingDate())
                    .manufacturingTime(batch.getManufacturingTime())
                    .dueDate(batch.getDueDate())
                    .build();

            batchResponseDTOList.add(batchResponseDTO);
        }
        this.batchStock = batchResponseDTOList;
    }
}
