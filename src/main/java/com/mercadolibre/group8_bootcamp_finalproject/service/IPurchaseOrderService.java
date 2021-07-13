package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.PurchaseOrderPriceResponseDTO;

import java.util.Set;

public interface IPurchaseOrderService {

    PurchaseOrderPriceResponseDTO savePurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO);

    ProductListDTO getAllProductsFromPurchaseOrder(Long orderId);

    PurchaseOrderPriceResponseDTO updatePurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO, Long purchaseOrderId);

    void cancelPurchaseOrder(Long purchaseOrderId);
}
