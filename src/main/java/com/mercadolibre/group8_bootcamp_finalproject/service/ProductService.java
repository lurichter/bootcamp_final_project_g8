package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.Product;
import com.mercadolibre.group8_bootcamp_finalproject.model.ProductCategory;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import com.mercadolibre.group8_bootcamp_finalproject.repository.ProductCategoryRepository;
import com.mercadolibre.group8_bootcamp_finalproject.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public Set<ProductDTO> getAllProducts(){

        List<Product> products = productRepository.findAll();
        verifyIfListIsEmpty(products);
        return convertProductListToProductDTOList(products);
    }

    public Set<ProductDTO> getAllProductsByCategory(ProductCategoryEnum category){

        ProductCategory productCategory = productCategoryRepository.findByName(category);
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

}
