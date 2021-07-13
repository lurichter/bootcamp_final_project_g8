package com.mercadolibre.group8_bootcamp_finalproject.service;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductBatchDTO;

public interface IBatchService {

    ProductBatchDTO listProductBatches(Long productId, String[] order);

    BatchStockDTO removeExpiredBatch(Long batchId);
}
