package com.mercadolibre.group8_bootcamp_finalproject.service.impl;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.SellerProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.SellerProductsListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.ProductNotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.SellerNotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.Seller;
import com.mercadolibre.group8_bootcamp_finalproject.repository.ProductRepository;
import com.mercadolibre.group8_bootcamp_finalproject.repository.SellerRepository;
import com.mercadolibre.group8_bootcamp_finalproject.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerServiceImpl implements ISellerService {
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public SellerProductsListDTO listAllProductsFromSeller(Long sellerId){

        boolean sellerExist = sellerRepository.existsById(sellerId);

        if(!sellerExist) throw new SellerNotFoundException("Seller id is not valid");

        List<SellerProductDTO> sellerProducts = productRepository.findAllProductsFromSeller(sellerId);

        if (sellerProducts.isEmpty()) throw new ProductNotFoundException("Seller does not have registered products");

        return SellerProductsListDTO
                .builder()
                .sellerId(sellerId)
                .products(sellerProducts)
                .build();
    }
}