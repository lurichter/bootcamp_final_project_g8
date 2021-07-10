package com.mercadolibre.group8_bootcamp_finalproject.service.impl;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseTotalProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.ProductNotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.WarehouseProductNotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.WarehouseSectionCapabilityException;
import com.mercadolibre.group8_bootcamp_finalproject.model.Product;
import com.mercadolibre.group8_bootcamp_finalproject.model.WarehouseSection;
import com.mercadolibre.group8_bootcamp_finalproject.repository.ProductRepository;
import com.mercadolibre.group8_bootcamp_finalproject.repository.SellerRepository;
import com.mercadolibre.group8_bootcamp_finalproject.repository.WarehouseSectionRepository;
import com.mercadolibre.group8_bootcamp_finalproject.service.IWarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImpl implements IWarehouseService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final WarehouseSectionRepository warehouseSectionRepository;

    @Override
    public WarehouseProductListDTO findAllProductsFromWarehouseById(Integer id) {

        // valid product
        this.getProductById(id);

        // valid seller
        this.isValidSeller(id);

        List<WarehouseTotalProductDTO> allProductsFromWarehouse = warehouseSectionRepository.findAllProductsFromWarehouse(id.longValue());

        if ( allProductsFromWarehouse.isEmpty() ) throw new WarehouseProductNotFoundException();

        return WarehouseProductListDTO.builder()
                .productId(id.longValue())
                .warehouses(allProductsFromWarehouse)
                .build();
    }

    @Override
    public void verifySectionCapability(WarehouseSection warehouseSection, int quantity) {
        if ( warehouseSection.getCurrentAvailability() < quantity ) {
            throw new WarehouseSectionCapabilityException();
        }
    }

    @Override
    public void decreaseWarehouseSectionCapacity(WarehouseSection warehouseSection, int quantity) {
        verifySectionCapability(warehouseSection, quantity);
        int newQuantity = warehouseSection.getCurrentAvailability() - quantity;
        warehouseSection.setCurrentAvailability(newQuantity);
        warehouseSectionRepository.save(warehouseSection);
    }

    private void isValidSeller (Integer id) {
        Long seller_id = this.getProductById(id).getSeller().getId();
        sellerRepository.findById(seller_id).orElseThrow(() -> new NotFoundException("Seller not found"));
    }

    private Product getProductById (Integer id) {
        return productRepository.findById(id.longValue()).orElseThrow(ProductNotFoundException::new);
    }
}
