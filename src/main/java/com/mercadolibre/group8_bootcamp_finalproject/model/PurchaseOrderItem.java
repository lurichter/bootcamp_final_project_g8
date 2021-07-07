package com.mercadolibre.group8_bootcamp_finalproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price" , nullable = false, precision = 8, scale = 2)
    private Double totalPrice;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private PurchaseOrder purchaseOrder;
}
