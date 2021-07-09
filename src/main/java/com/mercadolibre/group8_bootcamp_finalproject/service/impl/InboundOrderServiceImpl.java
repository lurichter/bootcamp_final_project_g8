package com.mercadolibre.group8_bootcamp_finalproject.service.impl;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.BatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.BadRequestException;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.ProductNotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.mapper.BatchMapper;
import com.mercadolibre.group8_bootcamp_finalproject.model.*;
import com.mercadolibre.group8_bootcamp_finalproject.repository.BatchRepository;
import com.mercadolibre.group8_bootcamp_finalproject.repository.InboudOrderRepository;
import com.mercadolibre.group8_bootcamp_finalproject.repository.ProductRepository;
import com.mercadolibre.group8_bootcamp_finalproject.repository.WarehouseSectionRepository;
import com.mercadolibre.group8_bootcamp_finalproject.service.IOperatorService;
import com.mercadolibre.group8_bootcamp_finalproject.service.IWarehouseService;
import com.mercadolibre.group8_bootcamp_finalproject.service.InboundOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InboundOrderServiceImpl implements InboundOrderService {

    private final BatchRepository batchRepository;
    private final InboudOrderRepository inboundOrderRepository;
    private final ProductRepository productRepository;
    private final WarehouseSectionRepository warehouseSectionRepository;

    private final IOperatorService operatorService;
    private final IWarehouseService warehouseService;

    @Override
    @Transactional
    public InboundOrderResponseDTO createInboundOrder(InboundOrderRequestDTO inboundOrderRequest) {
        validateCreateInboundOrder(inboundOrderRequest);
        return saveInboundOrder(inboundOrderRequest, new InboundOrder());
    }

    @Override
    @Transactional
    public InboundOrderResponseDTO updateInboundOrder(InboundOrderRequestDTO inboundOrderRequest) {
        InboundOrder inboundOrder = validateUpdateInboundOrder(inboundOrderRequest);
        return saveInboundOrder(inboundOrderRequest, inboundOrder);
    }

    private InboundOrderResponseDTO saveInboundOrder(InboundOrderRequestDTO inboundOrderRequest, InboundOrder inboundOrder) {

        // Get the operator who is doing the request
        Operator operator = operatorService.getLoggedUOperator();

        // Get all the batchStockDTO in request
        List<BatchDTO> batchStockToCreateList = inboundOrderRequest.getInboundOrder().getBatchStock();

        // Get the products Ids from the batches of the request
        List<Long> productIds = getInboundOrderRequestProductIds(batchStockToCreateList);

        // Get the products Entities from the Ids
        List<Product> productList = getProductList(productIds);

        // Get section code from request
        WarehouseSection warehouseSection = getWarehouseSectionByCode(inboundOrderRequest.getInboundOrder()
                .getSection().getSectionCode());

        // Validations
        validateSaveInboundOrder(operator.getId(), warehouseSection, productList,batchStockToCreateList);

        // Prepare the entity to insert in database
        inboundOrder.setOperator(operator);
        inboundOrder.setBatches(BatchMapper.batchStockDTOListToBatchList(inboundOrder, warehouseSection,
                batchStockToCreateList, productList));

        inboundOrder = inboundOrderRepository.save(inboundOrder);

        return new InboundOrderResponseDTO(inboundOrder);
    }

    private void validateSaveInboundOrder(Long operatorId, WarehouseSection warehouseSection,
                                          List<Product> productList, List<BatchDTO> batchStockToSaveList) {

        // Verify if the logged operator is in the informed warehouse
        operatorService.isOperatorInWarehouse(operatorId, warehouseSection.getWarehouse().getId());

        // Verify if the informed Warehouse Section accepts the products categories
        verifySectionAcceptsProductCategory(productList, warehouseSection.getId());

        // Decreases the batch capacity of the Warehouse Section if capable
        int quantity = batchStockToSaveList.stream().mapToInt(BatchDTO::getQuantity).sum();

        warehouseService.decreaseWarehouseSectionCapacity(warehouseSection, quantity);
    }

    private void validateCreateInboundOrder(InboundOrderRequestDTO inboundOrderRequest) {
        // Get all the batchStockDTO IDS in request
        List<Long> batchesIdsToCreate = inboundOrderRequest.getInboundOrder().getBatchStock()
                .stream().map(BatchDTO::getBatchId).collect(Collectors.toList());

        // Verify if some of the informed batches has an ID
        if (batchesIdsToCreate.stream().anyMatch(Objects::nonNull)) throw new BadRequestException("The batches " +
                "to create need to have a null identifier. If you want to update a batch please use the PUT method.");
    }

    private InboundOrder validateUpdateInboundOrder(InboundOrderRequestDTO inboundOrderRequest){
        // Get the Inbound Order to be updated
        InboundOrder inboundOrder = inboundOrderRepository.findById(inboundOrderRequest.getInboundOrder()
                .getInboundOrderId()).orElseThrow(() -> new NotFoundException("Inbound Order not found."));

        // Validate if the Warehouse Section is the same of the inboundOrderRequest
        Long inboundWareHouseSectionId = inboundOrder.getBatches().get(0).getWarehouseSection().getId();
        if(!inboundWareHouseSectionId.equals(inboundOrderRequest.getInboundOrder().getSection().getSectionCode()))
            throw new BadRequestException("Cannot update a batch warehouse section by this method.");

        // Get all the batchStockDTO in request
        List<BatchDTO> batchStockToUpdateList = inboundOrderRequest.getInboundOrder().getBatchStock();

        // Get all the batchStockDTO IDS in request
        List<Long> batchesIdsToUpdate = batchStockToUpdateList.stream().map(BatchDTO::getBatchId)
                .collect(Collectors.toList());

        // Verify if one of the informed batches don't have an ID
        if(batchesIdsToUpdate.stream().anyMatch(Objects::isNull)) throw new BadRequestException("Some Batches to " +
                "update have null identifier.");

        // Get all existing batches from the request ids
        List<Long> existingBatchIds = batchRepository.findAllById(batchesIdsToUpdate).stream()
                .map(Batch::getId).collect(Collectors.toList());

        // Verify if all the informed batches to update exists
        List<Long> unExistingBatchIds = batchesIdsToUpdate.stream().filter(
                batchIdToUpdate -> !existingBatchIds.contains(batchIdToUpdate)).collect(Collectors.toList());
        if (!unExistingBatchIds.isEmpty()) throw new BadRequestException("The batch ids " +
                unExistingBatchIds+" do not exist.");

        // Verify if some of the batches in the request is not of the informed Inbound Order
        List<Long> inboundOrderBatchIds = inboundOrder.getBatches().stream()
                .map(Batch::getId).collect(Collectors.toList());
        List<Long> batchesNotInInboundOrder = existingBatchIds.stream().filter(
                existingBatchId -> !inboundOrderBatchIds.contains(existingBatchId)).collect(Collectors.toList());
        if(!batchesNotInInboundOrder.isEmpty()) throw new BadRequestException("The batch(es) " +
                batchesNotInInboundOrder+" update do not belongs to the informed Inbound Order.");

        // Verify if one of these batches already had a purchase order
        List<Long> batchesIdWithPurchaseOrder = inboundOrder.getBatches().stream().filter(batch ->
                !batch.getPurchaseOrderItems().isEmpty()).map(Batch::getId).collect(Collectors.toList());

        if (CollectionUtils.containsAny(batchesIdsToUpdate, batchesIdWithPurchaseOrder)) throw new
                BadRequestException("The batch(es) "+batchesIdWithPurchaseOrder +" already had a purchase order.");

        return inboundOrder;
    }

    private List<Long> getInboundOrderRequestProductIds(List<BatchDTO> batchStock) {
        return batchStock.stream().map(BatchDTO::getProductId).collect(Collectors.toList());
    }

    private List<Product> getProductList(List<Long> productIds) {
        List<Product> productList = productRepository.findAllById(productIds);

        productIds.removeAll(productList.stream().map(Product::getId).collect(Collectors.toList()));

        if (!productIds.isEmpty()) throw new ProductNotFoundException("The products " + productIds + " was not found.");

        return productList;
    }

    private WarehouseSection getWarehouseSectionByCode(Long sectionCode) {
        return warehouseSectionRepository.findById(sectionCode)
                .orElseThrow(() -> new NotFoundException("Warehouse Section not found"));
    }

    private void verifySectionAcceptsProductCategory(List<Product> productList, Long wareHouseCode) {
        WarehouseSection warehouseSection = getWarehouseSectionByCode(wareHouseCode);
        ProductCategory productCategory = warehouseSection.getProductCategory();

        for (Product product : productList) {
            if (!product.getProductCategory().equals(productCategory)) {
                throw new BadRequestException("Product category is invalid to Warehouse Section Category. productId: "
                        + product.getId() +
                        ", WarehouseSection: " + warehouseSection.getId());
            }
        }
    }

}
