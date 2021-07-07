package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.PurchaseOrderDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.ProductQuantityRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.PurchaseOrderPriceResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.*;
import com.mercadolibre.group8_bootcamp_finalproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    //@Transactional Implementar baixa no estoque após salvar uma ordem de compra
    public PurchaseOrderPriceResponseDTO savePurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO){
        PurchaseOrder purchaseOrder = createPurchaseOrder(purchaseOrderRequestDTO);
        purchaseOrderRepository.save(purchaseOrder);
        updateOrdemItems(purchaseOrder.getPurchaseOrderItems(), purchaseOrder.getId());
        Double totalPrice = purchaseOrder.getPurchaseOrderItems().stream().mapToDouble(PurchaseOrderItem::getTotalPrice).sum();
        return PurchaseOrderPriceResponseDTO.builder().totalPrice(totalPrice).build();
    }

    public Set<ProductDTO> getAllProductsFromPurchaseOrder(Long orderId){
        verifyIfPurchaseOrderExists(orderId);
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemRepository.findAllByPurchaseOrder(orderId);
        List<Product> productsFromPurchaseOrder = new ArrayList<>();
        for(PurchaseOrderItem purchaseOrderItem: purchaseOrderItems){
            Batch batch = verifyIfBatchExists(purchaseOrderItem.getBatch().getId());
            productsFromPurchaseOrder.add(productRepository.findById(batch.getProduct().getId()).get());
        }
        return convertProductListToProductDTOList(productsFromPurchaseOrder);
    }

    public PurchaseOrderPriceResponseDTO updatePurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO, Long purchaseOrderId){
        verifyIfPurchaseOrderExists(purchaseOrderId);
        PurchaseOrderDTO purchaseOrderDTO = purchaseOrderRequestDTO.getPurchaseOrder();
        List<ProductQuantityRequestDTO> products = purchaseOrderDTO.getProducts();

        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemRepository.findAllByPurchaseOrder(purchaseOrderId);
        compareProductsFromPurchaseOrderWithProductsFromPurchaseOrderItem(purchaseOrderId, purchaseOrderItems, products);
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId).get();
        Double totalPrice = purchaseOrder.getPurchaseOrderItems().stream().mapToDouble(PurchaseOrderItem::getTotalPrice).sum();
        return PurchaseOrderPriceResponseDTO.builder().totalPrice(totalPrice).build();
    }

    private void compareProductsFromPurchaseOrderWithProductsFromPurchaseOrderItem(Long purchaseOrderId, List<PurchaseOrderItem> purchaseOrderItems, List<ProductQuantityRequestDTO> products){
        //devolver p/ lote produtos da ordem de compra que NAO aparecem no PUT
        for(ProductQuantityRequestDTO product: products){
            verifyIfProductExists(product.getProductId());
            if(verifyIfPurchaseOrderItemExistsWithProduct(product.getProductId(), purchaseOrderId)){
                List<PurchaseOrderItem> purchaseOrderItemListWithProduct = getPurchaseOrderItemsWithProduct(product.getProductId(), purchaseOrderId);
                Collections.sort(purchaseOrderItemListWithProduct);
                Integer quantityInPurchaseOrder = purchaseOrderItemListWithProduct.stream().mapToInt(PurchaseOrderItem::getQuantity).sum();
                if(quantityInPurchaseOrder > product.getQuantity()){
                    Integer quantityToReturn = quantityInPurchaseOrder - product.getQuantity();
                    for(PurchaseOrderItem purchaseOrderItem: purchaseOrderItemListWithProduct){
                        if(quantityToReturn >= purchaseOrderItem.getQuantity()){
                            returnItemsFromPurchaseOrderItem(purchaseOrderItem, purchaseOrderItem.getQuantity());
                            quantityToReturn -= purchaseOrderItem.getQuantity();
                        }
                        else{
                            returnItemsFromPurchaseOrderItem(purchaseOrderItem, quantityToReturn);
                            break;
                        }
                    }
                }
                else if (quantityInPurchaseOrder < product.getQuantity()) {
                    product.setQuantity(product.getQuantity() - quantityInPurchaseOrder);
                    addPurchaseOrderItemsToList(product, purchaseOrderItems);
                    updateOrdemItems(purchaseOrderItems, purchaseOrderId);
                }
            }
            else{
                addPurchaseOrderItemsToList(product, purchaseOrderItems);
                updateOrdemItems(purchaseOrderItems, purchaseOrderId);
            }

        }
    }

    private void returnItemsFromPurchaseOrderItem(PurchaseOrderItem purchaseOrderItem, Integer quantity){
        updateBatchesWithPurchaseOrder(purchaseOrderItem.getBatch(), quantity);
        if(purchaseOrderItem.getQuantity().equals(quantity)){
            purchaseOrderItemRepository.delete(purchaseOrderItem);
        }
    }

    private void updateOrdemItems(List<PurchaseOrderItem> purchaseOrderItems, Long purchaseOrderId){
        for (PurchaseOrderItem purchaseOrderItem: purchaseOrderItems){
            if(purchaseOrderItem.getPurchaseOrder() == null){
                PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(purchaseOrderId).get();
                purchaseOrderItem.setPurchaseOrder(purchaseOrder);
                purchaseOrderItemRepository.save(purchaseOrderItem);
            }
        }
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
            addPurchaseOrderItemsToList(product, purchaseOrderItems);
        }
        return purchaseOrderItems;
    }

    private void addPurchaseOrderItemsToList(ProductQuantityRequestDTO product, List<PurchaseOrderItem> purchaseOrderItems ){
        checkQuantityInBatches(product);
        Map<Long, Integer> batchesFromOrderItem = getBatchsAndQuantityToOrderItem(product);
        for(Map.Entry<Long, Integer> map: batchesFromOrderItem.entrySet()){
            if(productDueDateIsValid(map.getKey())){
                Batch actualBatch = getBatch(map.getKey());
                PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
                purchaseOrderItem.setQuantity(map.getValue());
                purchaseOrderItem.setTotalPrice(getPrice(map.getValue(), getPriceFromProduct(map.getKey())));
                purchaseOrderItem.setBatch(actualBatch);

                purchaseOrderItems.add(purchaseOrderItem);
                updateBatchesWithPurchaseOrder(actualBatch, actualBatch.getQuantity() - map.getValue());
            }
        }
    }

    private Double getPriceFromProduct(Long batchId){
        return batchRepository.findById(batchId).get().getProduct().getPrice();
    }

    private Batch getBatch(Long bachId){
        return batchRepository.findById(bachId).get();
    }

    private void updateBatchesWithPurchaseOrder(Batch batch, Integer quantity){
        batch.setQuantity(quantity);
        batchRepository.save(batch);
    }

    private Map<Long, Integer> getBatchsAndQuantityToOrderItem(ProductQuantityRequestDTO productQuantityRequestDTO){
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
                batchesAndQuantityFromOrderItem.put(batch.getId(), batch.getQuantity());
                neededQuantity = neededQuantity - batch.getQuantity();
            }
        }

        return batchesAndQuantityFromOrderItem;
    }

    private void checkQuantityInBatches(ProductQuantityRequestDTO product){
        Integer sum = productQuantityInBatches(product.getProductId());
        if(product.getQuantity() > sum){
            throw new NotFoundException("Quantidade insuficiente");
        }
    }

    private Boolean productDueDateIsValid(Long batchId){
        Batch batch = batchRepository.findById(batchId).get();
        return batch.getDueDate().plusWeeks(-3).compareTo(LocalDate.now()) >= 0;
    }

    private Integer productQuantityInBatches(Long productId){
        verifyIfProductExists(productId);
        List<Batch> batches = batchRepository.findAllByProduct(productId);
        if(batches.size()>0){
            return batches.stream().mapToInt(Batch::getQuantity).sum();
        }else{
            throw new NotFoundException("Estoque em falta");
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

    private Set<ProductDTO> convertProductListToProductDTOList(List<Product> products){
        Set<ProductDTO> productDTOS = new LinkedHashSet<>();
        for(Product product: products){
            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .minTemperature(product.getMinTemperature())
                    .maxTemperature(product.getMaxTemperature())
                    .price(product.getPrice())
                    .build();
            productDTOS.add(productDTO);
        }

        return productDTOS;
    }

    private void verifyIfProductExists(Long productId){
        if(productRepository.existsById(productId)){
            productRepository.findById(productId).get();
        }
        else{
            throw new NotFoundException("Products not found");
        }
    }

    private Buyer verifyIfBuyerExists(Long buyerId){
        if(buyerRepository.existsById(buyerId)){
            return buyerRepository.findById(buyerId).get();
        }
        else{
            throw new NotFoundException("Buyer not found");
        }
    }

    private Batch verifyIfBatchExists(Long batchId){
        if(batchRepository.existsById(batchId)){
            return batchRepository.findById(batchId).get();
        }
        else{
            throw new NotFoundException("Batch not found");
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
