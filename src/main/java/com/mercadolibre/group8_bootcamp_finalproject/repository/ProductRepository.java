package com.mercadolibre.group8_bootcamp_finalproject.repository;

import com.mercadolibre.group8_bootcamp_finalproject.dtos.response.SellerProductDTO;
import com.mercadolibre.group8_bootcamp_finalproject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p FROM Product p WHERE p.productCategory.id = :categoryId")
    List<Product> findAllByProductCategory(@Param("categoryId")Long categoryId);

    @Query(value = "SELECT p.id FROM Product p JOIN Batch b ON p.id = b.product.id JOIN PurchaseOrderItem po ON b.id = po.batch.id WHERE po.purchaseOrder.id = :purchaseOrderId")
    Set<Long> finAllByPurchaseOrder(@Param("purchaseOrderId") Long purchaseOrderId);

    @Query("SELECT " +
            " new com.mercadolibre.group8_bootcamp_finalproject.dtos.response.SellerProductDTO(p.id, p.name, p.description, p.productCategory.name, p.price) " +
            " FROM Product p " +
            " WHERE p.seller.id = :sellerId")
    List<SellerProductDTO> findAllProductsFromSeller(@Param("sellerId") Long sellerId);
}
