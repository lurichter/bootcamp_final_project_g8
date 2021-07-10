package com.mercadolibre.group8_bootcamp_finalproject.integration;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseTotalProductDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WarehouseControllerIntegrationTest extends ControllerTest {

//    @Mock
//    private WarehouseController warehouseController;
//    @Mock
//    private WarehouseProductListDTO warehouseProductListDTO;
//    @Mock
//    private WarehouseTotalProductDTO warehouseTotalProductDTO;

    @InjectMocks
    WarehouseProductListDTO warehouseProductListDTO;

    @InjectMocks
    WarehouseTotalProductDTO warehouseTotalProductDTO;

    @BeforeEach
    public void setup () {
        this.warehouseProductListDTO.setProductId(1L);

        this.warehouseTotalProductDTO.setWarehouseCode(1L);
        this.warehouseTotalProductDTO.setTotalQuantity(8L);

        this.warehouseProductListDTO.setWarehouses(Arrays.asList(this.warehouseTotalProductDTO));

        System.out.println(this.warehouseTotalProductDTO.getTotalQuantity());
    }


    @Test
    void shouldReturnStatusCodeOkFromAllProductsFromWarehouseById () {


        ResponseEntity<String> responseEntity = this.testRestTemplate.exchange(
                                                    "/api/v1/fresh-products/warehouse?querytype=1",
                                                    HttpMethod.GET,
                                                    this.getDefaultRequestEntity(),
                                                    String.class);

        Assertions.assertThat(HttpStatus.OK).isEqualTo(responseEntity.getStatusCode());
    }


    @Test
    void shouldReturnCorrectProductQuantityFromWarehouseById () {

        ResponseEntity<WarehouseProductListDTO> responseEntity = this.testRestTemplate.exchange(
                                                    "/api/v1/fresh-products/warehouse?querytype=1",
                                                    HttpMethod.GET,
                                                    this.getDefaultRequestEntity(),
                                                    WarehouseProductListDTO.class);

        Long allProductQuantitySum = responseEntity.getBody()
                .getWarehouses()
                .stream().mapToLong(WarehouseTotalProductDTO::getTotalQuantity).sum();

        Assertions.assertThat(allProductQuantitySum).isEqualTo(8);
    }

    // nao funcionando, falta corrigir algo
//    @Test
//    void shouldAllProductFromWarehouseById () {
//
//        ResponseEntity<WarehouseProductListDTO> responseEntity = this.testRestTemplate.exchange(
//                "/api/v1/fresh-products/warehouse?querytype=1",
//                HttpMethod.GET,
//                this.getDefaultRequestEntity(),
//                WarehouseProductListDTO.class);
//
//        Assertions.assertThat(responseEntity.getBody()).isInstanceOf(warehouseProductListDTO.getClass());
//    }


}
