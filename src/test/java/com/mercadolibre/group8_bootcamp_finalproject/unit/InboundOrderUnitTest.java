package com.mercadolibre.group8_bootcamp_finalproject.unit;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchResponseListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.*;
import com.mercadolibre.group8_bootcamp_finalproject.repository.*;
import com.mercadolibre.group8_bootcamp_finalproject.services.InboundOrderServiceImpl;
import com.mercadolibre.group8_bootcamp_finalproject.util.MockitoExtension;
import com.mercadolibre.group8_bootcamp_finalproject.util.TestObjectsUtil;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import static org.mockito.AdditionalMatchers.*;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class InboundOrderUnitTest {

    private TestObjectsUtil testObjects;

    @Mock
    private BatchRepository batchRepository;
    @Mock
    private InboudOrderRepository inboundOrderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private WarehouseSectionRepository warehouseSectionRepository;
    @Mock
    private WarehouseOperatorRepository warehouseOperatorRepository;

    @InjectMocks
    private InboundOrderServiceImpl inboundOrderService;

    @BeforeEach
    void setUp() {

        this.testObjects = new TestObjectsUtil();

        Product freshProduct1 = this.testObjects.getFreshProducts().get(0);
        Product freshProduct2 = this.testObjects.getFreshProducts().get(0);
        WarehouseSection freshWarehouseSection = this.testObjects.getFreshWarehouseSections().get(0);
        WarehouseSection frozenWarehouseSection = this.testObjects.getFrozenWarehouseSections().get(0);
        WarehouseOperator warehouseOperator = this.testObjects.getWarehouseOperators().get(0);
        InboundOrder inboundOrder = this.testObjects.getFreshInboundOrders().get(0);
        List<Batch> batches = this.testObjects.getFreshBatches();

        BDDMockito.doReturn(Optional.of(freshProduct1)).when(productRepository).findById(freshProduct1.getId());
        BDDMockito.doReturn(Optional.of(freshProduct2)).when(productRepository).findById(freshProduct2.getId());
        BDDMockito.doThrow(new NotFoundException("Product not found"))
                .when(productRepository)
                .findById(not(or(ArgumentMatchers.eq(freshProduct1.getId()),ArgumentMatchers.eq(freshProduct2.getId()))));

        BDDMockito.doReturn(freshProduct1).when(productRepository).getOne(freshProduct1.getId());
        BDDMockito.doReturn(freshProduct2).when(productRepository).getOne(freshProduct2.getId());
        BDDMockito.doThrow(new NotFoundException("Product not found"))
                .when(productRepository)
                .getOne(not(or(ArgumentMatchers.eq(freshProduct1.getId()),ArgumentMatchers.eq(freshProduct2.getId()))));

        BDDMockito.doReturn(Optional.of(freshWarehouseSection)).when(warehouseSectionRepository).findById(freshWarehouseSection.getId());
        BDDMockito.doReturn(Optional.of(frozenWarehouseSection)).when(warehouseSectionRepository).findById(frozenWarehouseSection.getId());
        BDDMockito.doThrow(new NotFoundException("Warehouse Section not found"))
                .when(productRepository)
                .getOne(not(or(ArgumentMatchers.eq(freshWarehouseSection.getId()),ArgumentMatchers.eq(frozenWarehouseSection.getId()))));

        BDDMockito.doReturn(Arrays.asList(warehouseOperator)).when(warehouseOperatorRepository).findByWarehouseCode(freshWarehouseSection.getWarehouse().getId());

        BDDMockito.when(inboundOrderRepository.save(inboundOrder))
                .thenAnswer(invocation -> {
                    Object[] args = invocation.getArguments();
                    ((InboundOrder)args[0]).setId(1L);
                    return args[0];
                });

        BDDMockito.when(batchRepository.saveAll(batches))
                .thenAnswer(invocation -> {
                    Object[] args = invocation.getArguments();
                    ((List<Batch>)args[0]).get(0).setId(1L);
                    ((List<Batch>)args[0]).get(1).setId(2L);
                    return args[0];
                });
    }

    @Test
    public void returnBatchResponseListWhenValidInboundOrder() {

        InboundOrderRequestDTO inboundOrderRequestDTO = this.testObjects.getFreshInboundOrderRequestDTOS().get(0);
        BatchResponseListDTO batchResponseListDTO = this.inboundOrderService.createInboundOrder(inboundOrderRequestDTO);

        Assertions.assertThat(batchResponseListDTO.getBatchStock())
                .extracting(
                        record -> record.getBatchNumber(),
                        record -> record.getProductId(),
                        record -> record.getCurrentTemperature(),
                        record -> record.getQuantity(),
                        record -> record.getManufacturingDate(),
                        record -> record.getManufacturingTime(),
                        record -> record.getDueDate())
                .contains(
                        Tuple.tuple(
                                inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(0).getBatchNumber(),
                                inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(0).getProductId(),
                                inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(0).getCurrentTemperature(),
                                inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(0).getQuantity(),
                                inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(0).getManufacturingDate(),
                                inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(0).getManufacturingTime(),
                                inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(0).getDueDate()),
                        Tuple.tuple(
                                inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(1).getBatchNumber(),
                                inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(1).getProductId(),
                                inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(1).getCurrentTemperature(),
                                inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(1).getQuantity(),
                                inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(1).getManufacturingDate(),
                                inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(1).getManufacturingTime(),
                                inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(1).getDueDate()));

    }

    @Test
    public void returnExceptionWhenInvalidProduct() {

        InboundOrderRequestDTO inboundOrderRequestDTO = this.testObjects.getFreshInboundOrderRequestDTOS().get(0);
        inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(0).setProductId(3);

        Assertions.assertThatThrownBy(() -> {this.inboundOrderService.createInboundOrder(inboundOrderRequestDTO);})
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Not Found Exception. Product not found");

    }

    @Test
    public void returnExceptionWhenInvalidWarehouseSection() {

        InboundOrderRequestDTO inboundOrderRequestDTO = this.testObjects.getFreshInboundOrderRequestDTOS().get(0);
        inboundOrderRequestDTO.getInboundOrder().getSection().setSectionCode(3);

        Assertions.assertThatThrownBy(() -> {this.inboundOrderService.createInboundOrder(inboundOrderRequestDTO);})
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Not Found Exception. Warehouse Section not found");

    }

    @Test
    public void returnExceptionWhenOperatorNotRegisteredInWarehouse() {

    }

    @Test
    public void returnExceptionWhenProductCategoryNotAllowedInWarehouseSection() {

        InboundOrderRequestDTO inboundOrderRequestDTO = this.testObjects.getFreshInboundOrderRequestDTOS().get(0);
        inboundOrderRequestDTO.getInboundOrder().getSection().setSectionCode(2);

        Assertions.assertThatThrownBy(() -> {this.inboundOrderService.createInboundOrder(inboundOrderRequestDTO);})
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Product category is invalid to Warehouse Section Category.");
    }

    @Test
    public void returnExceptionWhenOrderQuantityBiggerThanWarehouseSectionAvailability() {

        InboundOrderRequestDTO inboundOrderRequestDTO = this.testObjects.getFreshInboundOrderRequestDTOS().get(0);
        inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(0).setQuantity(100000);

        Assertions.assertThatThrownBy(() -> {this.inboundOrderService.createInboundOrder(inboundOrderRequestDTO);})
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Not Found Exception. WarehouseSection current capability is less than all quantity products from batch stock");
    }


}