package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseProductListDTO;

public interface IWarehouseService {

    WarehouseProductListDTO findAllProductsFromWarehouseById (Integer id);
}
