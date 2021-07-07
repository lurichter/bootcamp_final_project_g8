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
        updateOrdemItems(purchaseOrder);
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

    private void updateOrdemItems(PurchaseOrder purchaseOrder){
        for (PurchaseOrderItem purchaseOrderItem: purchaseOrder.getPurchaseOrderItems()){
            purchaseOrderItem.setPurchaseOrder(purchaseOrder);
            purchaseOrderItemRepository.save(purchaseOrderItem);
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
        Map<Long, Integer> batchesFromOrderItem;

        for(ProductQuantityRequestDTO product: purchaseOrderRequestDTO.getPurchaseOrder().getProducts()){
            checkQuantityInBatches(product);
            batchesFromOrderItem = getBatchsAndQuantityToOrderItem(product);
            for(Map.Entry<Long, Integer> map: batchesFromOrderItem.entrySet()){
                if(productDueDateIsValid(map.getKey())){
                    PurchaseOrderItem purchaseOrderItem = new PurchaseOrderItem();
                    purchaseOrderItem.setQuantity(map.getValue());
                    purchaseOrderItem.setTotalPrice(getPrice(map.getValue(), getPriceFromProduct(map.getKey())));
                    purchaseOrderItem.setBatch(getBatch(map.getKey()));

                    purchaseOrderItems.add(purchaseOrderItem);
                }
            }
        }
        return purchaseOrderItems;
    }

    private Double getPriceFromProduct(Long batchId){
        return batchRepository.findById(batchId).get().getProduct().getPrice();
    }

    private Batch getBatch(Long bachId){
        return batchRepository.findById(bachId).get();
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


}
