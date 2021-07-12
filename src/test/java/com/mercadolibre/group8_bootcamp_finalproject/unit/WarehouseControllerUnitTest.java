package com.mercadolibre.group8_bootcamp_finalproject.unit;


import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseTotalProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.ProductNotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.WarehouseSectionCapabilityException;
import com.mercadolibre.group8_bootcamp_finalproject.repository.ProductRepository;
import com.mercadolibre.group8_bootcamp_finalproject.repository.SellerRepository;
import com.mercadolibre.group8_bootcamp_finalproject.repository.WarehouseSectionRepository;
import com.mercadolibre.group8_bootcamp_finalproject.service.impl.WarehouseServiceImpl;
import com.mercadolibre.group8_bootcamp_finalproject.util.TestObjectsUtil;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class WarehouseControllerUnitTest {

    private TestObjectsUtil testObjectsUtil = new TestObjectsUtil();

    WarehouseProductListDTO warehouseProductListDTO = new WarehouseProductListDTO();
    WarehouseTotalProductDTO warehouseTotalProductDTO = new WarehouseTotalProductDTO();

    @Mock
    private WarehouseSectionRepository warehouseSectionRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private WarehouseServiceImpl warehouseService;

    @BeforeEach
    public void setup () {
         this.warehouseTotalProductDTO.setWarehouseCode(1L);
         this.warehouseTotalProductDTO.setTotalQuantity(22L);

         this.warehouseProductListDTO.setProductId(1L);
         this.warehouseProductListDTO.setWarehouses(Arrays.asList(this.warehouseTotalProductDTO));

        BDDMockito.doReturn(Optional.of(testObjectsUtil.getFreshProducts().get(0))).when(productRepository).findById(testObjectsUtil.getFreshProducts().get(0).getId());
        BDDMockito.doReturn(Optional.of(testObjectsUtil.getSellers().get(0))).when(sellerRepository).findById(testObjectsUtil.getSellers().get(0).getId());
        BDDMockito.doReturn(testObjectsUtil.getWarehouseTotalProductDTOS()).when(warehouseSectionRepository).findAllProductsFromWarehouse(testObjectsUtil.getFreshProducts().get(0).getId());
    }

    @Test
    void shouldReturnCorrectProductQuantityFromWarehouseById () {

        long sumFromService = this.warehouseService.findAllProductsFromWarehouseById(1L)
                .getWarehouses()
                .stream()
                .mapToLong(WarehouseTotalProductDTO::getTotalQuantity)
                .sum();

        long sumFromMock = this.warehouseProductListDTO
                .getWarehouses()
                .stream()
                .mapToLong(WarehouseTotalProductDTO::getTotalQuantity)
                .sum();

        Assert.assertEquals(sumFromMock, sumFromService);
    }

    @Test
    void shouldReturnExceptionFromInvalidProduct () {
        Assertions.assertThatThrownBy(() -> this.warehouseService.findAllProductsFromWarehouseById(5L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("Not Found Exception. Product not found");
    }

    @Test
    void shouldVerifySectionCapabilityException () {
        Assertions
                .assertThatThrownBy(() ->
                        this.warehouseService.verifySectionCapability(this.testObjectsUtil.getFreshWarehouseSections().get(0), 150000))
                .isInstanceOf(WarehouseSectionCapabilityException.class)
                .hasMessage("Bad Request Exception. WarehouseSection current capability is less than all quantity products from batch stock");
    }
}
