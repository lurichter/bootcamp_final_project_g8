package com.mercadolibre.group8_bootcamp_finalproject.service.impl;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.BadRequestException;
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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

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

    @Override
    public ProductDTO updateProductPrice(Long productId, Double percentValue) {

        DecimalFormat df = new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.ENGLISH));
        double newPrice;

        // valid if product exists
        Product product = this.verifyProductExists(productId);

        // valid if percent value is in range
        this.validIfDiscountIsInRange(percentValue);

        newPrice = product.getPrice() * ( percentValue / 100 );
        newPrice = Double.parseDouble(df.format(newPrice));

        product.setId(productId);
        product.setPrice(newPrice);

        Product productPriceUpdated = productRepository.save(product);

        return ProductDTO.builder()
                .id(productPriceUpdated.getId())
                .name(productPriceUpdated.getName())
                .description(productPriceUpdated.getDescription())
                .minTemperature(productPriceUpdated.getMinTemperature())
                .maxTemperature(productPriceUpdated.getMaxTemperature())
                .price(productPriceUpdated.getPrice())
                .build();
    }

    private void validIfDiscountIsInRange ( Double percentDiscountValue ) {
        if ( percentDiscountValue < 0 || percentDiscountValue > 100 ) throw new BadRequestException("Percent value is invalid");
    }

    private Product verifyProductExists ( Long productId ) {
        return productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
    }
}
