package com.mercadolibre.group8_bootcamp_finalproject.service.impl;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.repository.*;
import com.mercadolibre.group8_bootcamp_finalproject.service.IDueDateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DueDateServiceImpl implements IDueDateService {
    private final WarehouseRepository warehouseRepository;
    private final WarehouseSectionRepository warehouseSectionRepository;
    private final BatchRepository batchRepository;
    private final OperatorRepository operatorRepository;
    private final UserRepository userRepository;

    @Override
    public BatchStockDueDateListDTO listBatchPerWarehouseSectionOrderedByDueDate(Integer daysQuantity, String[] order){
//        String token = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
//        String user = SessionServiceImpl.getUsername(token);
        Long userId = userRepository.findByName("operador1@mercadolivre.com").getId();
        operatorRepository.findOperatorByUserId(userId);
        List<BatchStockDueDateDTO> batchStockDueDateDTOS =
                batchRepository.findAllByWarehouseSectionWhereDueDateLessThanParam(userId, dueDateFilter(daysQuantity));
        return new BatchStockDueDateListDTO(batchStockDueDateDTOS);
    }

    private LocalDate dueDateFilter(Integer daysQuantity){
        return LocalDate.now().plusDays(daysQuantity);
    }

    @Override
    public BatchStockDueDateListDTO listBatchPerProductCategoryOrderedByDueDate
            (Integer daysQuantity, String productCategory, String[] order){
        return null;
    };

}
