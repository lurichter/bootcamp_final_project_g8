package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.PurchaseOrderDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.ProductQuantityRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.PurchaseOrderPriceResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.*;
import com.mercadolibre.group8_bootcamp_finalproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    /*
    public PurchaseOrderPriceResponseDTO savePurchaseOrder(PurchaseOrderDTO purchaseOrderDTO){

    }
     */

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

    private Integer productQuantityInBatch(Long productId){
        List<Batch> batches = batchRepository.findAllByProduct(productId);
        if(batches.size()>0){
            return batches.stream().mapToInt(Batch::getQuantity).sum();
        }else{
            throw new NotFoundException("Estoque em falta");
        }
    }

    private void checkQuantityInBatch(ProductQuantityRequestDTO product){
        Integer sum = productQuantityInBatch(product.getProductId());
        if(product.getQuantity() > sum){
            throw new NotFoundException("Quantidade insuficiente");
        }
    }

    private Double getPriceFromOrderItem(ProductQuantityRequestDTO productQuantityRequestDTO){
        Product product = verifyIfProductExists(productQuantityRequestDTO.getProductId());
        return product.getPrice() * productQuantityRequestDTO.getQuantity();
    }

    //prazo de validade do produto não seja inferior a 3 semanas

    /*
    private void createPurchaseOrder(PurchaseOrderDTO purchaseOrderDTO){
        Buyer buyer = verifyIfBuyerExists(purchaseOrderDTO.getBuyerId());
        Set<PurchaseOrderItem> purchaseOrderItems;
        PurchaseOrder purchaseOrder = PurchaseOrder.builder()
                .dateTime(purchaseOrderDTO.getDate())
                .orderStatusEnum(purchaseOrderDTO.getOrderStatus().getStatusCode())
                .buyer(buyer)
                .

    }

    private Set<PurchaseOrderItem> createPurchaseOrderItems(PurchaseOrderDTO purchaseOrderDTO){
        Set<PurchaseOrderItem> purchaseOrderItems = new HashSet<>();
        for(ProductQuantityRequestDTO product: purchaseOrderDTO.getProducts()){
            checkQuantityInBatch(product);
            PurchaseOrderItem purchaseOrderItem = PurchaseOrderItem.builder()
                    .quantity(product.getQuantity())
                    .totalPrice(getPriceFromOrderItem(product))

            //gerar o preço total do item de ordem
        }
    }
    */

    private void verifyIfListIsEmpty(List<Product> products){
        if(products.size() == 0){
            throw new NotFoundException("Products not found");
        }
    }

    private Product verifyIfProductExists(Long productId){
        if(productRepository.existsById(productId)){
            return productRepository.findById(productId).get();
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
