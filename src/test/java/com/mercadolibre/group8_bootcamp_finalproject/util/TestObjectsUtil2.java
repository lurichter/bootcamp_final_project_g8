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
import java.util.List;

@Getter
public class TestObjectsUtil2 {

    private final List<Users> users = new ArrayList<>();
    private final List<Operator> operators = new ArrayList<>();
    private final List<Seller> sellers = new ArrayList<>();
    private final List<Warehouse> warehouses = new ArrayList<>();
    private final List<WarehouseOperator> warehouseOperators = new ArrayList<>();
    private final ProductCategory freshProductCategory;
    private final ProductCategory frozenProductCategory;
    private final ProductCategory chilledProductCategory;
    private final List<WarehouseSection> freshWarehouseSections = new ArrayList<>();
    private final List<WarehouseSection> frozenWarehouseSections = new ArrayList<>();
    private final List<WarehouseSection> chilledWarehouseSections = new ArrayList<>();
    private final List<Product> freshProducts = new ArrayList<>();
    private final List<Product> frozenProducts = new ArrayList<>();
    private final List<Product> chilledProducts = new ArrayList<>();
    private final List<Batch> freshBatches = new ArrayList<>();
    private final List<Batch> frozenBatches = new ArrayList<>();
    private final List<Batch> chilledBatches = new ArrayList<>();

    public TestObjectsUtil2() {

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
                .dueDate(LocalDate.now().plusDays(1))
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
                .dueDate(LocalDate.now().plusDays(10))
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
    }
}