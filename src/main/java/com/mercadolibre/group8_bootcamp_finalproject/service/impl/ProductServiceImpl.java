package com.mercadolibre.group8_bootcamp_finalproject.service.impl;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.ProductNotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.mapper.ProductMapper;
import com.mercadolibre.group8_bootcamp_finalproject.model.Product;
import com.mercadolibre.group8_bootcamp_finalproject.model.ProductCategory;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import com.mercadolibre.group8_bootcamp_finalproject.repository.ProductCategoryRepository;
import com.mercadolibre.group8_bootcamp_finalproject.repository.ProductRepository;
import com.mercadolibre.group8_bootcamp_finalproject.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    public ProductListDTO getAllProducts(){
        List<Product> products = productRepository.findAll();
        verifyIfListIsEmpty(products);
        return ProductListDTO.builder().products(ProductMapper.convertProductListToProductDTOList(products)).build();
    }

    public ProductListDTO getAllProductsByCategory(ProductCategoryEnum category){
        ProductCategory productCategory = productCategoryRepository.findByName(category);
        List<Product> products = productRepository.findAllByProductCategory(productCategory.getId());
        verifyIfListIsEmpty(products);
        return ProductListDTO.builder().products(ProductMapper.convertProductListToProductDTOList(products)).build();
    }

    private void verifyIfListIsEmpty(List<Product> products){
        if(products.size() == 0){
            throw new ProductNotFoundException("Product List not found");
        }
    }



}
