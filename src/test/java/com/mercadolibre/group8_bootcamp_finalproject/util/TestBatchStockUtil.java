package com.mercadolibre.group8_bootcamp_finalproject.util;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.Batch;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;


@Getter
public class TestBatchStockUtil {

    TestObjectsUtil2 testObjects = new TestObjectsUtil2();

    private BatchStockDueDateListDTO batchStockDueDateListFresh = new BatchStockDueDateListDTO();
    private BatchStockDueDateListDTO batchStockDueDateListFrozen = new BatchStockDueDateListDTO();
    private BatchStockDueDateListDTO batchStockDueDateListChilled = new BatchStockDueDateListDTO();
    private BatchStockDueDateListDTO batchStockDueDateList1 = new BatchStockDueDateListDTO();
    private BatchStockDueDateListDTO batchStockDueDateList2 = new BatchStockDueDateListDTO();
    private BatchStockDueDateListDTO batchStockDueDateListOneDayAhead = new BatchStockDueDateListDTO();

//    private List<BatchStockDueDateDTO> batchStockDueDate1 = new ArrayList<>();

    Batch freshBatch1 = this.testObjects.getFreshBatches().get(0);
    Batch freshBatch2 = this.testObjects.getFreshBatches().get(1);
    Batch frozenBatch1 = this.testObjects.getFrozenBatches().get(0);
    Batch frozenBatch2 = this.testObjects.getFrozenBatches().get(1);
    Batch chilledBatch1 = this.testObjects.getChilledBatches().get(0);
    Batch chilledBatch2 = this.testObjects.getChilledBatches().get(1);

    public TestBatchStockUtil() {
        BatchStockDueDateDTO batchStockDueDateDTO1 = BatchStockDueDateDTO.builder()
                .quantity(freshBatch1.getQuantity())
                .dueDate(freshBatch1.getDueDate()) // LocalDate.now().plusDays(14)
                .productId(freshBatch1.getProduct().getId())
                .batchNumber(freshBatch1.getNumber())
                .productCategory(freshBatch1.getProduct().getProductCategory().getName().getLabel())
                .sectionId(freshBatch1.getWarehouseSection().getId())
                .build();

        BatchStockDueDateDTO batchStockDueDateDTO2 = BatchStockDueDateDTO.builder()
                .quantity(freshBatch2.getQuantity())
                .dueDate(freshBatch2.getDueDate()) // LocalDate.now().plusDays(1)
                .productId(freshBatch2.getProduct().getId())
                .batchNumber(freshBatch2.getNumber())
                .productCategory(freshBatch2.getProduct().getProductCategory().getName().getLabel())
                .sectionId(freshBatch2.getWarehouseSection().getId())
                .build();

        List<BatchStockDueDateDTO> freshBatchStockDueDate = new ArrayList<>();
        freshBatchStockDueDate.add(0, batchStockDueDateDTO1);
        freshBatchStockDueDate.add(1, batchStockDueDateDTO2);

        BatchStockDueDateDTO batchStockDueDateDTO3 = BatchStockDueDateDTO.builder()
                .quantity(frozenBatch1.getQuantity())
                .dueDate(frozenBatch1.getDueDate()) //LocalDate.now().plusDays(60)
                .productId(frozenBatch1.getProduct().getId())
                .batchNumber(frozenBatch1.getNumber())
                .productCategory(frozenBatch1.getProduct().getProductCategory().getName().getLabel())
                .sectionId(frozenBatch1.getWarehouseSection().getId())
                .build();

        BatchStockDueDateDTO batchStockDueDateDTO4 = BatchStockDueDateDTO.builder()
                .quantity(frozenBatch2.getQuantity())
                .dueDate(frozenBatch2.getDueDate()) //LocalDate.now().plusDays(30)
                .productId(frozenBatch2.getProduct().getId())
                .batchNumber(frozenBatch2.getNumber())
                .productCategory(frozenBatch2.getProduct().getProductCategory().getName().getLabel())
                .sectionId(frozenBatch2.getWarehouseSection().getId())
                .build();

        List<BatchStockDueDateDTO> frozenBatchStockDueDate = new ArrayList<>();
        frozenBatchStockDueDate.add(0, batchStockDueDateDTO4);
        frozenBatchStockDueDate.add(1, batchStockDueDateDTO3);

        BatchStockDueDateDTO batchStockDueDateDTO5 = BatchStockDueDateDTO.builder()
                .quantity(chilledBatch1.getQuantity())
                .dueDate(chilledBatch1.getDueDate())//LocalDate.now().plusDays(10)
                .productId(chilledBatch1.getProduct().getId())
                .batchNumber(chilledBatch1.getNumber())
                .productCategory(chilledBatch1.getProduct().getProductCategory().getName().getLabel())
                .sectionId(chilledBatch1.getWarehouseSection().getId())
                .build();

        BatchStockDueDateDTO batchStockDueDateDTO6 = BatchStockDueDateDTO.builder()
                .quantity(chilledBatch2.getQuantity())
                .dueDate(chilledBatch2.getDueDate())//LocalDate.now().plusDays(25)
                .productId(chilledBatch2.getProduct().getId())
                .batchNumber(chilledBatch2.getNumber())
                .productCategory(chilledBatch2.getProduct().getProductCategory().getName().getLabel())
                .sectionId(chilledBatch2.getWarehouseSection().getId())
                .build();

        List<BatchStockDueDateDTO> chilledBatchStockDueDate = new ArrayList<>();
        chilledBatchStockDueDate.add(0, batchStockDueDateDTO6);
        chilledBatchStockDueDate.add(1, batchStockDueDateDTO5);

        List<BatchStockDueDateDTO> batchStockDueDate1 = new ArrayList<>();
        batchStockDueDate1.add(0, batchStockDueDateDTO1);
        batchStockDueDate1.add(1, batchStockDueDateDTO5);

        List<BatchStockDueDateDTO> batchStockDueDate2 = new ArrayList<>();
        batchStockDueDate2.add(0, batchStockDueDateDTO6);
        batchStockDueDate2.add(1, batchStockDueDateDTO4);

        List<BatchStockDueDateDTO> batchStockDueDateOneDayAhead = new ArrayList<>();
        batchStockDueDateOneDayAhead.add(batchStockDueDateDTO2);

        this.batchStockDueDateListFresh.setBatchStock(freshBatchStockDueDate);
        this.batchStockDueDateListFrozen.setBatchStock(frozenBatchStockDueDate);
        this.batchStockDueDateListChilled.setBatchStock(chilledBatchStockDueDate);
        this.batchStockDueDateList1.setBatchStock(batchStockDueDate1);
        this.batchStockDueDateList2.setBatchStock(batchStockDueDate2);
        this.batchStockDueDateListOneDayAhead.setBatchStock(batchStockDueDateOneDayAhead);
    }
}
