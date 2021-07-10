package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseProductListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.WarehouseSection;

public interface IWarehouseService {

    WarehouseProductListDTO findAllProductsFromWarehouseById (Long id);

    void verifySectionCapability(WarehouseSection warehouseSection, int quantity);

    void decreaseWarehouseSectionCapacity(WarehouseSection warehouseSection, int quantity);

}
