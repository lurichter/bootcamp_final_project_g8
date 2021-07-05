package com.mercadolibre.group8_bootcamp_finalproject.services;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.BatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderRequest;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchResponseListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.*;
import com.mercadolibre.group8_bootcamp_finalproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InboundOrderServiceImpl implements InboundOrderService {

	private final BatchRepository batchRepository;
	private final InboudOrderRepository inboundOrderRepository;
	private final ProductRepository productRepository;
	private final WarehouseSectionRepository warehouseSectionRepository;
	private final WarehouseOperatorRepository warehouseOperatorRepository;

    @Override
    @Transactional
    public BatchResponseListDTO createInboundOrder(InboundOrderRequest inboundOrderRequest) {

        // verify if seller exists
        List<Integer> productIds = inboundOrderRequest.getInboundOrder()
                                .getBatchStock()
                                .stream().map(BatchDTO::getProductId).collect(Collectors.toList());

        List<Product> productList = new ArrayList<>();

        productIds.stream().map( product_id ->
            productList.add(productRepository.findById(product_id.longValue()).orElseThrow(() -> new NotFoundException("Product not found")))
        );

        // get section code -> returning data from Warehouse_Section (table)
        WarehouseSection warehouseSection = warehouseSectionRepository
                                .findById(inboundOrderRequest.getInboundOrder().getSection().getSectionCode().longValue())
                                .orElseThrow(() -> new NotFoundException("Warehouse Section not found"));

        // verify if operator exists


        // with section data, we can get Warehouse (table) code
        Long wareHouseCode = warehouseSection.getWarehouse().getId();

        // validating in Warehouse_Operator has access to determinate warehouse
        List<WarehouseOperator> warehouseOperatorList = warehouseOperatorRepository.findByWarehouseCode(wareHouseCode);

        // verify section accepts product category
        productList.stream().map(product ->
                (product.getProductCategory() == warehouseSection.getProductCategory()) ?
                        null :
                        new NotFoundException("Product category is invalid to Warehouse Section Category. productId: "
                                + product.getId()  +
                                ", WarehouseSection: " + warehouseSection.getWarehouse().getId())
        );


        // verify quantity from request is less than warehouse section current_availability
        Integer allQuantityProductBatchStock = inboundOrderRequest.getInboundOrder().getBatchStock().stream().mapToInt(BatchDTO::getQuantity).sum();

        if ( warehouseSection.getCurrent_availability() <= allQuantityProductBatchStock ) {
            throw new NotFoundException("WarehouseSection current capability is less than all quantity products from batch stock");
        }

        // NÃO PRECISA MAIS - validate if section accept product category and section temperature in range than batch current_temperature

        // Create object type of Batch and insert on table
        InboundOrder inboundOrder = new InboundOrder();

        inboundOrder.setOperator(warehouseOperatorList.get(0).getOperator());
        InboundOrder inboundOrderResponse = inboundOrderRepository.save(inboundOrder);

        // -----------

        List<Batch> batches = new ArrayList<>();
        Batch batch;

        for ( BatchDTO batchRequest : inboundOrderRequest.getInboundOrder().getBatchStock() ) {
            batch = new Batch();

            batch.setNumber(batchRequest.getBatchNumber());
            batch.setProduct(productRepository.getOne(batchRequest.getProductId().longValue()));
            batch.setQuantity(batchRequest.getQuantity());
            batch.setCurrent_temperature(batchRequest.getCurrentTemperature());
            batch.setManufacturing_date(batchRequest.getManufacturingDate());
            batch.setManufacturing_time(batchRequest.getManufacturingTime().toLocalTime());
            batch.setDue_date(batchRequest.getDueDate());
            batch.setInboundOrder(inboundOrderResponse);
            batch.setWarehouseSection(warehouseSection);

            batches.add(batch);
        }

        List<Batch> batchesResponse = batchRepository.saveAll(batches);

        return new BatchResponseListDTO(batchesResponse);
    }

    @Override
    public BatchResponseDTO updateInboundOrder(InboundOrderRequest inboundOrderRequest) {
        return null;
    }


}
