package com.mercadolibre.group8_bootcamp_finalproject.service;


import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.SellerProductsListDTO;

public interface ISellerService {
    SellerProductsListDTO listAllProductsFromSeller(Long sellerId);
}
