package com.mercadolibre.group8_bootcamp_finalproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PurchaseOrderItem implements Comparable<PurchaseOrderItem> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total_price" , nullable = false, precision = 8, scale = 2)
    private Double totalPrice;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private PurchaseOrder purchaseOrder;

    @Override
    public int compareTo(PurchaseOrderItem o) {
        return this.getQuantity().compareTo(o.getQuantity());
    }
}
