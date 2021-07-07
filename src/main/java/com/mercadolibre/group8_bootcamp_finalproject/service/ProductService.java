package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.*;
import com.mercadolibre.group8_bootcamp_finalproject.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private BuyerRepository buyerRepository;

    public Set<ProductDTO> getAllProducts(){

        List<Product> products = productRepository.findAll();
        verifyIfListIsEmpty(products);
        return convertProductListToProductDTOList(products);
    }

    public Set<ProductDTO> getAllProductsByCategory(String category){

        ProductCategory productCategory = productCategoryRepository.findByCategoryName(category);
        List<Product> products = productRepository.findAllByProductCategory(productCategory.getId());
        verifyIfListIsEmpty(products);
        return convertProductListToProductDTOList(products);
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

    private Batch verifyIfBatchExists(Long batchId){
        if(batchRepository.existsById(batchId)){
            return batchRepository.findById(batchId).get();
        }
        else{
            throw new NotFoundException("Batch not found");
        }
    }


}
