package com.mercadolibre.group8_bootcamp_finalproject.service.impl;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.PurchaseOrderDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.ProductQuantityRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.PurchaseOrderPriceResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.*;
import com.mercadolibre.group8_bootcamp_finalproject.mapper.ProductMapper;
import com.mercadolibre.group8_bootcamp_finalproject.model.*;
import com.mercadolibre.group8_bootcamp_finalproject.repository.*;
import com.mercadolibre.group8_bootcamp_finalproject.service.IPurchaseOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

    private final ProductRepository productRepository;

    private final PurchaseOrderRepository purchaseOrderRepository;

    private final PurchaseOrderItemRepository purchaseOrderItemRepository;

    private final BatchRepository batchRepository;

    private final BuyerRepository buyerRepository;

    //@Transactional
    public PurchaseOrderPriceResponseDTO savePurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO){
        PurchaseOrder purchaseOrder = createPurchaseOrder(purchaseOrderRequestDTO);
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        updateOrderItems(purchaseOrder.getPurchaseOrderItems(), purchaseOrder.getId());
        Double totalPrice = purchaseOrder.getPurchaseOrderItems().stream().mapToDouble(PurchaseOrderItem::getTotalPrice).sum();
        return PurchaseOrderPriceResponseDTO.builder().totalPrice(totalPrice).build();
    }

    public Set<ProductDTO> getAllProductsFromPurchaseOrder(Long orderId){
        verifyIfPurchaseOrderExists(orderId);
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemRepository.findAllByPurchaseOrder(orderId);
        List<Product> productsFromPurchaseOrder = new ArrayList<>();
        for(PurchaseOrderItem purchaseOrderItem: purchaseOrderItems){
            Batch batch = verifyIfBatchExists(purchaseOrderItem.getBatch().getId());
            productsFromPurchaseOrder.add(verifyIfProductExists(batch.getProduct().getId()));
        }
        return ProductMapper.convertProductListToProductDTOList(productsFromPurchaseOrder);
    }

    //@Transactional
    public PurchaseOrderPriceResponseDTO updatePurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO, Long purchaseOrderId){
        verifyIfPurchaseOrderExists(purchaseOrderId);
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderRequestDTO.getPurchaseOrder();
        List<ProductQuantityRequestDTO> products = purchaseOrderDTO.getProducts();

        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemRepository.findAllByPurchaseOrder(purchaseOrderId);
        compareProductsFromPurchaseOrderRequestWithProductsFromPurchaseOrderItemDb(purchaseOrderId, purchaseOrderItems, products);
        PurchaseOrder purchaseOrder = getPurchaseOrder(purchaseOrderId);
        Double totalPrice = purchaseOrder.getPurchaseOrderItems().stream().mapToDouble(PurchaseOrderItem::getTotalPrice).sum();
        return PurchaseOrderPriceResponseDTO.builder().totalPrice(totalPrice).build();
    }

    private PurchaseOrder createPurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO){
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderRequestDTO.getPurchaseOrder();
        Buyer buyer = verifyIfBuyerExists(purchaseOrderDTO.getBuyerId());

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderStatusEnum(purchaseOrderDTO.getOrderStatus().getStatusCode());
        purchaseOrder.setBuyer(buyer);
        purchaseOrder.setPurchaseOrderItems(createPurchaseOrderItems(purchaseOrderRequestDTO));

        return purchaseOrder;
    }

    private List<PurchaseOrderItem> createPurchaseOrderItems(PurchaseOrderRequestDTO purchaseOrderRequestDTO){
        List<PurchaseOrderItem> purchaseOrderItems = new ArrayList<>();

        for(ProductQuantityRequestDTO product: purchaseOrderRequestDTO.getPurchaseOrder().getProducts()){
            addPurchaseOrderItemsToList(product, purchaseOrderItems, null);
        }
        return purchaseOrderItems;
    }

    private void addPurchaseOrderItemsToList(ProductQuantityRequestDTO product, List<PurchaseOrderItem> purchaseOrderItems, Long purchaseOrderId){
        checkQuantityInBatches(product);
        Map<Long, Integer> batchesFromOrderItem = getBatchesAndQuantityToOrderItem(product);
        for(Map.Entry<Long, Integer> map: batchesFromOrderItem.entrySet()){
            if(productDueDateIsValid(map.getKey())){
                Batch actualBatch = getBatch(map.getKey());
                if(purchaseOrderId != null){
                    PurchaseOrderItem purchaseOrderItem = purchaseOrderItemRepository.findPurchaseOrderItemByBatch_IdAndPurchaseOrderId(actualBatch.getId(), purchaseOrderId);
                    if(purchaseOrderItem != null){
                        purchaseOrderItem.setQuantity(purchaseOrderItem.getQuantity() + map.getValue());
                        purchaseOrderItem.setTotalPrice(purchaseOrderItem.getBatch().getProduct().getPrice()*purchaseOrderItem.getQuantity());
                    }
                    else{
                        createPurchaseOrderItemAndAddToList(map, actualBatch, purchaseOrderItems);
                    }
                }
                else{
                    createPurchaseOrderItemAndAddToList(map, actualBatch, purchaseOrderItems);
                }
                updateBatchesWithPurchaseOrder(actualBatch, actualBatch.getQuantity() - map.getValue());
            }
        }
    }

    private void createPurchaseOrderItemAndAddToList(Map.Entry<Long, Integer> map, Batch batch, List<PurchaseOrderItem> purchaseOrderItems){
        PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
        purchaseOrderItem.setQuantity(map.getValue());
        purchaseOrderItem.setTotalPrice(getPrice(map.getValue(), getPriceFromProduct(map.getKey())));
        purchaseOrderItem.setBatch(batch);
        purchaseOrderItems.add(purchaseOrderItem);
    }

    private Double getPriceFromProduct(Long batchId){
        return getBatch(batchId).getProduct().getPrice();
    }

    private void updateBatchesWithPurchaseOrder(Batch batch, Integer quantity){
        batch.setQuantity(quantity);
        batchRepository.save(batch);
    }

    private Map<Long, Integer> getBatchesAndQuantityToOrderItem(ProductQuantityRequestDTO productQuantityRequestDTO){
        List<Batch> batches = batchRepository.findAllByProduct(productQuantityRequestDTO.getProductId());
        Map<Long, Integer> batchesAndQuantityFromOrderItem = new HashMap<>();
        Collections.sort(batches);

        int neededQuantity = productQuantityRequestDTO.getQuantity();

        for(Batch batch: batches){
            if(batch.getQuantity() >= neededQuantity){
                batchesAndQuantityFromOrderItem.put(batch.getId(), neededQuantity);
                break;
            }
            else{
                if(batch.getQuantity() != 0){
                    batchesAndQuantityFromOrderItem.put(batch.getId(), batch.getQuantity());
                    neededQuantity = neededQuantity - batch.getQuantity();
                }
            }
        }

        return batchesAndQuantityFromOrderItem;
    }

    private void checkQuantityInBatches(ProductQuantityRequestDTO productDTO){
        Product product = verifyIfProductExists(productDTO.getProductId());
        Integer sum = productQuantityInBatchesWithDueDateValid(productDTO.getProductId());
        if(productDTO.getQuantity() > sum){
            throw new ProductQuantityOutStockNotFoundException("Insufficient Quantity (" +productDTO.getQuantity()+ ") to " + product.getName());
        }
    }

    private void compareProductsFromPurchaseOrderRequestWithProductsFromPurchaseOrderItemDb(Long purchaseOrderId, List<PurchaseOrderItem> purchaseOrderItems, List<ProductQuantityRequestDTO> products){
        Set<Long> productsIdInPurchaseOrderRequest = new HashSet<>();

        for(ProductQuantityRequestDTO product: products){
            productsIdInPurchaseOrderRequest.add(product.getProductId());
            verifyIfProductExists(product.getProductId());
            if(verifyIfPurchaseOrderItemExistsWithProduct(product.getProductId(), purchaseOrderId)){
                updatePurchaseItemsWithProduct(product, purchaseOrderId);
            }
            else{
                addPurchaseOrderItemsToList(product, purchaseOrderItems, purchaseOrderId);
                updateOrderItems(purchaseOrderItems, purchaseOrderId);
            }
        }
        Set<Long> productsIdInPurchaseOrderDb = productRepository.finAllByPurchaseOrder(purchaseOrderId);

        checkProductsOutOfPurchaseOrderUpdated(productsIdInPurchaseOrderDb, productsIdInPurchaseOrderRequest, purchaseOrderId);

    }

    private void updatePurchaseItemsWithProduct(ProductQuantityRequestDTO product, Long purchaseOrderId){
        List<PurchaseOrderItem> purchaseOrderItemListWithProduct = getPurchaseOrderItemsWithProduct(product.getProductId(), purchaseOrderId);
        Collections.sort(purchaseOrderItemListWithProduct);
        Integer quantityInPurchaseOrder = purchaseOrderItemListWithProduct.stream().mapToInt(PurchaseOrderItem::getQuantity).sum();
        if(quantityInPurchaseOrder > product.getQuantity()){
            updatePurchaseOrderWithMoreQuantitiesInUpdate(product, purchaseOrderItemListWithProduct, quantityInPurchaseOrder);
        }
        else if (quantityInPurchaseOrder < product.getQuantity()) {
            product.setQuantity(product.getQuantity() - quantityInPurchaseOrder);
            addPurchaseOrderItemsToList(product, purchaseOrderItemListWithProduct, purchaseOrderId);
            updateOrderItems(purchaseOrderItemListWithProduct, purchaseOrderId);
        }
    }

    private void updatePurchaseOrderWithMoreQuantitiesInUpdate(ProductQuantityRequestDTO product, List<PurchaseOrderItem> purchaseOrderItemListWithProduct, Integer quantityInPurchaseOrder ){
        List<PurchaseOrderItem> purchaseOrderItemListToRemove = new ArrayList<>();
        Integer quantityToReturn = quantityInPurchaseOrder - product.getQuantity();
        for(PurchaseOrderItem purchaseOrderItem: purchaseOrderItemListWithProduct){
            if(quantityToReturn >= purchaseOrderItem.getQuantity()){
                returnItemsFromPurchaseOrderItem(purchaseOrderItem, purchaseOrderItem.getQuantity());
                quantityToReturn -= purchaseOrderItem.getQuantity();
                purchaseOrderItemListToRemove.add(purchaseOrderItem);
            }
            else{
                returnItemsFromPurchaseOrderItem(purchaseOrderItem, quantityToReturn);
                purchaseOrderItem.setQuantity(purchaseOrderItem.getQuantity() - quantityToReturn);
                purchaseOrderItem.setTotalPrice(purchaseOrderItem.getBatch().getProduct().getPrice()*purchaseOrderItem.getQuantity());
                purchaseOrderItemRepository.save(purchaseOrderItem);
                break;
            }
        }
        removeItemsFromList(purchaseOrderItemListWithProduct, purchaseOrderItemListToRemove);
        removeItemsFromPurchaseOrderItemsDb(purchaseOrderItemListToRemove);
    }

    private void checkProductsOutOfPurchaseOrderUpdated(Set<Long> productsIdInPurchaseOrderDb, Set<Long> productsIdInPurchaseOrderRequest, Long purchaseOrderId ){
        if(productsIdInPurchaseOrderDb.size() != productsIdInPurchaseOrderRequest.size()){

            if(productsIdInPurchaseOrderDb.size() > productsIdInPurchaseOrderRequest.size()){
                productsIdInPurchaseOrderDb.removeAll(productsIdInPurchaseOrderRequest);
                returnProductsDiscardedInThePurchaseOrder(productsIdInPurchaseOrderDb, purchaseOrderId);
            }
            else{
                productsIdInPurchaseOrderRequest.removeAll(productsIdInPurchaseOrderDb);
                returnProductsDiscardedInThePurchaseOrder(productsIdInPurchaseOrderRequest, purchaseOrderId);
            }
        }
    }

    private void returnProductsDiscardedInThePurchaseOrder(Set<Long> productsId, Long purchaseOrderId){
        for(Long productId: productsId){
            List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemRepository.findPurchaseOrderItemByBatch_ProductIdAndPurchaseOrderId(productId, purchaseOrderId);
            for(PurchaseOrderItem purchaseOrderItem: purchaseOrderItems){
                returnItemsFromPurchaseOrderItem(purchaseOrderItem, purchaseOrderItem.getQuantity());
                purchaseOrderItemRepository.delete(purchaseOrderItem);
            }
        }
    }

    private void removeItemsFromList(List<PurchaseOrderItem> purchaseOrderItemListWithProduct, List<PurchaseOrderItem> purchaseOrderItemListToRemove){
        for(PurchaseOrderItem purchaseOrderItem: purchaseOrderItemListToRemove){
            purchaseOrderItemListWithProduct.remove(purchaseOrderItem);
        }

    }

    private void removeItemsFromPurchaseOrderItemsDb(List<PurchaseOrderItem> purchaseOrderItemListToRemove){
        for(PurchaseOrderItem purchaseOrderItem: purchaseOrderItemListToRemove){
            purchaseOrderItemRepository.delete(purchaseOrderItem);
        }
    }

    private void returnItemsFromPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem, Integer quantity){
        updateBatchesWithPurchaseOrder(purchaseOrderItem.getBatch(), purchaseOrderItem.getBatch().getQuantity() + quantity);
    }

    private void updateOrderItems(List<PurchaseOrderItem> purchaseOrderItems, Long purchaseOrderId){
        for (PurchaseOrderItem purchaseOrderItem: purchaseOrderItems){
            if(purchaseOrderItem.getPurchaseOrder() == null){
                Optional<PurchaseOrder> purchaseOrderO = purchaseOrderRepository.findById(purchaseOrderId);
                if(purchaseOrderO.isPresent()){
                    PurchaseOrder purchaseOrder = purchaseOrderO.get();
                    purchaseOrderItem.setPurchaseOrder(purchaseOrder);
                }
                else{
                    throw new NotFoundException("Purchase Order " + purchaseOrderId.toString() + " not Found");
                }
            }
            purchaseOrderItemRepository.save(purchaseOrderItem);
        }
    }

    private Boolean productDueDateIsValid(Long batchId){
        Batch batch = getBatch(batchId);
        return batch.getDueDate().plusWeeks(-3).compareTo(LocalDate.now()) >= 0;
    }

    private Integer productQuantityInBatchesWithDueDateValid(Long productId){
        Product product = verifyIfProductExists(productId);
        List<Batch> batches = batchRepository.findAllByProduct(productId);
        if(batches.size()>0){
            return batches.stream().filter(b->b.getDueDate().plusWeeks(-3).compareTo(LocalDate.now()) >= 0).mapToInt(Batch::getQuantity).sum();
        }else{
            throw new BatchNotFoundException("Batch with product " + product.getName() + " not found");
        }
    }

    private Double getPrice(Integer quantity, Double price){
        return price * quantity;
    }

    private void verifyIfPurchaseOrderExists(Long orderId){
        if (!purchaseOrderRepository.existsById(orderId)){
            throw new NotFoundException("Purchase order not found");
        }
    }

    private Batch getBatch(Long batchId){
        Optional<Batch> batch = batchRepository.findById(batchId);
        if(batch.isPresent()){
            return batch.get();
        }
        throw new BatchNotFoundException("Batch with id " + batchId + " not found");
    }

    private PurchaseOrder getPurchaseOrder(Long purchaseOrderId){
        Optional<PurchaseOrder> purchaseOrderO = purchaseOrderRepository.findById(purchaseOrderId);
        if(purchaseOrderO.isPresent()){
            return purchaseOrderO.get();
        }
        throw new NotFoundException("Purchase Order not found");
    }

    private Product verifyIfProductExists(Long productId){
        Optional<Product> productOptional = productRepository.findById(productId);
        if(productOptional.isPresent()){
            return productOptional.get();
        }
        else{
            throw new ProductNotFoundException("Product with id "+ productId + " not found");
        }
    }

    private Buyer verifyIfBuyerExists(Long buyerId){
        Optional<Buyer> buyerOptional = buyerRepository.findById(buyerId);
        if(buyerOptional.isPresent()){
            return buyerOptional.get();
        }
        else{
            throw new BuyerNotFoundException("Buyer with id " + buyerId + " not found");
        }
    }

    private Batch verifyIfBatchExists(Long batchId){
        if(batchRepository.existsById(batchId)){
            return getBatch(batchId);
        }
        else{
            throw new BatchNotFoundException("Batch with id " + batchId + " not found");
        }
    }

    private Boolean verifyIfPurchaseOrderItemExistsWithProduct(Long productId, Long purchaseOrderId){
        Long count = purchaseOrderItemRepository.countByBatch_ProductIdAndPurchaseOrderId(productId, purchaseOrderId);
        return count > 0;
    }

    private List<PurchaseOrderItem> getPurchaseOrderItemsWithProduct(Long productId, Long purchaseOrderId){
        return purchaseOrderItemRepository.findPurchaseOrderItemByBatch_ProductIdAndPurchaseOrderId(productId, purchaseOrderId);
    }

}
