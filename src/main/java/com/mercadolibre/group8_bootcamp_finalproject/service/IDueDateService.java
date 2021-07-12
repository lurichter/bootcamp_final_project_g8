package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;

public interface IDueDateService {
    BatchStockDueDateListDTO listBatchesOrderedByDueDate
            (Integer daysQuantity, ProductCategoryEnum productCategory, String[] order);
}
