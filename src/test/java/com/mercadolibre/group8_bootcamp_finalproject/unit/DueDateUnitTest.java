package com.mercadolibre.group8_bootcamp_finalproject.unit;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.BatchNotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import com.mercadolibre.group8_bootcamp_finalproject.repository.BatchRepository;
import com.mercadolibre.group8_bootcamp_finalproject.service.IOperatorService;
import com.mercadolibre.group8_bootcamp_finalproject.service.impl.DueDateServiceImpl;
import com.mercadolibre.group8_bootcamp_finalproject.util.SortUtil;
import com.mercadolibre.group8_bootcamp_finalproject.util.TestBatchStockUtil;
import com.mercadolibre.group8_bootcamp_finalproject.util.TestObjectsUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class DueDateUnitTest {

    private TestObjectsUtil testObjects;

    private TestBatchStockUtil testBatchStock;

    private BatchStockDueDateListDTO testBatchStockDueDateListDTO;

    @Mock
    private BatchRepository batchRepository;

    @InjectMocks
    private DueDateServiceImpl dueDateService;

    @Mock
    private IOperatorService operatorService;

    Long operatorId;

    Sort sortDesc = SortUtil.sortStringToSort(new String[]{"dueDate_desc"});
    Sort sortAsc = SortUtil.sortStringToSort(new String[]{"dueDate_asc"});

    @BeforeEach
    void setup(){
        this.testObjects = new TestObjectsUtil();
        this.testBatchStock = new TestBatchStockUtil();

        this.testBatchStockDueDateListDTO = new BatchStockDueDateListDTO();

        operatorId = this.testObjects.getOperators().get(0).getId();

        BDDMockito.doReturn(operatorId).when(operatorService).getLoggedUserOperatorId();
    }

    private LocalDate dueDateFilter(Integer daysQuantity){
        return LocalDate.now().plusDays(daysQuantity);
    }

    @Test
    public void returnBatchStockListOrderedByDescDueDate(){
        List<BatchStockDueDateDTO> batchStockDueDateList = this.testBatchStock.getBatchStockDueDateList1().getBatchStock();

        BDDMockito.doReturn(batchStockDueDateList)
               .when(this.batchRepository).findAllByWarehouseSectionWhereDueDateLessThanParam
                (this.operatorId, dueDateFilter(15), sortDesc);

        this.testBatchStockDueDateListDTO.setBatchStock(batchStockDueDateList);

        BatchStockDueDateListDTO serviceBatchStockDueDateList = this.dueDateService
                .listBatchesOrderedByDueDate(15, null, new String[]{"dueDate_desc"});

        Assertions.assertThat(this.testBatchStockDueDateListDTO).isEqualTo(serviceBatchStockDueDateList);

    }

    @Test
    public void returnBatchStockListOrderedByAscDueDate(){
        List<BatchStockDueDateDTO> batchStockDueDateList = this.testBatchStock.getBatchStockDueDateList2().getBatchStock();

        BDDMockito.doReturn(batchStockDueDateList)
                .when(this.batchRepository).findAllByWarehouseSectionWhereDueDateLessThanParam
                (this.operatorId, dueDateFilter(35), sortAsc);

        this.testBatchStockDueDateListDTO.setBatchStock(batchStockDueDateList);

        BatchStockDueDateListDTO serviceBatchStockDueDateList = this.dueDateService
                .listBatchesOrderedByDueDate(35, null, new String[]{"dueDate_asc"});

        Assertions.assertThat(this.testBatchStockDueDateListDTO).isEqualTo(serviceBatchStockDueDateList);
    }

    @Test
    public void returnBatchStockListOrderedByDescDueDateAndFilteredByProductCategory(){
        List<BatchStockDueDateDTO> batchStockDueDateList = this.testBatchStock.getBatchStockDueDateListFresh().getBatchStock();

        BDDMockito.doReturn(batchStockDueDateList)
                .when(this.batchRepository).findAllByWarehouseSectionWhereDueDateLessThanParam
                (this.operatorId, dueDateFilter(100), sortDesc);

        this.testBatchStockDueDateListDTO.setBatchStock(batchStockDueDateList);

        BatchStockDueDateListDTO serviceBatchStockDueDateList = this.dueDateService
                .listBatchesOrderedByDueDate(100, ProductCategoryEnum.FS, new String[]{"dueDate_desc"});

        Assertions.assertThat(this.testBatchStockDueDateListDTO).isEqualTo(serviceBatchStockDueDateList);

    }

    @Test
    public void returnBatchStockListOrderedByAscDueDateAndFilteredByProductCategory(){
        List<BatchStockDueDateDTO> batchStockDueDateList = this.testBatchStock.getBatchStockDueDateListFrozen().getBatchStock();

        BDDMockito.doReturn(batchStockDueDateList)
                .when(this.batchRepository).findAllByWarehouseSectionWhereDueDateLessThanParam
                (this.operatorId, dueDateFilter(100), sortAsc);

        this.testBatchStockDueDateListDTO.setBatchStock(batchStockDueDateList);

        BatchStockDueDateListDTO serviceBatchStockDueDateList = this.dueDateService
                .listBatchesOrderedByDueDate(100, ProductCategoryEnum.FF, new String[]{"dueDate_asc"});

        Assertions.assertThat(this.testBatchStockDueDateListDTO).isEqualTo(serviceBatchStockDueDateList);
    }

    @Test
    public void returnBatchNotFoundExceptionWhenNoBatchIsFoundWithGivenDueDateRange(){
        List<BatchStockDueDateDTO> batchStockDueDateList = this.testBatchStock.getBatchStockDueDateListOneDayAhead().getBatchStock();

        BDDMockito.doReturn(batchStockDueDateList)
                .when(this.batchRepository).findAllByWarehouseSectionWhereDueDateLessThanParam
                (this.operatorId, dueDateFilter(3), sortAsc);

        Assertions
                .assertThatThrownBy(() ->
                        this.dueDateService.listBatchesOrderedByDueDate
                                (3, null, new String[]{"dueDate_asc"}))
                .isInstanceOf(BatchNotFoundException.class)
                .hasMessage("Bad Request Exception. There are no batches with the due date between the given range.");
    }

    @Test
    public void returnBatchNotFoundExceptionWhenNoBatchIsFoundWithGivenDueDateRangeAndProductCategoryFilter(){
        List<BatchStockDueDateDTO> batchStockDueDateList = this.testBatchStock.getBatchStockDueDateListFrozen().getBatchStock();

        BDDMockito.doReturn(batchStockDueDateList)
                .when(this.batchRepository).findAllByWarehouseSectionWhereDueDateLessThanParam
                (this.operatorId, dueDateFilter(35), sortAsc);

        Assertions
                .assertThatThrownBy(() ->
                        this.dueDateService.listBatchesOrderedByDueDate
                                (35, ProductCategoryEnum.FS, new String[]{"dueDate_asc"}))
                .isInstanceOf(BatchNotFoundException.class)
                .hasMessage("Bad Request Exception. There are no batches with the due date between the given range and the category selected.");
    }
}