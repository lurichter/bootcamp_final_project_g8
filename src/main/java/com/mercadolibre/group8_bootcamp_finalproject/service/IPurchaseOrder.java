package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.ProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.PurchaseOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.PurchaseOrderPriceResponseDTO;

import java.util.Set;

public interface IPurchaseOrder {

    PurchaseOrderPriceResponseDTO savePurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO);

    public Set<ProductDTO> getAllProductsFromPurchaseOrder(Long orderId);

    public PurchaseOrderPriceResponseDTO updatePurchaseOrder(PurchaseOrderRequestDTO purchaseOrderRequestDTO, Long purchaseOrderId);
}
