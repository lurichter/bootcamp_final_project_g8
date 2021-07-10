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

        Operator operator = operatorService.getLoggedUOperator();

        List<BatchDTO> batchStockToSaveList = inboundOrderRequest.getInboundOrder().getBatchStock();

        List<Long> productIds = getInboundOrderRequestProductIds(batchStockToSaveList);

        List<Product> productList = getProductList(productIds);

        WarehouseSection warehouseSection = getWarehouseSectionByCode(inboundOrderRequest.getInboundOrder()
                .getSection().getSectionCode());

        validateSaveInboundOrder(operator.getId(), warehouseSection, productList);

        int quantity = batchStockToSaveList.stream().mapToInt(BatchDTO::getQuantity).sum();

        warehouseService.decreaseWarehouseSectionCapacity(warehouseSection, quantity);

        inboundOrder.setOperator(operator);
        inboundOrder.setBatches(BatchMapper.batchStockDTOListToBatchList(inboundOrder, warehouseSection,
                batchStockToSaveList, productList));

        inboundOrder = inboundOrderRepository.save(inboundOrder);

        return new InboundOrderResponseDTO(inboundOrder);
    }

    private void validateSaveInboundOrder(Long operatorId, WarehouseSection warehouseSection, List<Product> productList) {

        operatorService.validateOperatorInWarehouse(operatorId, warehouseSection.getWarehouse().getId());

        verifySectionAcceptsProductCategory(productList, warehouseSection.getId());
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
        InboundOrder inboundOrder = inboundOrderRepository.findById(inboundOrderRequest.getInboundOrder()
                .getInboundOrderId()).orElseThrow(() -> new NotFoundException("Inbound Order not found."));

        verifySameWarehouseSection(inboundOrderRequest, inboundOrder);

        List<BatchDTO> batchStockToUpdateList = inboundOrderRequest.getInboundOrder().getBatchStock();

        List<Long> batchesIdsToUpdate = batchStockToUpdateList.stream().map(BatchDTO::getBatchId)
                .collect(Collectors.toList());

        verifyIfBatchesHaveID(batchesIdsToUpdate);

        List<Long> existingBatchIds = batchRepository.findAllById(batchesIdsToUpdate).stream()
                .map(Batch::getId).collect(Collectors.toList());

        verifyBatchesExistence(batchesIdsToUpdate, existingBatchIds);

        verifyInboundOrderBatches(inboundOrder, existingBatchIds);

        verifyBatchesPurchaseOrder(inboundOrder, batchesIdsToUpdate);

        return inboundOrder;
    }

    // Verify if one of these batches already had a purchase order
    private void verifyBatchesPurchaseOrder(InboundOrder inboundOrder, List<Long> batchesIdsToUpdate) {
        List<Long> batchesIdWithPurchaseOrder = inboundOrder.getBatches().stream().filter(batch ->
                !batch.getPurchaseOrderItems().isEmpty()).map(Batch::getId).collect(Collectors.toList());

        if (CollectionUtils.containsAny(batchesIdsToUpdate, batchesIdWithPurchaseOrder)) throw new
                BadRequestException("The batch(es) "+batchesIdWithPurchaseOrder +" already had a purchase order.");
    }

    // Verify if some of the batches in the request is not of the informed Inbound Order
    private void verifyInboundOrderBatches(InboundOrder inboundOrder, List<Long> existingBatchIds) {
        List<Long> inboundOrderBatchIds = inboundOrder.getBatches().stream()
                .map(Batch::getId).collect(Collectors.toList());
        List<Long> batchesNotInInboundOrder = existingBatchIds.stream().filter(
                existingBatchId -> !inboundOrderBatchIds.contains(existingBatchId)).collect(Collectors.toList());
        if(!batchesNotInInboundOrder.isEmpty()) throw new BadRequestException("The batch(es) " +
                batchesNotInInboundOrder+" update do not belongs to the informed Inbound Order.");
    }

    // Verify if all the informed batches to update exists
    private void verifyBatchesExistence(List<Long> batchesIdsToUpdate, List<Long> existingBatchIds) {
        List<Long> unExistingBatchIds = batchesIdsToUpdate.stream().filter(
                batchIdToUpdate -> !existingBatchIds.contains(batchIdToUpdate)).collect(Collectors.toList());
        if (!unExistingBatchIds.isEmpty()) throw new BadRequestException("The batch ids " +
                unExistingBatchIds+" do not exist.");
    }

    // Verify if one of the informed batches don't have an ID
    private void verifyIfBatchesHaveID(List<Long> batchesIdsToUpdate) {
        if(batchesIdsToUpdate.stream().anyMatch(Objects::isNull)) throw new BadRequestException("Some Batches to " +
                "update have null identifier.");
    }

    private void verifySameWarehouseSection(InboundOrderRequestDTO inboundOrderRequest, InboundOrder inboundOrder) {
        Long inboundWareHouseSectionId = inboundOrder.getBatches().get(0).getWarehouseSection().getId();
        if(!inboundWareHouseSectionId.equals(inboundOrderRequest.getInboundOrder().getSection().getSectionCode()))
            throw new BadRequestException("Cannot update a batch warehouse section by this method.");
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
            if (!product.getProductCategory().getId().equals(productCategory.getId())) {
                throw new BadRequestException("Product category is invalid to Warehouse Section Category. productId: "
                        + product.getId() +
                        ", WarehouseSection: " + warehouseSection.getId());
            }
        }
    }

}
