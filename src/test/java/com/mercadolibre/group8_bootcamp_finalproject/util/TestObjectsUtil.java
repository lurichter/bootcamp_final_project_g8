package com.mercadolibre.group8_bootcamp_finalproject.util;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.BatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.WarehouseSectionDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.*;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Getter
public class TestObjectsUtil {

    private List<User> users = new ArrayList<User>();
    private List<Operator> operators = new ArrayList<Operator>();
    private List<Seller> sellers = new ArrayList<Seller>();
    private List<Warehouse> warehouses = new ArrayList<Warehouse>();;
    private List<WarehouseOperator> warehouseOperators = new ArrayList<WarehouseOperator>();
    private ProductCategory freshProductCategory;
    private ProductCategory frozenProductCategory;
    private List<WarehouseSection> freshWarehouseSections = new ArrayList<WarehouseSection>();
    private List<WarehouseSection> frozenWarehouseSections = new ArrayList<WarehouseSection>();
    private List<Product> freshProducts = new ArrayList<Product>();
    private List<Batch> freshBatches = new ArrayList<Batch>();
    private List<InboundOrder> freshInboundOrders = new ArrayList<InboundOrder>();
    private List<WarehouseSectionDTO> freshWarehouseSectionDTOS = new ArrayList<WarehouseSectionDTO>();
    private List<BatchDTO> freshBatchDTOS = new ArrayList<BatchDTO>();
    private List<InboundOrderDTO> freshInboundOrderDTOS = new ArrayList<InboundOrderDTO>();
    private List<InboundOrderRequestDTO> freshInboundOrderRequestDTOS = new ArrayList<InboundOrderRequestDTO>();

    public TestObjectsUtil() {

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

        Operator operator = Operator.builder()
                .id(1L)
                .user(user1)
                .build();

        Seller seller = Seller.builder()
                .id(1L)
                .user(user2)
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

        List<WarehouseOperator> warehouseOperators = new ArrayList<WarehouseOperator>();
        warehouseOperators.add(warehouseOperator);
        operator.setWarehouseOperators(warehouseOperators);
        warehouse.setWarehouseOperators(warehouseOperators);

        ProductCategory freshCategory = ProductCategory.builder()
                .id(1L)
                .name(ProductCategoryEnum.FS)
                .build();

        ProductCategory frozenCategory = ProductCategory.builder()
                .id(2L)
                .name(ProductCategoryEnum.FF)
                .build();

        WarehouseSection freshWarehouseSection = WarehouseSection.builder()
                .id(1L)
                .name("CAJAMAR01FRESH")
                .capacity(100000)
                .currentAvailability(100000)
                .temperature(10.0)
                .warehouse(warehouse)
                .productCategory(freshCategory)
                .build();

        WarehouseSection frozenWarehouseSection = WarehouseSection.builder()
                .id(2L)
                .name("CAJAMAR01FROZEN")
                .capacity(100000)
                .currentAvailability(100000)
                .temperature(0.0)
                .warehouse(warehouse)
                .productCategory(frozenCategory)
                .build();

        warehouse.setWarehouseSections(new ArrayList<>(Arrays.asList(freshWarehouseSection, freshWarehouseSection)));

        Product freshProduct1 = Product.builder()
                .id(1L)
                .name("Tomate Caqui unidade")
                .description("Uma unidade de tomate Caqui")
                .minTemperature(8.0)
                .maxTemperature(13.0)
                .price(2.39)
                .seller(seller)
                .productCategory(freshCategory)
                .build();

        Product freshProduct2 = Product.builder()
                .id(2L)
                .name("Banana nanica 6/un")
                .description("Um cacho com 6 bananas nanicas")
                .minTemperature(8.0)
                .maxTemperature(13.0)
                .price(3.10)
                .seller(seller)
                .productCategory(freshCategory)
                .build();

        seller.setProducts(new HashSet<Product>(Arrays.asList(freshProduct1, freshProduct2)));

        Batch freshBatch1 = Batch.builder()
                .number("MELI0001")
                .quantity(20000)
                .currentTemperature(10.0)
                .manufacturingDate(LocalDate.now().minusDays(7))
                .manufacturingTime(LocalTime.now())
                .dueDate(LocalDate.now().plusDays(14))
                .product(freshProduct1)
                .warehouseSection(freshWarehouseSection)
                .build();

        Batch freshBatch2 = Batch.builder()
                .number("MELI0002")
                .quantity(20000)
                .currentTemperature(10.0)
                .manufacturingDate(LocalDate.now().minusDays(7))
                .manufacturingTime(LocalTime.now())
                .dueDate(LocalDate.now().plusDays(14))
                .product(freshProduct2)
                .warehouseSection(freshWarehouseSection)
                .build();

        InboundOrder freshInboundOrder = InboundOrder.builder()
                .dateTime(LocalDateTime.now())
                .operator(operator)
                .batch(new HashSet<Batch>(Arrays.asList(freshBatch1, freshBatch2)))
                .build();

        WarehouseSectionDTO freshWarehouseSectionDTO = WarehouseSectionDTO.builder()
                .sectionCode(freshWarehouseSection.getId().intValue())
                .warehouseCode(warehouse.getId().intValue())
                .build();

        WarehouseSectionDTO frozenWarehouseSectionDTO = WarehouseSectionDTO.builder()
                .sectionCode(freshWarehouseSection.getId().intValue())
                .warehouseCode(warehouse.getId().intValue())
                .build();

        BatchDTO freshBatchDTO1 = BatchDTO.builder()
                .batchNumber(freshBatch1.getNumber())
                .productId(freshBatch1.getProduct().getId().intValue())
                .currentTemperature(freshBatch1.getCurrentTemperature())
                .quantity(freshBatch1.getQuantity())
                .manufacturingDate(freshBatch1.getManufacturingDate())
                .manufacturingTime(freshBatch1.getManufacturingTime())
                .dueDate(freshBatch1.getDueDate())
                .build();

        BatchDTO freshBatchDTO2 = BatchDTO.builder()
                .batchNumber(freshBatch1.getNumber())
                .productId(freshBatch1.getProduct().getId().intValue())
                .currentTemperature(freshBatch1.getCurrentTemperature())
                .quantity(freshBatch1.getQuantity())
                .manufacturingDate(freshBatch1.getManufacturingDate())
                .manufacturingTime(freshBatch1.getManufacturingTime())
                .dueDate(freshBatch1.getDueDate())
                .build();

        InboundOrderDTO freshInboundOrderDTO = InboundOrderDTO.builder()
                .section(freshWarehouseSectionDTO)
                .batchStock(new ArrayList<>(Arrays.asList(freshBatchDTO1, freshBatchDTO2)))
                .build();

        InboundOrderRequestDTO freshInboundOrderRequestDTO = InboundOrderRequestDTO.builder()
                .inboundOrder(freshInboundOrderDTO)
                .build();

        this.users.add(user1);
        this.users.add(user2);
        this.operators.add(operator);
        this.sellers.add(seller);
        this.warehouses.add(warehouse);
        this.warehouseOperators.add(warehouseOperator);
        this.freshProductCategory = freshCategory;
        this.frozenProductCategory = frozenCategory;
        this.freshWarehouseSections.add(freshWarehouseSection);
        this.frozenWarehouseSections.add(frozenWarehouseSection);
        this.freshProducts.add(freshProduct1);
        this.freshProducts.add(freshProduct2);
        this.freshBatches.add(freshBatch1);
        this.freshBatches.add(freshBatch2);
        this.freshInboundOrders.add(freshInboundOrder);
        this.freshWarehouseSectionDTOS.add(freshWarehouseSectionDTO);
        this.freshBatchDTOS.add(freshBatchDTO1);
        this.freshBatchDTOS.add(freshBatchDTO2);
        this.freshInboundOrderDTOS.add(freshInboundOrderDTO);
        this.freshInboundOrderRequestDTOS.add(freshInboundOrderRequestDTO);

    }



}
