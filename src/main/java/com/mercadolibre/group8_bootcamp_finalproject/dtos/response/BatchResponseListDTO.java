package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import com.mercadolibre.group8_bootcamp_finalproject.model.Batch;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BatchResponseListDTO {

    private List<BatchResponseDTO> batchStock;

    public BatchResponseListDTO (List<Batch> batches) {

        List<BatchResponseDTO> batchResponseDTOList = new ArrayList<>();

        for ( Batch batch : batches ) {

            BatchResponseDTO batchResponseDTO = BatchResponseDTO.builder()
                    .batchNumber(batch.getNumber())
                    .productId(batch.getProduct().getId().intValue())
                    .currentTemperature(batch.getCurrentTemperature())
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
