package com.mercadolibre.group8_bootcamp_finalproject.unit;

import com.mercadolibre.group8_bootcamp_finalproject.model.Product;
import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import com.mercadolibre.group8_bootcamp_finalproject.repository.*;
import com.mercadolibre.group8_bootcamp_finalproject.service.impl.PurchaseOrderServiceImpl;
import com.mercadolibre.group8_bootcamp_finalproject.util.MockitoExtension;
import com.mercadolibre.group8_bootcamp_finalproject.util.TestObjectsUtil;
import com.mercadolibre.group8_bootcamp_finalproject.util.TestObjectsUtilUpdated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PurchaseOrderUnitTest {

    private TestObjectsUtilUpdated testObjectsUtil;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private PurchaseOrderRepository purchaseOrderRepository;

    @Mock
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Mock
    private BatchRepository batchRepository;

    @Mock
    private BuyerRepository buyerRepository;

    @InjectMocks
    private PurchaseOrderServiceImpl purchaseOrderServicel;

    @BeforeEach
    void setup(){
        this.testObjectsUtil = new TestObjectsUtilUpdated();
        List<Product> allProducts = testObjectsUtil.getProducts();
        List<Product> freshProducts = testObjectsUtil.getFreshProducts();
        List<Product> frozenProducts = testObjectsUtil.getFrozenProducts();


    }
}
