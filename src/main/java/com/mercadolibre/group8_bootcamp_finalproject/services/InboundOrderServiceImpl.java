package com.mercadolibre.group8_bootcamp_finalproject.services;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.BatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchResponseListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.*;
import com.mercadolibre.group8_bootcamp_finalproject.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InboundOrderServiceImpl implements InboundOrderService {

	private final BatchRepository batchRepository;
	private final InboudOrderRepository inboundOrderRepository;
	private final ProductRepository productRepository;
	private final WarehouseSectionRepository warehouseSectionRepository;
	private final WarehouseOperatorRepository warehouseOperatorRepository;

	// put method
    private final PurchaseOrderItemRepository purchaseOrderItemRepository;

	private List<Product> getProductList (List<Integer> productIds) {
        List<Product> productList = new ArrayList<>();

        productIds.stream().map( product_id ->
                productList.add(productRepository.findById(product_id.longValue()).orElseThrow(() -> new NotFoundException("Product not found")))
        );

        return productList;
    }

    private WarehouseSection getWarehouseSectionByCode ( Long sectionCode ) {
	    return warehouseSectionRepository
                .findById(sectionCode)
                .orElseThrow(() -> new NotFoundException("Warehouse Section not found"));
    }
    // verify section accepts product category

    private void verifySectionAcceptsProductCategory (List<Product> productList, Long wareHouseCode) {
        productList.stream().map(product ->
                (product.getProductCategory() == this.getWarehouseSectionByCode(wareHouseCode).getProductCategory()) ?
                        null :
                        new NotFoundException("Product category is invalid to Warehouse Section Category. productId: "
                                + product.getId()  +
                                ", WarehouseSection: " + this.getWarehouseSectionByCode(wareHouseCode).getWarehouse().getId())
        );
    }

    private Integer verifySumOfProductsQuantitiesIsLessThanWarehouseSectionCapability (InboundOrderRequestDTO inboundOrderRequest) {
        return inboundOrderRequest.getInboundOrder().getBatchStock().stream().mapToInt(BatchDTO::getQuantity).sum();
    }

    @Override
    @Transactional
    public BatchResponseListDTO createInboundOrder(InboundOrderRequestDTO inboundOrderRequest) {

        // verify if seller exists
        List<Integer> productIds = inboundOrderRequest.getInboundOrder()
                                .getBatchStock()
                                .stream().map(BatchDTO::getProductId).collect(Collectors.toList());

        List<Product> productList = this.getProductList(productIds);

        // get section code -> returning data from Warehouse_Section (table)
        WarehouseSection warehouseSection = this.getWarehouseSectionByCode(inboundOrderRequest.getInboundOrder().getSection().getSectionCode().longValue());

        // verify if operator exists

        // with section data, we can get Warehouse (table) code
        Long wareHouseCode = warehouseSection.getWarehouse().getId();

        // validating in Warehouse_Operator has access to determinate warehouse
        List<WarehouseOperator> warehouseOperatorList = warehouseOperatorRepository.findByWarehouseCode(wareHouseCode);

        // verify section accepts product category
        this.verifySectionAcceptsProductCategory(productList, inboundOrderRequest.getInboundOrder().getSection().getSectionCode().longValue());

        // verify quantity from request is less than warehouse section current_availability
        Integer allQuantityProductBatchStock = this.verifySumOfProductsQuantitiesIsLessThanWarehouseSectionCapability(inboundOrderRequest);

        if ( warehouseSection.getCurrentAvailability() <= allQuantityProductBatchStock ) {
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
            batch.setCurrentTemperature(batchRequest.getCurrentTemperature());
            batch.setManufacturingDate(batchRequest.getManufacturingDate());
            batch.setManufacturingTime(batchRequest.getManufacturingTime());
            batch.setDueDate(batchRequest.getDueDate());
            batch.setInboundOrder(inboundOrderResponse);
            batch.setWarehouseSection(warehouseSection);

            batches.add(batch);
        }

        List<Batch> batchesResponse = batchRepository.saveAll(batches);

        return new BatchResponseListDTO(batchesResponse);
    }

    @Override
    public BatchResponseListDTO updateInboundOrder(InboundOrderRequestDTO inboundOrderRequest) {

        List<Batch> batches = new ArrayList<>();
        List<Batch> batchesResponse = new ArrayList<>();
        Batch batch;

	    for ( BatchDTO batchRequest : inboundOrderRequest.getInboundOrder().getBatchStock() ) {

            List<PurchaseOrderItem> p = purchaseOrderItemRepository.findByBatchId(batchRequest.getBatchId().longValue());

            if (!p.isEmpty()) {
                // already some occurence
                // type some exception here
                System.out.println("entrou no if, pois tem registro");
                return null;
            } else {

                // verify if seller exists
                List<Integer> productIds = inboundOrderRequest.getInboundOrder()
                        .getBatchStock()
                        .stream().map(BatchDTO::getProductId).collect(Collectors.toList());

                List<Product> productList = this.getProductList(productIds);


                // get section code -> returning data from Warehouse_Section (table)
                WarehouseSection warehouseSection = this.getWarehouseSectionByCode(inboundOrderRequest.getInboundOrder().getSection().getSectionCode().longValue());

                // verify if operator exists


                // with section data, we can get Warehouse (table) code
                Long wareHouseCode = warehouseSection.getWarehouse().getId();

                // validating in Warehouse_Operator has access to determinate warehouse
                List<WarehouseOperator> warehouseOperatorList = warehouseOperatorRepository.findByWarehouseCode(wareHouseCode);

                // verify section accepts product category
                this.verifySectionAcceptsProductCategory(productList, inboundOrderRequest.getInboundOrder().getSection().getSectionCode().longValue());

                // verify quantity from request is less than warehouse section current_availability
                Integer allQuantityProductBatchStock = this.verifySumOfProductsQuantitiesIsLessThanWarehouseSectionCapability(inboundOrderRequest);

                if ( warehouseSection.getCurrentAvailability() <= allQuantityProductBatchStock ) {
                    throw new NotFoundException("WarehouseSection current capability is less than all quantity products from batch stock");
                }

                // get inbound order request
                Long inboundOrderId = batchRepository.getOne(batchRequest.getBatchId().longValue()).getInboundOrder().getId();

                InboundOrder inboundOrderFromBatch = inboundOrderRepository.getOne(inboundOrderId);

                batch = new Batch();

                batch.setId(batchRequest.getBatchId().longValue());
                batch.setNumber(batchRequest.getBatchNumber());
                batch.setProduct(productRepository.getOne(batchRequest.getProductId().longValue()));
                batch.setQuantity(batchRequest.getQuantity());
                batch.setCurrentTemperature(batchRequest.getCurrentTemperature());
                batch.setManufacturingDate(batchRequest.getManufacturingDate());
                batch.setManufacturingTime(batchRequest.getManufacturingTime());
                batch.setDueDate(batchRequest.getDueDate());
                batch.setInboundOrder(inboundOrderFromBatch);
                batch.setWarehouseSection(warehouseSection);

                batches.add(batch);

            }
            batchesResponse = batchRepository.saveAll(batches);

        }
        return new BatchResponseListDTO(batchesResponse);
    }


}
