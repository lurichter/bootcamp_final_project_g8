package com.mercadolibre.group8_bootcamp_finalproject.service.impl;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.ProductBatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.SectionDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.ProductNotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.ProductNotInBatchException;
import com.mercadolibre.group8_bootcamp_finalproject.model.Batch;
import com.mercadolibre.group8_bootcamp_finalproject.model.WarehouseSection;
import com.mercadolibre.group8_bootcamp_finalproject.repository.BatchRepository;
import com.mercadolibre.group8_bootcamp_finalproject.repository.ProductRepository;
import com.mercadolibre.group8_bootcamp_finalproject.service.IBatchService;
import com.mercadolibre.group8_bootcamp_finalproject.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BatchServiceImpl implements IBatchService {

    private final BatchRepository batchRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductBatchDTO listProductBatches(Long productId, String[] order) {

        if (!productRepository.existsById(productId)) throw new ProductNotFoundException();

        List<Batch> batches = batchRepository.findBatchByProductIdAndByDueDate(productId, minimunDueDate(), SortUtil.sortStringToSort(order));

        if (batches.isEmpty()) throw new ProductNotInBatchException("The Product isn't in a Batch");

        return toProductBatchDTO(productId, batches);
    }

    private ProductBatchDTO toProductBatchDTO(Long productId, List<Batch> batches){
        return ProductBatchDTO.builder()
                .productId(productId)
                .batchStock(toBatchStockDTOList(batches))
                .build();
    }

    private List<BatchStockDTO> toBatchStockDTOList(List<Batch> batches){
        List<BatchStockDTO> batchStockDTOList = new ArrayList<>();
        batches.forEach(batch -> batchStockDTOList.add(
                BatchStockDTO.builder()
                        .wareHouseId(batch.getWarehouseSection().getWarehouse().getId())
                        .sectionId(batch.getWarehouseSection().getId())
                        .batchId(batch.getId())
                        .batchNumber(batch.getNumber())
                        .currentQuantity(batch.getQuantity())
                        .dueDate(batch.getDueDate())
                        .build()));
        return batchStockDTOList;
    }

    private LocalDate minimunDueDate(){
        return LocalDate.now().plusDays(21);
    }

}
