package com.mercadolibre.group8_bootcamp_finalproject.service.impl;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.BatchNotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import com.mercadolibre.group8_bootcamp_finalproject.repository.BatchRepository;
import com.mercadolibre.group8_bootcamp_finalproject.service.IDueDateService;
import com.mercadolibre.group8_bootcamp_finalproject.service.IOperatorService;
import com.mercadolibre.group8_bootcamp_finalproject.util.SortUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DueDateServiceImpl implements IDueDateService {

    private final BatchRepository batchRepository;

    private final IOperatorService operatorService;

    @Override
    public BatchStockDueDateListDTO listBatchesOrderedByDueDate(Integer daysQuantity, ProductCategoryEnum category, String[] order){
        Long operatorId = operatorService.getLoggedUserOperatorId();

        List<BatchStockDueDateDTO> batchStockDueDate =
                batchRepository.findAllByWarehouseSectionWhereDueDateLessThanParam(operatorId, dueDateFilter(daysQuantity), SortUtil.sortStringToSort(order));

        if (category != null) return new BatchStockDueDateListDTO(filterByCategory(batchStockDueDate, category));

        if (batchStockDueDate.isEmpty()) throw new BatchNotFoundException("There are no batches with the due date between the given range.");

        return new BatchStockDueDateListDTO(batchStockDueDate);
    }

    private LocalDate dueDateFilter(Integer daysQuantity){
        return LocalDate.now().plusDays(daysQuantity);
    }

    private List<BatchStockDueDateDTO> filterByCategory(List<BatchStockDueDateDTO> batchStockDueDate, ProductCategoryEnum category){
        List<BatchStockDueDateDTO> batchStockDueDateResponse= batchStockDueDate
                .stream()
                .filter(batch -> batch.getProductCategory().equals(category.getLabel()))
                .collect(Collectors.toList());
        if (batchStockDueDateResponse.isEmpty()) throw new BatchNotFoundException("There are no batches with the due date between the given range and the category selected.");
        return batchStockDueDateResponse;
    }

}
