package com.mercadolibre.group8_bootcamp_finalproject.unit;

import com.mercadolibre.group8_bootcamp_finalproject.repository.BatchRepository;
import com.mercadolibre.group8_bootcamp_finalproject.service.IDueDateService;
import com.mercadolibre.group8_bootcamp_finalproject.service.IOperatorService;
import com.mercadolibre.group8_bootcamp_finalproject.util.SortUtil;
import com.mercadolibre.group8_bootcamp_finalproject.util.TestObjectsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
public class DueDateUnitTest {

    private TestObjectsUtil testObjects;

    @Mock
    private BatchRepository batchRepository;

    @Mock
    private IDueDateService dueDateService;

    @Mock
    private IOperatorService operatorService;

    Long operatorId;

    Sort sortDesc = SortUtil.sortStringToSort(new String[]{"dueDate_desc"});
    Sort sortAsc = SortUtil.sortStringToSort(new String[]{"dueDate_asc"});

    @BeforeEach
    void setup(){
        this.testObjects = new TestObjectsUtil();

        operatorId = this.testObjects.getOperators().get(0).getId();

        BDDMockito.doReturn(operatorId).when(operatorService).getLoggedUserOperatorId();
    }

    private LocalDate dueDateFilter(Integer daysQuantity){
        return LocalDate.now().plusDays(daysQuantity);
    }

    @Test
    public void returnBatchStockListOrderedByDescDueDate(){
        BDDMockito.doReturn(this.testObjects.getBatchStockDueDate1())
               .when(batchRepository).findAllByWarehouseSectionWhereDueDateLessThanParam
                (operatorId, dueDateFilter(10), sortDesc);

//        given(dueDateService.listBatchesOrderedByDueDate(10, null, new String[]{"dueDate_desc"}))
//        BatchStockDueDateListDTO listBatchesOrderedByDueDate(Integer daysQuantity, ProductCategoryEnum category, String[] order)
    }

    @Test
    public void returnBatchStockListOrderedByAscDueDate(){
        BDDMockito.doReturn(this.testObjects.getBatchStockDueDate1())
                .when(batchRepository).findAllByWarehouseSectionWhereDueDateLessThanParam
                (operatorId, dueDateFilter(10), sortAsc);
    }

    @Test
    public void returnBatchStockListOrderedByDescDueDateAndFilteredByProductCategory(){
        BDDMockito.doReturn(this.testObjects.getBatchStockDueDate1())
                .when(batchRepository).findAllByWarehouseSectionWhereDueDateLessThanParam
                (operatorId, dueDateFilter(10), sortDesc);
//        add category param
    }

    @Test
    public void returnBatchStockListOrderedByAscDueDateAndFilteredByProductCategory(){
        BDDMockito.doReturn(this.testObjects.getBatchStockDueDate1())
                .when(batchRepository).findAllByWarehouseSectionWhereDueDateLessThanParam
                (operatorId, dueDateFilter(10), sortAsc);
//        add category param
    }

    @Test
    public void returnBatchNotFoundExceptionWhenNoBatchIsFoundWithGivenDueDateRange(){
        BDDMockito.doReturn(this.testObjects.getBatchStockDueDate1())
                .when(batchRepository).findAllByWarehouseSectionWhereDueDateLessThanParam
                (operatorId, dueDateFilter(-1000), sortDesc);
    }

    @Test
    public void returnBatchNotFoundExceptionWhenNoBatchIsFoundWithGivenDueDateRangeAndProductCategoryFilter(){
        BDDMockito.doReturn(this.testObjects.getBatchStockDueDate1())
                .when(batchRepository).findAllByWarehouseSectionWhereDueDateLessThanParam
                (operatorId, dueDateFilter(10), sortDesc);
    }
    //        add invalid category param
}