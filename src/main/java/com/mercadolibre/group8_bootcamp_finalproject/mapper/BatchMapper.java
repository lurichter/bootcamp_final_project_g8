package com.mercadolibre.group8_bootcamp_finalproject.mapper;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.BatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.ProductNotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.Batch;
import com.mercadolibre.group8_bootcamp_finalproject.model.InboundOrder;
import com.mercadolibre.group8_bootcamp_finalproject.model.Product;
import com.mercadolibre.group8_bootcamp_finalproject.model.WarehouseSection;

import java.util.ArrayList;
import java.util.List;

public abstract class BatchMapper {

    public static List<Batch> batchStockDTOListToBatchList(InboundOrder inboundOrder, WarehouseSection warehouseSection,
                                                     List<BatchDTO> batchStockList, List<Product> productList) {
        Batch batch;
        List<Batch> batches = new ArrayList<>();
        for (BatchDTO batchRequest : batchStockList) {
            batch = Batch.builder()
                    .inboundOrder(inboundOrder)
                    .warehouseSection(warehouseSection)
                    .id(batchRequest.getBatchId())
                    .number(batchRequest.getBatchNumber())
                    .quantity(batchRequest.getQuantity())
                    .currentTemperature(batchRequest.getCurrentTemperature())
                    .manufacturingDate(batchRequest.getManufacturingDate())
                    .manufacturingTime(batchRequest.getManufacturingTime())
                    .dueDate(batchRequest.getDueDate())
                    .product(
                            productList.stream().filter(product ->
                                    product.getId().equals(batchRequest.getProductId())).findFirst()
                                    .orElseThrow(ProductNotFoundException::new))
                    .build();

            batches.add(batch);
        }
        return batches;
    }
}
