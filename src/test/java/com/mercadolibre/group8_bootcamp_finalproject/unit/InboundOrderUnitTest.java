package com.mercadolibre.group8_bootcamp_finalproject.unit;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.InboundOrderResponseDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.*;
import com.mercadolibre.group8_bootcamp_finalproject.model.*;
import com.mercadolibre.group8_bootcamp_finalproject.repository.*;
import com.mercadolibre.group8_bootcamp_finalproject.service.impl.InboundOrderServiceImpl;
import com.mercadolibre.group8_bootcamp_finalproject.service.impl.OperatorService;
import com.mercadolibre.group8_bootcamp_finalproject.service.impl.WarehouseServiceImpl;
import com.mercadolibre.group8_bootcamp_finalproject.util.MockitoExtension;
import com.mercadolibre.group8_bootcamp_finalproject.util.TestObjectsUtil;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.internal.matchers.GreaterThan;

import static org.hamcrest.Matchers.is;
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
    private OperatorRepository operatorRepository;
    @Mock
    private WarehouseSectionRepository warehouseSectionRepository;
    @Mock
    private WarehouseOperatorRepository warehouseOperatorRepository;
    @Mock
    private OperatorService operatorService;
    @Mock
    private WarehouseServiceImpl warehouseService;

    @InjectMocks
    private InboundOrderServiceImpl inboundOrderService;

    @BeforeEach
    void setUp() {

        this.testObjects = new TestObjectsUtil();

        Product freshProduct1 = this.testObjects.getFreshProducts().get(0);
        Product freshProduct2 = this.testObjects.getFreshProducts().get(1);
        List<Long> freshProductsIds = new ArrayList<Long>(Arrays.asList(freshProduct1.getId(), freshProduct2.getId()));
        WarehouseSection freshWarehouseSection = this.testObjects.getFreshWarehouseSections().get(0);
        WarehouseSection frozenWarehouseSection = this.testObjects.getFrozenWarehouseSections().get(0);
        WarehouseOperator warehouseOperator = this.testObjects.getWarehouseOperators().get(0);
        InboundOrder inboundOrder = this.testObjects.getFreshInboundOrders().get(0);
        List<Batch> batches = this.testObjects.getFreshBatches();
        Operator operator = this.testObjects.getOperators().get(0);

        BDDMockito.doReturn(operator).when(operatorService).getLoggedUOperator();

        BDDMockito.doReturn(this.testObjects.getFreshProducts()).when(productRepository).findAllById(freshProductsIds);
        BDDMockito.doNothing().when(operatorService).validateOperatorInWarehouse(warehouseOperator.getOperator().getId(), warehouseOperator.getWarehouse().getId());

        BDDMockito.doReturn(Optional.of(freshWarehouseSection)).when(warehouseSectionRepository).findById(freshWarehouseSection.getId());
        BDDMockito.doReturn(Optional.of(frozenWarehouseSection)).when(warehouseSectionRepository).findById(frozenWarehouseSection.getId());
        BDDMockito.doThrow(new NotFoundException("Warehouse Section not found"))
                .when(productRepository)
                .getOne(not(or(ArgumentMatchers.eq(freshWarehouseSection.getId()),ArgumentMatchers.eq(frozenWarehouseSection.getId()))));
        BDDMockito.doReturn(Arrays.asList(warehouseOperator)).when(warehouseOperatorRepository).findByWarehouseCode(freshWarehouseSection.getWarehouse().getId());

        BDDMockito.doThrow(new OperatorNotInWarehouseException())
                .when(operatorService)
                .validateOperatorInWarehouse(not(ArgumentMatchers.eq(warehouseOperator.getOperator().getId())),
                        ArgumentMatchers.eq(warehouseOperator.getWarehouse().getId()));

        BDDMockito.doThrow(new WarehouseSectionCapabilityException())
                .when(warehouseService)
                .decreaseWarehouseSectionCapacity(ArgumentMatchers.eq(freshWarehouseSection), gt(freshWarehouseSection.getCurrentAvailability().intValue()));

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
    public void returnInboundOrderResponseDTOWhenCreateInboundOrderWithValidInboundOrder() {

        InboundOrderRequestDTO inboundOrderRequestDTO = this.testObjects.getFreshInboundOrderRequestDTOS().get(0);
        InboundOrderResponseDTO inboundOrderResponseDTO = this.inboundOrderService.createInboundOrder(inboundOrderRequestDTO);

        Assertions.assertThat(inboundOrderResponseDTO.getBatchStock())
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
    public void returnExceptionWhenCreateInboundOrderWithInvalidProduct() {

        InboundOrderRequestDTO inboundOrderRequestDTO = this.testObjects.getFreshInboundOrderRequestDTOS().get(0);
        inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(0).setProductId(3L);

        Assertions.assertThatThrownBy(() -> {this.inboundOrderService.createInboundOrder(inboundOrderRequestDTO);})
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageStartingWith("Not Found Exception. The products")
                .hasMessageEndingWith("was not found.");

    }

    @Test
    public void returnExceptionWhenCreateInboundOrderWithInvalidWarehouseOperator() {

        Operator operator = this.testObjects.getOperators().get(1);
        BDDMockito.doReturn(operator).when(operatorService).getLoggedUOperator();

        InboundOrderRequestDTO inboundOrderRequestDTO = this.testObjects.getFreshInboundOrderRequestDTOS().get(0);

        Assertions.assertThatThrownBy(() -> {this.inboundOrderService.createInboundOrder(inboundOrderRequestDTO);})
                .isInstanceOf(OperatorNotInWarehouseException.class)
                .hasMessage("Bad Request Exception. The operator is not in the Warehouse.");

    }

    @Test
    public void returnExceptionWhenCreateInboundOrderWithInvalidWarehouseSection() {

        InboundOrderRequestDTO inboundOrderRequestDTO = this.testObjects.getFreshInboundOrderRequestDTOS().get(0);
        inboundOrderRequestDTO.getInboundOrder().getSection().setSectionCode(3L);

        Assertions.assertThatThrownBy(() -> {this.inboundOrderService.createInboundOrder(inboundOrderRequestDTO);})
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Not Found Exception. Warehouse Section not found");

    }

    @Test
    public void returnExceptionWhenCreateInboundOrderWithProductCategoryNotAllowedInWarehouseSection() {

        InboundOrderRequestDTO inboundOrderRequestDTO = this.testObjects.getFreshInboundOrderRequestDTOS().get(0);
        inboundOrderRequestDTO.getInboundOrder().getSection().setSectionCode(2L);

        Assertions.assertThatThrownBy(() -> {this.inboundOrderService.createInboundOrder(inboundOrderRequestDTO);})
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Bad Request Exception. Product category is invalid to Warehouse Section Category.");
    }

    @Test
    public void returnExceptionWhenCreateInboundOrderWithQuantityBiggerThanWarehouseSectionAvailability() {

        InboundOrderRequestDTO inboundOrderRequestDTO = this.testObjects.getFreshInboundOrderRequestDTOS().get(0);
        inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(0).setQuantity(100000);

        Assertions.assertThatThrownBy(() -> {this.inboundOrderService.createInboundOrder(inboundOrderRequestDTO);})
                .isInstanceOf(WarehouseSectionCapabilityException.class)
                .hasMessage("Bad Request Exception. WarehouseSection current capability is less than all quantity products from batch stock");
    }


}