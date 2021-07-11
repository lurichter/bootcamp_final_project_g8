package com.mercadolibre.group8_bootcamp_finalproject.util;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.BatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.WarehouseSectionDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.BatchStockDueDateDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.*;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class TestObjectsUtil {

    private List<Users> users = new ArrayList<Users>();
    private List<Operator> operators = new ArrayList<Operator>();
    private List<Seller> sellers = new ArrayList<Seller>();
    private List<Warehouse> warehouses = new ArrayList<Warehouse>();;
    private List<WarehouseOperator> warehouseOperators = new ArrayList<WarehouseOperator>();
    private ProductCategory freshProductCategory;
    private ProductCategory frozenProductCategory;
    private ProductCategory chilledProductCategory;
    private List<WarehouseSection> freshWarehouseSections = new ArrayList<WarehouseSection>();
    private List<WarehouseSection> frozenWarehouseSections = new ArrayList<WarehouseSection>();
    private List<WarehouseSection> chilledWarehouseSections = new ArrayList<WarehouseSection>();
    private List<Product> freshProducts = new ArrayList<Product>();
    private List<Product> frozenProducts = new ArrayList<Product>();
    private List<Product> chilledProducts = new ArrayList<Product>();
    private List<Batch> freshBatches = new ArrayList<Batch>();
    private List<Batch> frozenBatches = new ArrayList<Batch>();
    private List<Batch> chilledBatches = new ArrayList<Batch>();
    private List<InboundOrder> freshInboundOrders = new ArrayList<InboundOrder>();
    private List<InboundOrder> frozenInboundOrders = new ArrayList<InboundOrder>();
    private List<InboundOrder> chilledInboundOrders = new ArrayList<InboundOrder>();
    private List<WarehouseSectionDTO> freshWarehouseSectionDTOS = new ArrayList<WarehouseSectionDTO>();
    private List<WarehouseSectionDTO> frozenWarehouseSectionDTOS = new ArrayList<WarehouseSectionDTO>();
    private List<WarehouseSectionDTO> chilledWarehouseSectionDTOS = new ArrayList<WarehouseSectionDTO>();
    private List<BatchDTO> freshBatchDTOS = new ArrayList<BatchDTO>();
    private List<BatchDTO> frozenBatchDTOS = new ArrayList<BatchDTO>();
    private List<BatchDTO> chilledBatchDTOS = new ArrayList<BatchDTO>();
    private List<InboundOrderDTO> freshInboundOrderDTOS = new ArrayList<InboundOrderDTO>();
    private List<InboundOrderDTO> frozenInboundOrderDTOS = new ArrayList<InboundOrderDTO>();
    private List<InboundOrderDTO> chilledInboundOrderDTOS = new ArrayList<InboundOrderDTO>();
    private List<InboundOrderRequestDTO> freshInboundOrderRequestDTOS = new ArrayList<InboundOrderRequestDTO>();
    private List<InboundOrderRequestDTO> frozenInboundOrderRequestDTOS = new ArrayList<InboundOrderRequestDTO>();
    private List<InboundOrderRequestDTO> chilledInboundOrderRequestDTOS = new ArrayList<InboundOrderRequestDTO>();
    private List<BatchStockDueDateDTO> batchStockDueDate1 = new ArrayList<>();
    private List<BatchStockDueDateDTO> batchStockDueDate2 = new ArrayList<>();

    public TestObjectsUtil() {

        Users user1 = Users.builder()
                .id(1L)
                .name("operador1@mercadolivre.com")
                .password("123456")
                .build();

        Users user2 = Users.builder()
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

        List<WarehouseOperator> warehouseOperators = new ArrayList<>();
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

        ProductCategory chilledCategory = ProductCategory.builder()
                .id(3L)
                .name(ProductCategoryEnum.RF)
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

        WarehouseSection chilledWarehouseSection = WarehouseSection.builder()
                .id(3L)
                .name("CAJAMAR01CHILLED")
                .capacity(100000)
                .currentAvailability(100000)
                .temperature(5.0)
                .warehouse(warehouse)
                .productCategory(chilledCategory)
                .build();

        warehouse.setWarehouseSections(new ArrayList<>(Arrays.asList(freshWarehouseSection, freshWarehouseSection, chilledWarehouseSection)));

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

        Product frozenProduct1 = Product.builder()
                .id(3L)
                .name("Torta congelada")
                .description("Torta de frango Catupiry congelada 700g")
                .minTemperature(-3.0)
                .maxTemperature(4.0)
                .price(22.4)
                .seller(seller)
                .productCategory(frozenCategory)
                .build();

        Product frozenProduct2 = Product.builder()
                .id(4L)
                .name("Açaí Frooty tradicional")
                .description("Açaí Frooty 1.5L")
                .minTemperature(-3.0)
                .maxTemperature(0.0)
                .price(24.99)
                .seller(seller)
                .productCategory(frozenCategory)
                .build();

        Product chilledProduct1 = Product.builder()
                .id(5L)
                .name("Um maço de alface crespa higienizado")
                .description("Torta de franAlface Crespa Bem Querer 140g")
                .minTemperature(0.0)
                .maxTemperature(3.0)
                .price(3.98)
                .seller(seller)
                .productCategory(chilledCategory)
                .build();

        Product chilledProduct2 = Product.builder()
                .id(6L)
                .name("Queijo minas frescal")
                .description("Queijo minas frescal 500g")
                .minTemperature(1.0)
                .maxTemperature(7.0)
                .price(18.9)
                .seller(seller)
                .productCategory(chilledCategory)
                .build();

        seller.setProducts((Arrays.asList(freshProduct1, freshProduct2, frozenProduct1, frozenProduct2, chilledProduct1, chilledProduct2)));

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

        Batch frozenBatch1 = Batch.builder()
                .number("MELI0003")
                .quantity(20000)
                .currentTemperature(-5.0)
                .manufacturingDate(LocalDate.now().minusDays(7))
                .manufacturingTime(LocalTime.now())
                .dueDate(LocalDate.now().plusDays(60))
                .product(frozenProduct1)
                .warehouseSection(frozenWarehouseSection)
                .build();

        Batch frozenBatch2 = Batch.builder()
                .number("MELI0004")
                .quantity(20000)
                .currentTemperature(-7.0)
                .manufacturingDate(LocalDate.now().minusDays(7))
                .manufacturingTime(LocalTime.now())
                .dueDate(LocalDate.now().plusDays(30))
                .product(frozenProduct2)
                .warehouseSection(frozenWarehouseSection)
                .build();

        Batch chilledBatch1 = Batch.builder()
                .number("MELI0005")
                .quantity(20000)
                .currentTemperature(3.0)
                .manufacturingDate(LocalDate.now().minusDays(7))
                .manufacturingTime(LocalTime.now())
                .dueDate(LocalDate.now().plusDays(20))
                .product(frozenProduct1)
                .warehouseSection(frozenWarehouseSection)
                .build();

        Batch chilledBatch2 = Batch.builder()
                .number("MELI0006")
                .quantity(20000)
                .currentTemperature(5.0)
                .manufacturingDate(LocalDate.now().minusDays(7))
                .manufacturingTime(LocalTime.now())
                .dueDate(LocalDate.now().plusDays(25))
                .product(frozenProduct2)
                .warehouseSection(frozenWarehouseSection)
                .build();

        InboundOrder freshInboundOrder = InboundOrder.builder()
                .dateTime(LocalDateTime.now())
                .operator(operator)
                .batches(Arrays.asList(freshBatch1, freshBatch2))
                .build();

        InboundOrder frozenInboundOrder = InboundOrder.builder()
                .dateTime(LocalDateTime.now())
                .operator(operator)
                .batches(Arrays.asList(frozenBatch1, frozenBatch2))
                .build();

        InboundOrder chilledInboundOrder = InboundOrder.builder()
                .dateTime(LocalDateTime.now())
                .operator(operator)
                .batches(Arrays.asList(chilledBatch1, chilledBatch2))
                .build();

        WarehouseSectionDTO freshWarehouseSectionDTO = WarehouseSectionDTO.builder()
                .sectionCode(freshWarehouseSection.getId())
                .warehouseCode(warehouse.getId())
                .build();

        WarehouseSectionDTO frozenWarehouseSectionDTO = WarehouseSectionDTO.builder()
                .sectionCode(freshWarehouseSection.getId())
                .warehouseCode(warehouse.getId())
                .build();

        WarehouseSectionDTO chilledWarehouseSectionDTO = WarehouseSectionDTO.builder()
                .sectionCode(chilledWarehouseSection.getId())
                .warehouseCode(warehouse.getId())
                .build();

        BatchDTO freshBatchDTO1 = BatchDTO.builder()
                .batchNumber(freshBatch1.getNumber())
                .productId(freshBatch1.getProduct().getId())
                .currentTemperature(freshBatch1.getCurrentTemperature())
                .quantity(freshBatch1.getQuantity())
                .manufacturingDate(freshBatch1.getManufacturingDate())
                .manufacturingTime(freshBatch1.getManufacturingTime())
                .dueDate(freshBatch1.getDueDate())
                .build();

        BatchDTO freshBatchDTO2 = BatchDTO.builder()
                .batchNumber(freshBatch1.getNumber())
                .productId(freshBatch1.getProduct().getId())
                .currentTemperature(freshBatch1.getCurrentTemperature())
                .quantity(freshBatch1.getQuantity())
                .manufacturingDate(freshBatch1.getManufacturingDate())
                .manufacturingTime(freshBatch1.getManufacturingTime())
                .dueDate(freshBatch1.getDueDate())
                .build();

        BatchDTO frozenBatchDTO1 = BatchDTO.builder()
                .batchNumber(frozenBatch1.getNumber())
                .productId(frozenBatch1.getProduct().getId())
                .currentTemperature(frozenBatch1.getCurrentTemperature())
                .quantity(frozenBatch1.getQuantity())
                .manufacturingDate(frozenBatch1.getManufacturingDate())
                .manufacturingTime(frozenBatch1.getManufacturingTime())
                .dueDate(frozenBatch1.getDueDate())
                .build();

        BatchDTO frozenBatchDTO2 = BatchDTO.builder()
                .batchNumber(frozenBatch2.getNumber())
                .productId(frozenBatch2.getProduct().getId())
                .currentTemperature(frozenBatch2.getCurrentTemperature())
                .quantity(frozenBatch2.getQuantity())
                .manufacturingDate(frozenBatch2.getManufacturingDate())
                .manufacturingTime(frozenBatch2.getManufacturingTime())
                .dueDate(frozenBatch2.getDueDate())
                .build();

        BatchDTO chilledBatchDTO1 = BatchDTO.builder()
                .batchNumber(chilledBatch1.getNumber())
                .productId(chilledBatch1.getProduct().getId())
                .currentTemperature(chilledBatch1.getCurrentTemperature())
                .quantity(chilledBatch1.getQuantity())
                .manufacturingDate(chilledBatch1.getManufacturingDate())
                .manufacturingTime(chilledBatch1.getManufacturingTime())
                .dueDate(chilledBatch1.getDueDate())
                .build();

        BatchDTO chilledBatchDTO2 = BatchDTO.builder()
                .batchNumber(chilledBatch2.getNumber())
                .productId(chilledBatch2.getProduct().getId())
                .currentTemperature(chilledBatch2.getCurrentTemperature())
                .quantity(chilledBatch2.getQuantity())
                .manufacturingDate(chilledBatch2.getManufacturingDate())
                .manufacturingTime(chilledBatch2.getManufacturingTime())
                .dueDate(chilledBatch2.getDueDate())
                .build();

        InboundOrderDTO freshInboundOrderDTO = InboundOrderDTO.builder()
                .section(freshWarehouseSectionDTO)
                .batchStock(new ArrayList<>(Arrays.asList(freshBatchDTO1, freshBatchDTO2)))
                .build();

        InboundOrderDTO frozenInboundOrderDTO = InboundOrderDTO.builder()
                .section(frozenWarehouseSectionDTO)
                .batchStock(new ArrayList<>(Arrays.asList(frozenBatchDTO1, frozenBatchDTO2)))
                .build();

        InboundOrderDTO chilledInboundOrderDTO = InboundOrderDTO.builder()
                .section(chilledWarehouseSectionDTO)
                .batchStock(new ArrayList<>(Arrays.asList(chilledBatchDTO1, chilledBatchDTO2)))
                .build();

        InboundOrderRequestDTO freshInboundOrderRequestDTO = InboundOrderRequestDTO.builder()
                .inboundOrder(freshInboundOrderDTO)
                .build();

        InboundOrderRequestDTO frozenInboundOrderRequestDTO = InboundOrderRequestDTO.builder()
                .inboundOrder(frozenInboundOrderDTO)
                .build();

        InboundOrderRequestDTO chilledInboundOrderRequestDTO = InboundOrderRequestDTO.builder()
                .inboundOrder(chilledInboundOrderDTO)
                .build();

//        BatchStockDueDateDTO batchStockDueDateDTO1 = BatchStockDueDateDTO.builder()
//                .quantity()
//                .dueDate()
//                .productId()
//                .sectionId()
//                .batchNumber()
//                .productCategory()
//                .build();

        this.users.add(user1);
        this.users.add(user2);
        this.operators.add(operator);
        this.sellers.add(seller);
        this.warehouses.add(warehouse);
        this.warehouseOperators.add(warehouseOperator);
        this.freshProductCategory = freshCategory;
        this.frozenProductCategory = frozenCategory;
        this.chilledProductCategory = chilledCategory;
        this.freshWarehouseSections.add(freshWarehouseSection);
        this.frozenWarehouseSections.add(frozenWarehouseSection);
        this.chilledWarehouseSections.add(chilledWarehouseSection);
        this.freshProducts.add(freshProduct1);
        this.freshProducts.add(freshProduct2);
        this.frozenProducts.add(frozenProduct1);
        this.frozenProducts.add(frozenProduct2);
        this.chilledProducts.add(chilledProduct1);
        this.chilledProducts.add(chilledProduct2);
        this.freshBatches.add(freshBatch1);
        this.freshBatches.add(freshBatch2);
        this.frozenBatches.add(frozenBatch1);
        this.frozenBatches.add(frozenBatch1);
        this.chilledBatches.add(chilledBatch1);
        this.chilledBatches.add(chilledBatch2);
        this.freshInboundOrders.add(freshInboundOrder);
        this.frozenInboundOrders.add(frozenInboundOrder);
        this.chilledInboundOrders.add(chilledInboundOrder);
        this.freshWarehouseSectionDTOS.add(freshWarehouseSectionDTO);
        this.frozenWarehouseSectionDTOS.add(frozenWarehouseSectionDTO);
        this.chilledWarehouseSectionDTOS.add(chilledWarehouseSectionDTO);
        this.freshBatchDTOS.add(freshBatchDTO1);
        this.freshBatchDTOS.add(freshBatchDTO2);
        this.frozenBatchDTOS.add(frozenBatchDTO1);
        this.frozenBatchDTOS.add(frozenBatchDTO2);
        this.chilledBatchDTOS.add(chilledBatchDTO1);
        this.chilledBatchDTOS.add(chilledBatchDTO2);
        this.freshInboundOrderDTOS.add(freshInboundOrderDTO);
        this.frozenInboundOrderDTOS.add(frozenInboundOrderDTO);
        this.chilledInboundOrderDTOS.add(chilledInboundOrderDTO);
        this.freshInboundOrderRequestDTOS.add(freshInboundOrderRequestDTO);
        this.frozenInboundOrderRequestDTOS.add(frozenInboundOrderRequestDTO);
        this.chilledInboundOrderRequestDTOS.add(chilledInboundOrderRequestDTO);

    }
}