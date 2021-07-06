package com.mercadolibre.group8_bootcamp_finalproject.service.impl;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductBatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.SectionDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.ProductNotInBatchException;
import com.mercadolibre.group8_bootcamp_finalproject.model.Batch;
import com.mercadolibre.group8_bootcamp_finalproject.model.WarehouseSection;
import com.mercadolibre.group8_bootcamp_finalproject.repository.BatchRepository;
import com.mercadolibre.group8_bootcamp_finalproject.repository.ProductRepository;
import com.mercadolibre.group8_bootcamp_finalproject.service.IBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements IBatchService {

    private final BatchRepository batchRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductBatchDTO listProductBatches(Long productId) {
        Set<Batch> batches = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found")).getBatch();

        if (batches.isEmpty()) throw new ProductNotInBatchException();
        return toProductBatchDTO(productId, batches);
    }

    private ProductBatchDTO toProductBatchDTO(Long productId, Set<Batch> batches){
        WarehouseSection warehouseSection = batches.stream().findFirst().orElseThrow(() -> new NotFoundException("Section not found")).getWarehouseSection();

        return ProductBatchDTO.builder()
                .productId(productId)
                .section(toSectionDTO(warehouseSection))
                .batchStock(toBatchStockDTOList(batches))
                .build();
    }

    private SectionDTO toSectionDTO(WarehouseSection warehouseSection){
        return SectionDTO.builder().sectionCode(warehouseSection.getId()).warehouseCode(warehouseSection.getWarehouse().getId()).build();
    }

    private List<BatchStockDTO> toBatchStockDTOList(Set<Batch> batches){
        List<BatchStockDTO> batchStockDTOList = new ArrayList<>();
        batches.forEach(batch -> batchStockDTOList.add(BatchStockDTO.builder()
            .batchNumber(batch.getId())
            .currentQuantity(batch.getQuantity())
            .dueDate(batch.getDueDate())
            .build()));
        return batchStockDTOList;
    }

}
