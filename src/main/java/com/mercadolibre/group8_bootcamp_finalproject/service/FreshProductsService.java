package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.PurchaseOrderDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.ProductQuantityRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.PurchaseOrderPriceResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.*;
import com.mercadolibre.group8_bootcamp_finalproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class FreshProductsService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    public List<Product> getAllProducts(){

        List<Product> products = productRepository.findAll();
        verifyIfListIsEmpty(products);
        return products;
    }

    public List<Product> getAllProductsByCategory(String category){
        List<Product> products = productRepository.findAllByProductCategory(category);
        verifyIfListIsEmpty(products);
        return products;
    }


    public PurchaseOrderPriceResponseDTO savePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO){
        PurchaseOrder purchaseOrder = createPurchaseOrder(purchaseOrderDTO);
        purchaseOrderRepository.save(purchaseOrder);
        //realizar a baixa no estoque
        Double totalPrice = purchaseOrder.getPurchaseOrderItems().stream().mapToDouble(PurchaseOrderItem::getTotalPrice).sum();
        return PurchaseOrderPriceResponseDTO.builder().totalPrice(totalPrice).build();
    }

    public List<Product> getAllProductsFromPurchaseOrder(Long orderId){
        verifyIfPurchaseOrderExists(orderId);
        List<PurchaseOrderItem> purchaseOrderItems = purchaseOrderItemRepository.findAllByPurchaseOrder(orderId);
        List<Product> productsFromPurchaseOrder = new ArrayList<>();
        for(PurchaseOrderItem purchaseOrderItem: purchaseOrderItems){
            Batch batch = verifyIfBatchExists(purchaseOrderItem.getBatch().getId());
            productsFromPurchaseOrder.add(productRepository.findById(batch.getProduct().getId()).get());
        }
        return productsFromPurchaseOrder;
    }

    //comprador registrado -> verificar se o usuário está na tabela de comprador (token)

    //produto no estoque

    //prazo de validade do produto não seja inferior a 3 semanas

    private PurchaseOrder createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO){
        Buyer buyer = verifyIfBuyerExists(purchaseOrderDTO.getBuyerId());

        return PurchaseOrder.builder()
                .dateTime(purchaseOrderDTO.getDate())
                .orderStatusEnum(purchaseOrderDTO.getOrderStatus().getStatusCode())
                .buyer(buyer)
                .purchaseOrderItems(createPurchaseOrderItems(purchaseOrderDTO))
                .build();
    }

    private Set<PurchaseOrderItem> createPurchaseOrderItems(PurchaseOrderDTO purchaseOrderDTO){
        Set<PurchaseOrderItem> purchaseOrderItems = new HashSet<>();
        Map<Batch, Integer> batchesFromOrderItem;

        for(ProductQuantityRequestDTO product: purchaseOrderDTO.getProducts()){
            checkQuantityInBatches(product);
            batchesFromOrderItem = getBatchsAndQuantityToOrderItem(product);
            for(Map.Entry<Batch, Integer> map: batchesFromOrderItem.entrySet()){
                if(productDueDateIsValid(map.getKey())){
                    PurchaseOrderItem purchaseOrderItem = PurchaseOrderItem.builder()
                            .quantity(map.getValue())
                            .totalPrice(getPrice(map.getValue(), map.getKey().getProduct().getPrice()))
                            .batch(map.getKey())
                            .build();
                    purchaseOrderItems.add(purchaseOrderItem);
                }
            }
        }
        return purchaseOrderItems;
    }

    private Map<Batch, Integer> getBatchsAndQuantityToOrderItem(ProductQuantityRequestDTO productQuantityRequestDTO){
        List<Batch> batches = batchRepository.findAllByProduct(productQuantityRequestDTO.getProductId());
        Map<Batch, Integer> batchesAndQuantityFromOrderItem = new HashMap<>();
        Collections.sort(batches);

        int neededQuantity = productQuantityRequestDTO.getQuantity();

        for(Batch batch: batches){
            if(batch.getQuantity() >= neededQuantity){
                batchesAndQuantityFromOrderItem.put(batch, neededQuantity);
                break;
            }
            else{
                batchesAndQuantityFromOrderItem.put(batch, batch.getQuantity());
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

    private Boolean productDueDateIsValid(Batch batch){
        return batch.getDueDate().plusWeeks(3).compareTo(LocalDate.now()) <= 0;
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

    private void verifyIfListIsEmpty(List<Product> products){
        if(products.size() == 0){
            throw new NotFoundException("Products not found");
        }
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

    private void verifyIfPurchaseOrderExists(Long orderId){
        if (!purchaseOrderRepository.existsById(orderId)){
            throw new NotFoundException("Purchase order not found");
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
