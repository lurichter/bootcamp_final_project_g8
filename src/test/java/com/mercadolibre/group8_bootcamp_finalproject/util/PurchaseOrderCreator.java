package com.mercadolibre.group8_bootcamp_finalproject.util;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.OrderStatusDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.PurchaseOrderDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.ProductQuantityRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.OrderStatusEnum;
import org.aspectj.weaver.ast.Or;

import java.util.Arrays;
import java.util.List;

public class PurchaseOrderCreator {

    public static PurchaseOrderRequestDTO createValidPurchaseOrderRequest(){
        return new PurchaseOrderRequestDTO(createValidPurchaseOrderToSave());
    }

    public static PurchaseOrderRequestDTO createValidPurchaseOrderUpdateRequest(){
        return new PurchaseOrderRequestDTO(createValidPurchaseOrderToUpdate());
    }

    public static PurchaseOrderRequestDTO createInvalidPurchaseOrderRequest(){
        return new PurchaseOrderRequestDTO(createInvalidPurchaseOrderToSave());
    }

    public static PurchaseOrderDTO createValidPurchaseOrderToSave(){
        return new PurchaseOrderDTO(1L, createOrderStatus(), createValidProductsDTO());
    }

    public static PurchaseOrderDTO createInvalidPurchaseOrderToSave(){
        return new PurchaseOrderDTO(1L, createOrderStatus(), createInvalidProductsDTO());
    }

    public static PurchaseOrderDTO createValidPurchaseOrderToUpdate(){
        return new PurchaseOrderDTO(1L, createOrderStatus(), createValidProductsUpdateDTO());
    }

    public static OrderStatusDTO createOrderStatus(){
        return OrderStatusDTO.builder().statusCode(OrderStatusEnum.OPEN).build();
    }

    public static ProductQuantityRequestDTO createValidProduct1(){
        return ProductQuantityRequestDTO.builder().productId(1L).quantity(1).build();
    }

    public static ProductQuantityRequestDTO createValidProduct2(){
        return ProductQuantityRequestDTO.builder().productId(2L).quantity(1).build();
    }

    public static ProductQuantityRequestDTO createValidProduct3(){
        return ProductQuantityRequestDTO.builder().productId(1L).quantity(10).build();
    }

    public static ProductQuantityRequestDTO createValidProduct4(){
        return ProductQuantityRequestDTO.builder().productId(2L).quantity(5).build();
    }

    public static ProductQuantityRequestDTO createInvalidProduct1(){
        return ProductQuantityRequestDTO.builder().productId(1L).quantity(10000).build();
    }

    public static ProductQuantityRequestDTO createInvalidProduct2(){
        return ProductQuantityRequestDTO.builder().productId(2L).quantity(50000).build();
    }

    public static List<ProductQuantityRequestDTO> createValidProductsDTO(){
        return Arrays.asList(createValidProduct1(), createValidProduct2());
    }

    public static List<ProductQuantityRequestDTO> createValidProductsUpdateDTO(){
        return Arrays.asList(createValidProduct3(), createValidProduct4());
    }

    public static List<ProductQuantityRequestDTO> createInvalidProductsDTO(){
        return Arrays.asList(createInvalidProduct1(), createInvalidProduct2());
    }
}
