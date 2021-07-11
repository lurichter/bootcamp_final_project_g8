package com.mercadolibre.group8_bootcamp_finalproject.unit;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.Product;
import com.mercadolibre.group8_bootcamp_finalproject.model.ProductCategory;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import com.mercadolibre.group8_bootcamp_finalproject.repository.ProductCategoryRepository;
import com.mercadolibre.group8_bootcamp_finalproject.repository.ProductRepository;
import com.mercadolibre.group8_bootcamp_finalproject.service.impl.ProductServiceImpl;
import com.mercadolibre.group8_bootcamp_finalproject.util.MockitoExtension;
import com.mercadolibre.group8_bootcamp_finalproject.util.TestObjectsUtil;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
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

    private TestObjectsUtil testObjectsUtil;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setup(){
        this.testObjectsUtil = new TestObjectsUtil();
        Set<ProductDTO> productDTOS = this.testObjectsUtil.getProductDTOS();
        List<ProductDTO> productDTOS1 = new ArrayList<>(productDTOS);
        ProductDTO productDTO1 = productDTOS1.get(0);
        ProductDTO productDTO2 = productDTOS1.get(1);

                ProductCategoryEnum productCategoryFS = ProductCategoryEnum.FS;

        BDDMockito.doReturn(this.testObjectsUtil.getFreshProducts()).when(productRepository.findAll());

        BDDMockito.when(productRepository.findAll())
                .thenAnswer(invocation -> {
                    Object[] args = invocation.getArguments();
                    ((List<Product>)args[0]).get(0).setId(1L);
                    ((List<Product>)args[0]).get(0).setId(2L);
                    return args[0];
                });
        BDDMockito.when(productRepository.findById(productDTO1.getId()))
                .thenAnswer(invocation -> {
                    Object[] args = invocation.getArguments();
                    ((Product)args[0]).setId(productDTO1.getId());
                    return args;
                });

        BDDMockito.when(productCategoryRepository.findByName(productCategoryFS))
                .thenAnswer(invocation -> {
                    Object[] args = invocation.getArguments();
                    ((ProductCategory)args[0]).setId(1L);
                    return args[0];
                });


    }

    @Test
    public void ShouldReturnProductDTOListWhenGetAllProducts(){
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
                        )
                );


    }

}
