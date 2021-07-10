package com.mercadolibre.group8_bootcamp_finalproject.service.impl;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
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
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    private final ProductCategoryRepository productCategoryRepository;

    public Set<ProductDTO> getAllProducts(){
        List<Product> products = productRepository.findAll();
        verifyIfListIsEmpty(products);
        return ProductMapper.convertProductListToProductDTOList(products);
    }

    public Set<ProductDTO> getAllProductsByCategory(ProductCategoryEnum category){
        ProductCategory productCategory = productCategoryRepository.findByName(category);
        List<Product> products = productRepository.findAllByProductCategory(productCategory.getId());
        verifyIfListIsEmpty(products);
        return ProductMapper.convertProductListToProductDTOList(products);
    }

    private void verifyIfListIsEmpty(List<Product> products){
        if(products.size() == 0){
            throw new ProductNotFoundException("Product List not found");
        }
    }

}
