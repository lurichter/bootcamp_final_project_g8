package com.mercadolibre.group8_bootcamp_finalproject.unit;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.ProductNotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.Product;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import com.mercadolibre.group8_bootcamp_finalproject.repository.ProductCategoryRepository;
import com.mercadolibre.group8_bootcamp_finalproject.repository.ProductRepository;
import com.mercadolibre.group8_bootcamp_finalproject.service.impl.ProductServiceImpl;
import com.mercadolibre.group8_bootcamp_finalproject.util.MockitoExtension;
import com.mercadolibre.group8_bootcamp_finalproject.util.TestObjectsUtil;
import com.mercadolibre.group8_bootcamp_finalproject.util.TestObjectsUtilUpdated;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class ProductUnitTest {

    private TestObjectsUtilUpdated testObjectsUtil;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setup(){
        this.testObjectsUtil = new TestObjectsUtilUpdated();
        List<Product> allProducts = testObjectsUtil.getProducts();
        List<Product> freshProducts = testObjectsUtil.getFreshProducts();
        List<Product> frozenProducts = testObjectsUtil.getFrozenProducts();
        ProductCategoryEnum productCategoryFS = ProductCategoryEnum.FS;
        ProductCategoryEnum productCategoryRF = ProductCategoryEnum.RF;

        BDDMockito.doReturn(allProducts).when(productRepository).findAll();
        BDDMockito.doReturn(freshProducts).when(productRepository).findAllByProductCategory(1L);
        BDDMockito.doReturn(frozenProducts).when(productRepository).findAllByProductCategory(2L);
        BDDMockito.doReturn(testObjectsUtil.getFreshProductCategory()).when(productCategoryRepository).findByName(productCategoryFS);
        BDDMockito.doReturn(testObjectsUtil.getFrozenProductCategory()).when(productCategoryRepository).findByName(productCategoryRF);

    }

    @Test
    public void shouldReturnProductDTOListWhenGetAllProducts(){
        Set<ProductDTO> productDTOSMock = this.testObjectsUtil.getProductDTOS();
        List<ProductDTO> productDTOSList = new ArrayList<>(productDTOSMock);
        ProductListDTO productDTOS = this.productService.getAllProducts();

        Assertions.assertThat(productDTOS.getProducts())
                .extracting(
                        ProductDTO::getId,
                        ProductDTO::getName,
                        ProductDTO::getDescription,
                        ProductDTO::getMinTemperature,
                        ProductDTO::getMaxTemperature,
                        ProductDTO::getPrice
                )
                .contains(
                        Tuple.tuple(
                                productDTOSList.get(0).getId(),
                                productDTOSList.get(0).getName(),
                                productDTOSList.get(0).getDescription(),
                                productDTOSList.get(0).getMinTemperature(),
                                productDTOSList.get(0).getMaxTemperature(),
                                productDTOSList.get(0).getPrice()
                        ),
                        Tuple.tuple(
                                productDTOSList.get(1).getId(),
                                productDTOSList.get(1).getName(),
                                productDTOSList.get(1).getDescription(),
                                productDTOSList.get(1).getMinTemperature(),
                                productDTOSList.get(1).getMaxTemperature(),
                                productDTOSList.get(1).getPrice()
                        ),
                        Tuple.tuple(
                                productDTOSList.get(2).getId(),
                                productDTOSList.get(2).getName(),
                                productDTOSList.get(2).getDescription(),
                                productDTOSList.get(2).getMinTemperature(),
                                productDTOSList.get(2).getMaxTemperature(),
                                productDTOSList.get(2).getPrice()
                        ),
                        Tuple.tuple(
                                productDTOSList.get(3).getId(),
                                productDTOSList.get(3).getName(),
                                productDTOSList.get(3).getDescription(),
                                productDTOSList.get(3).getMinTemperature(),
                                productDTOSList.get(3).getMaxTemperature(),
                                productDTOSList.get(3).getPrice()
                        )
                );
    }

    @Test
    public void shouldReturnExceptionWhenProductListIsEmpty(){
        List<Product> productsEmpty = new ArrayList<>();
        BDDMockito.doReturn(productsEmpty).when(productRepository).findAll();

        Assertions.assertThatThrownBy(() -> this.productService.getAllProducts()).isInstanceOf(ProductNotFoundException.class).hasMessage("Not Found Exception. Product List not found");
    }

    @Test
    public void shouldReturnFreshProductsListWhenGetAllProductsByFreshCategory(){
        ProductCategoryEnum productCategoryFS = ProductCategoryEnum.FS;
        ProductListDTO productDTOS = this.productService.getAllProductsByCategory(productCategoryFS);
        Set<ProductDTO> productDTOSMock = this.testObjectsUtil.getFreshPoductDTOS();
        List<ProductDTO> productDTOSList = new ArrayList<>(productDTOSMock);

        Assertions.assertThat(productDTOS.getProducts())
                .extracting(
                        ProductDTO::getId,
                        ProductDTO::getName,
                        ProductDTO::getDescription,
                        ProductDTO::getMinTemperature,
                        ProductDTO::getMaxTemperature,
                        ProductDTO::getPrice
                )
                .contains(
                        Tuple.tuple(
                                productDTOSList.get(0).getId(),
                                productDTOSList.get(0).getName(),
                                productDTOSList.get(0).getDescription(),
                                productDTOSList.get(0).getMinTemperature(),
                                productDTOSList.get(0).getMaxTemperature(),
                                productDTOSList.get(0).getPrice()
                        ),
                        Tuple.tuple(
                                productDTOSList.get(1).getId(),
                                productDTOSList.get(1).getName(),
                                productDTOSList.get(1).getDescription(),
                                productDTOSList.get(1).getMinTemperature(),
                                productDTOSList.get(1).getMaxTemperature(),
                                productDTOSList.get(1).getPrice()
                        )
                );
    }

    @Test
    public void shouldReturnFrozenProductsListWhenGetAllProductsByFrozenCategory(){
        ProductCategoryEnum productCategoryRF = ProductCategoryEnum.RF;
        ProductListDTO productDTOS = this.productService.getAllProductsByCategory(productCategoryRF);
        Set<ProductDTO> productDTOSMock = this.testObjectsUtil.getFrozenPoductDTOS();
        List<ProductDTO> productDTOSList = new ArrayList<>(productDTOSMock);

        Assertions.assertThat(productDTOS.getProducts())
                .extracting(
                        ProductDTO::getId,
                        ProductDTO::getName,
                        ProductDTO::getDescription,
                        ProductDTO::getMinTemperature,
                        ProductDTO::getMaxTemperature,
                        ProductDTO::getPrice
                )
                .contains(
                        Tuple.tuple(
                                productDTOSList.get(0).getId(),
                                productDTOSList.get(0).getName(),
                                productDTOSList.get(0).getDescription(),
                                productDTOSList.get(0).getMinTemperature(),
                                productDTOSList.get(0).getMaxTemperature(),
                                productDTOSList.get(0).getPrice()
                        ),
                        Tuple.tuple(
                                productDTOSList.get(1).getId(),
                                productDTOSList.get(1).getName(),
                                productDTOSList.get(1).getDescription(),
                                productDTOSList.get(1).getMinTemperature(),
                                productDTOSList.get(1).getMaxTemperature(),
                                productDTOSList.get(1).getPrice()
                        )
                );
    }

    @Test
    public void shouldReturnExceptionWhenProductListByCategoryIsEmpty(){
        List<Product> productsEmpty = new ArrayList<>();
        BDDMockito.doReturn(productsEmpty).when(productRepository).findAllByProductCategory(2L);

        Assertions.assertThatThrownBy(() -> this.productService.getAllProductsByCategory(ProductCategoryEnum.RF)).isInstanceOf(ProductNotFoundException.class).hasMessage("Not Found Exception. Product List not found");
    }

    @Test
    void shouldReturnProductNotFoundInPriceDiscount () {
        Assertions.assertThatThrownBy(() -> this.productService.updateProductPrice(9L, 50.0))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Not Found Exception. Product not found");
    }
}
