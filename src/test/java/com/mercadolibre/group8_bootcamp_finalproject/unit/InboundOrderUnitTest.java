package com.mercadolibre.group8_bootcamp_finalproject.unit;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.BatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.WarehouseSectionDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchResponseListDTO;
import com.mercadolibre.group8_bootcamp_finalproject.exceptions.NotFoundException;
import com.mercadolibre.group8_bootcamp_finalproject.model.*;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import com.mercadolibre.group8_bootcamp_finalproject.repository.*;
import com.mercadolibre.group8_bootcamp_finalproject.services.InboundOrderServiceImpl;
import com.mercadolibre.group8_bootcamp_finalproject.util.MockitoExtension;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import static org.mockito.AdditionalMatchers.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class InboundOrderUnitTest {

    InboundOrderRequestDTO inboundOrderRequestDTO = new InboundOrderRequestDTO();
    InboundOrderDTO inboundOrderDTO = new InboundOrderDTO();
    WarehouseSectionDTO warehouseSectionDTO = new WarehouseSectionDTO();
    List<BatchDTO> batchesDTO = new ArrayList<BatchDTO>();

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

        this.warehouseSectionDTO.setSectionCode(1);
        this.warehouseSectionDTO.setWarehouseCode(1);

        BatchDTO batchDTO1 = BatchDTO.builder()
                .batchNumber("MELI0001")
                .productId(1)
                .currentTemperature(10.0)
                .quantity(20000)
                .manufacturingDate(LocalDate.now().minusDays(7))
                .manufacturingTime(LocalTime.now())
                .dueDate(LocalDate.now().plusDays(14))
                .build();

        this.batchesDTO.add(batchDTO1);

        BatchDTO batchDTO2 = BatchDTO.builder()
                .batchNumber("MELI0002")
                .productId(2)
                .currentTemperature(2.0)
                .quantity(20000)
                .manufacturingDate(LocalDate.now().minusDays(7))
                .manufacturingTime(LocalTime.now())
                .dueDate(LocalDate.now().plusDays(14))
                .build();

        this.batchesDTO.add(batchDTO2);

        this.inboundOrderDTO.setSection(this.warehouseSectionDTO);
        this.inboundOrderDTO.setBatchStock(this.batchesDTO);

        this.inboundOrderRequestDTO.setInboundOrder(this.inboundOrderDTO);

        User user1 = User.builder()
                .id(1L)
                .name("operador1@mercadolivre.com")
                .password("123456")
                .build();

        User user2 = User.builder()
                .id(2L)
                .name("vendedor1@mercadolivre.com")
                .password("123456")
                .build();

        Seller seller = Seller.builder()
                .id(1L)
                .user(user2)
                .build();

        Operator operator = Operator.builder()
                .id(1L)
                .user(user1)
                .build();

        Warehouse warehouse = Warehouse.builder()
                .id(1L)
                .name("CAJAMAR01")
                .address("Avenida Doutor Antonio Joao Abdalla, 3333")
                .acceptFresh(true)
                .build();

        WarehouseOperator warehouseOperator = WarehouseOperator.builder()
                .id(new WarehouseOperatorKey(warehouse.getId(), operator.getId()))
                .warehouse(warehouse)
                .operator(operator)
                .build();

        ProductCategory category = ProductCategory.builder()
                .id(1L)
                .name(ProductCategoryEnum.FS)
                .build();

        WarehouseSection warehouseSection = WarehouseSection.builder()
                .id(1L)
                .name("CAJAMAR01FRESH")
                .capacity(100000)
                .currentAvailability(100000)
                .temperature(10.0)
                .warehouse(warehouse)
                .productCategory(category)
                .build();

        Product freshProduct1 = Product.builder()
                .id(1L)
                .name("Tomate Caqui unidade")
                .description("Uma unidade de tomate Caqui")
                .minTemperature(8.0)
                .maxTemperature(13.0)
                .price(2.39)
                .seller(seller)
                .productCategory(category)
                .build();

        Product freshProduct2 = Product.builder()
                .id(2L)
                .name("Banana nanica 6/un")
                .description("Um cacho com 6 bananas nanicas")
                .minTemperature(8.0)
                .maxTemperature(13.0)
                .price(3.10)
                .seller(seller)
                .productCategory(category)
                .build();

        Batch batch1 = Batch.builder()
                .number(batchesDTO.get(0).getBatchNumber())
                .quantity(batchesDTO.get(0).getQuantity())
                .currentTemperature(batchesDTO.get(0).getCurrentTemperature())
                .manufacturingDate(batchesDTO.get(0).getManufacturingDate())
                .manufacturingTime(batchesDTO.get(0).getManufacturingTime())
                .dueDate(batchesDTO.get(0).getDueDate())
                .product(freshProduct1)
                .warehouseSection(warehouseSection)
                .build();

        Batch batch2 = Batch.builder()
                .number(batchesDTO.get(1).getBatchNumber())
                .quantity(batchesDTO.get(1).getQuantity())
                .currentTemperature(batchesDTO.get(1).getCurrentTemperature())
                .manufacturingDate(batchesDTO.get(1).getManufacturingDate())
                .manufacturingTime(batchesDTO.get(1).getManufacturingTime())
                .dueDate(batchesDTO.get(1).getDueDate())
                .product(freshProduct2)
                .warehouseSection(warehouseSection)
                .build();

        InboundOrder inboundOrder = InboundOrder.builder()
                .dateTime(LocalDateTime.now())
                .operator(operator)
                .batch(new HashSet<Batch>(Arrays.asList(batch1, batch2)))
                .build();

        seller.setProducts(new HashSet<Product>(Arrays.asList(freshProduct1, freshProduct2)));

        List<WarehouseOperator> warehouseOperators = new ArrayList<WarehouseOperator>();
        warehouseOperators.add(warehouseOperator);

        operator.setWarehouseOperators(warehouseOperators);
        warehouse.setWarehouseOperators(warehouseOperators);

        warehouse.setWarehouseSections(new ArrayList<>(Arrays.asList(warehouseSection)));

        BDDMockito.doReturn(Optional.of(freshProduct1)).when(productRepository).findById(freshProduct1.getId());
        BDDMockito.doReturn(Optional.of(freshProduct2)).when(productRepository).findById(freshProduct2.getId());
        BDDMockito.doThrow(new NotFoundException("Product not found")).when(productRepository).findById(not(or(ArgumentMatchers.eq(freshProduct1.getId()),ArgumentMatchers.eq(freshProduct2.getId()))));

        BDDMockito.when(productRepository.getOne(freshProduct1.getId())).thenReturn(freshProduct1);
        BDDMockito.when(productRepository.getOne(freshProduct2.getId())).thenReturn(freshProduct2);
        BDDMockito.doThrow(new NotFoundException("Product not found")).when(productRepository).getOne(not(or(ArgumentMatchers.eq(freshProduct1.getId()),ArgumentMatchers.eq(freshProduct2.getId()))));

        BDDMockito.when(warehouseSectionRepository.findById(warehouseSection.getId())).thenReturn(Optional.of(warehouseSection));
        BDDMockito.when(warehouseOperatorRepository.findByWarehouseCode(warehouseOperator.getWarehouse().getId())).thenReturn(Arrays.asList(warehouseOperator));

        BDDMockito.when(inboundOrderRepository.save(inboundOrder))
                .thenAnswer(invocation -> {
                    Object[] args = invocation.getArguments();
                    ((InboundOrder)args[0]).setId(1L);
                    return args[0];
        });

        BDDMockito.when(batchRepository.saveAll(new ArrayList<Batch>(Arrays.asList(batch1, batch2))))
                .thenAnswer(invocation -> {
                    Object[] args = invocation.getArguments();
                    ((List<Batch>)args[0]).get(0).setId(1L);
                    ((List<Batch>)args[0]).get(1).setId(2L);
                    return args[0];
        });

    }

    @Test
    public void returnBatchResponseListWhenValidInboundOrder() {

        BatchResponseListDTO batchResponseListDTO = this.inboundOrderService.createInboundOrder(this.inboundOrderRequestDTO);

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
                                "MELI0001",
                                1,
                                10.0,
                                20000,
                                this.batchesDTO.get(0).getManufacturingDate(),
                                this.batchesDTO.get(0).getManufacturingTime(),
                                this.batchesDTO.get(0).getDueDate()),
                        Tuple.tuple(
                                "MELI0002",
                                2,
                                2.0,
                                20000,
                                this.batchesDTO.get(1).getManufacturingDate(),
                                this.batchesDTO.get(1).getManufacturingTime(),
                                this.batchesDTO.get(1).getDueDate()));

    }

    @Test
    public void returnExceptionWhenInvalidProduct() {
        this.inboundOrderRequestDTO.getInboundOrder().getBatchStock().get(0).setProductId(3);
        Assertions.assertThatThrownBy(() -> {this.inboundOrderService.createInboundOrder(this.inboundOrderRequestDTO);})
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Not Found Exception. Product not found");

    }

}
