package com.mercadolibre.group8_bootcamp_finalproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Batch implements Comparable<Batch>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batch_id")
    private Long id;

    @Column(name = "batch_number", nullable = false, length = 45)
    private String number;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "current_temperature", nullable = false, precision = 4, scale = 2)
    private Double currentTemperature;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "manufacturing_date", nullable = false)
    private LocalDate manufacturingDate = LocalDate.now();

    @Column(name = "manufacturing_time", nullable = false)
    private LocalTime manufacturingTime = LocalTime.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "due_date", nullable = false)
    private LocalDate dueDate;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "inbound_order_id")
    private InboundOrder inboundOrder;


    @ManyToOne
    @JoinColumn(name = "section_id")
    private WarehouseSection warehouseSection;

    @OneToMany(mappedBy = "batch")
    private Set<PurchaseOrderItem> purchaseOrderItems;

    @Override
    public int compareTo(Batch o) {
        return this.getDueDate().compareTo(o.getDueDate());
    }
}
