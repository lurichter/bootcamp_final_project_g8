package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateListDTO;

public interface IDueDateService {
    BatchStockDueDateListDTO listBatchPerWarehouseSectionOrderedByDueDate
            (Integer daysQuantity, String[] order);

    BatchStockDueDateListDTO listBatchPerProductCategoryOrderedByDueDate
            (Integer daysQuantity, String productCategory, String[] order);
}
