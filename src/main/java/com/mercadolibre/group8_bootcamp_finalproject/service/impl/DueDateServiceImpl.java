package com.mercadolibre.group8_bootcamp_finalproject.service.impl;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.ProductNotInBatchException;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import com.mercadolibre.group8_bootcamp_finalproject.repository.*;
import com.mercadolibre.group8_bootcamp_finalproject.service.IDueDateService;
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
    private final UserRepository userRepository;

    @Override
    public BatchStockDueDateListDTO listBatchesOrderedByDueDate(Integer daysQuantity, ProductCategoryEnum category, String[] order){
        //        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
//        String user = SessionServiceImpl.getUsername(token);
        Long userId = userRepository.findByName("operador1@mercadolivre.com").getId();

        List<BatchStockDueDateDTO> batchStockDueDate =
                batchRepository.findAllByWarehouseSectionWhereDueDateLessThanParam(userId, dueDateFilter(daysQuantity), SortUtil.sortStringToSort(order));

        if (batchStockDueDate.isEmpty()) throw new ProductNotInBatchException("The Product isn't in a Batch");

        if (category != null) return new BatchStockDueDateListDTO(filterByCategory(batchStockDueDate, category));

        return new BatchStockDueDateListDTO(batchStockDueDate);
    }

    private LocalDate dueDateFilter(Integer daysQuantity){
        return LocalDate.now().plusDays(daysQuantity);
    }

    private List<BatchStockDueDateDTO> filterByCategory(List<BatchStockDueDateDTO> batchStockDueDate, ProductCategoryEnum category){
        return batchStockDueDate.stream().filter(batch -> batch.getProductCategory().equals(category.getLabel())).collect(Collectors.toList());
    }

}
