package com.mercadolibre.group8_bootcamp_finalproject.util;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.BatchDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.InboundOrderDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.WarehouseSectionDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.request.InboundOrderRequestDTO;
import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.WarehouseTotalProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.*;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.OrderStatusEnum;
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

    private List<Users> users = new ArrayList<Users>();
    private List<Operator> operators = new ArrayList<Operator>();
    private List<Seller> sellers = new ArrayList<Seller>();
    private List<Warehouse> warehouses = new ArrayList<Warehouse>();
    private List<WarehouseOperator> warehouseOperators = new ArrayList<WarehouseOperator>();
    private ProductCategory freshProductCategory;
    private ProductCategory frozenProductCategory;
    private List<WarehouseSection> freshWarehouseSections = new ArrayList<WarehouseSection>();
    private List<WarehouseSection> frozenWarehouseSections = new ArrayList<WarehouseSection>();
    private List<Product> freshProducts = new ArrayList<Product>();
    private List<Batch> freshBatches = new ArrayList<Batch>();
    private List<InboundOrder> freshInboundOrders = new ArrayList<InboundOrder>();
    private List<PurchaseOrder> freshPurchaseOrders = new ArrayList<PurchaseOrder>();
    private List<PurchaseOrderItem> freshPurchaseOrderItems = new ArrayList<PurchaseOrderItem>();
    private List<WarehouseSectionDTO> freshWarehouseSectionDTOS = new ArrayList<WarehouseSectionDTO>();
    private List<WarehouseSectionDTO> frozenWarehouseSectionDTOS = new ArrayList<WarehouseSectionDTO>();
    private List<BatchDTO> freshBatchDTOS = new ArrayList<BatchDTO>();
    private List<InboundOrderDTO> freshInboundOrderDTOS = new ArrayList<InboundOrderDTO>();
    private List<InboundOrderRequestDTO> freshInboundOrderRequestDTOS = new ArrayList<InboundOrderRequestDTO>();

    // added by victor
    private List<WarehouseTotalProductDTO> warehouseTotalProductDTOS = new ArrayList<>();

    public TestObjectsUtil() {

        Users userOperator1 = Users.builder()
                .id(1L)
                .name("operador1@mercadolivre.com")
                .password("123456")
                .build();

        Users userOperator2 = Users.builder()
                .id(2L)
                .name("operador2@mercadolivre.com")
                .password("123456")
                .build();

        Users userSeller = Users.builder()
                .id(3L)
                .name("vendedor1@gmail.com")
                .password("123456")
                .build();

        Users userBuyer = Users.builder()
                .id(4L)
                .name("comprador1@gmail.com")
                .password("123456")
                .build();

        Operator operator1 = Operator.builder()
                .id(1L)
                .user(userOperator1)
                .build();

        Operator operator2 = Operator.builder()
                .id(2L)
                .user(userOperator2)
                .build();

        Seller seller = Seller.builder()
                .id(1L)
                .user(userSeller)
                .build();

        Buyer buyer = Buyer.builder()
                .id(1L)
                .user(userBuyer)
                .build();

        Warehouse warehouse = Warehouse.builder()
                .id(1L)
                .name("CAJAMAR01")
                .address("Avenida Doutor Antonio Joao Abdalla, 3333")
                .acceptFresh(true)
                .build();

        WarehouseOperator warehouseOperator = WarehouseOperator.builder()
                .id(new WarehouseOperatorKey(warehouse.getId(), operator1.getId()))
                .warehouse(warehouse)
                .operator(operator1)
                .build();

        List<WarehouseOperator> warehouseOperators = new ArrayList<WarehouseOperator>();
        warehouseOperators.add(warehouseOperator);
        operator1.setWarehouseOperators(warehouseOperators);
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

        warehouse.setWarehouseSections(new ArrayList<>(Arrays.asList(freshWarehouseSection, frozenWarehouseSection)));

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

        seller.setProducts(new ArrayList<>(Arrays.asList(freshProduct1, freshProduct2)));

        Batch freshBatch1 = Batch.builder()
                .id(1L)
                .number("MELI0001")
                .quantity(20000)
                .currentTemperature(10.0)
                .manufacturingDate(LocalDate.now().minusDays(7))
                .manufacturingTime(LocalTime.now())
                .dueDate(LocalDate.now().plusDays(14))
                .product(freshProduct1)
                .warehouseSection(freshWarehouseSection)
                .purchaseOrderItems(new ArrayList<PurchaseOrderItem>())
                .build();

        Batch freshBatch2 = Batch.builder()
                .id(2L)
                .number("MELI0002")
                .quantity(20000)
                .currentTemperature(10.0)
                .manufacturingDate(LocalDate.now().minusDays(7))
                .manufacturingTime(LocalTime.now())
                .dueDate(LocalDate.now().plusDays(14))
                .product(freshProduct2)
                .warehouseSection(freshWarehouseSection)
                .purchaseOrderItems(new ArrayList<PurchaseOrderItem>())
                .build();

        Batch freshBatch3 = Batch.builder()
                .id(3L)
                .number("MELI0003")
                .quantity(20000)
                .currentTemperature(10.0)
                .manufacturingDate(LocalDate.now().minusDays(7))
                .manufacturingTime(LocalTime.now())
                .dueDate(LocalDate.now().plusDays(14))
                .product(freshProduct1)
                .warehouseSection(freshWarehouseSection)
                .purchaseOrderItems(new ArrayList<PurchaseOrderItem>())
                .build();

        InboundOrder freshInboundOrder = InboundOrder.builder()
                .id(1L)
                .dateTime(LocalDateTime.now())
                .operator(operator1)
                .batches(new ArrayList<>(Arrays.asList(freshBatch1, freshBatch2)))
                .build();

        PurchaseOrder freshPurchaseOrder = PurchaseOrder.builder()
                .id(1L)
                .orderStatusEnum(OrderStatusEnum.OPEN)
                .buyer(buyer)
                .build();

        PurchaseOrderItem freshPurchaseOrderItem = PurchaseOrderItem.builder()
                .id(1L)
                .quantity(10)
                .totalPrice(23.90)
                .batch(freshBatch3)
                .purchaseOrder(freshPurchaseOrder)
                .build();

        freshBatch3.getPurchaseOrderItems().add(freshPurchaseOrderItem);

        WarehouseSectionDTO freshWarehouseSectionDTO = WarehouseSectionDTO.builder()
                .sectionCode(freshWarehouseSection.getId())
                .warehouseCode(warehouse.getId())
                .build();

        WarehouseSectionDTO frozenWarehouseSectionDTO = WarehouseSectionDTO.builder()
                .sectionCode(frozenWarehouseSection.getId())
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
                .batchNumber(freshBatch2.getNumber())
                .productId(freshBatch2.getProduct().getId())
                .currentTemperature(freshBatch2.getCurrentTemperature())
                .quantity(freshBatch2.getQuantity())
                .manufacturingDate(freshBatch2.getManufacturingDate())
                .manufacturingTime(freshBatch2.getManufacturingTime())
                .dueDate(freshBatch2.getDueDate())
                .build();

        BatchDTO freshBatchDTO3 = BatchDTO.builder()
                .batchNumber(freshBatch3.getNumber())
                .productId(freshBatch3.getProduct().getId())
                .currentTemperature(freshBatch3.getCurrentTemperature())
                .quantity(freshBatch3.getQuantity())
                .manufacturingDate(freshBatch3.getManufacturingDate())
                .manufacturingTime(freshBatch3.getManufacturingTime())
                .dueDate(freshBatch3.getDueDate())
                .build();

        InboundOrderDTO freshInboundOrderDTO = InboundOrderDTO.builder()
                .section(freshWarehouseSectionDTO)
                .batchStock(new ArrayList<>(Arrays.asList(freshBatchDTO1, freshBatchDTO2)))
                .build();

        InboundOrderRequestDTO freshInboundOrderRequestDTO = InboundOrderRequestDTO.builder()
                .inboundOrder(freshInboundOrderDTO)
                .build();

        this.users.add(userOperator1);
        this.users.add(userOperator2);
        this.users.add(userSeller);
        this.operators.add(operator1);
        this.operators.add(operator2);
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
        this.freshBatches.add(freshBatch3);
        this.freshInboundOrders.add(freshInboundOrder);
        this.freshPurchaseOrders.add(freshPurchaseOrder);
        this.freshPurchaseOrderItems.add(freshPurchaseOrderItem);
        this.freshWarehouseSectionDTOS.add(freshWarehouseSectionDTO);
        this.frozenWarehouseSectionDTOS.add(frozenWarehouseSectionDTO);
        this.freshBatchDTOS.add(freshBatchDTO1);
        this.freshBatchDTOS.add(freshBatchDTO2);
        this.freshBatchDTOS.add(freshBatchDTO3);
        this.freshInboundOrderDTOS.add(freshInboundOrderDTO);
        this.freshInboundOrderRequestDTOS.add(freshInboundOrderRequestDTO);

        this.warehouseTotalProductDTOS.add(WarehouseTotalProductDTO.builder().warehouseCode(this.warehouses.get(0).getId()).totalQuantity(22L).build());

    }



}