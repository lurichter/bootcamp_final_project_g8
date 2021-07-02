package com.mercadolibre.group8_bootcamp_finalproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batch_id")
    private Long id;

    @Column(name = "batch_number", nullable = false, length = 45)
    private String number;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "current_temperature", nullable = false, precision = 4, scale = 2)
    private Double current_temperature;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "manufacturing_date", nullable = false)
    private LocalDate manufacturing_date = LocalDate.now();

    @Column(name = "manufacturing_time", nullable = false)
    private LocalTime manufacturing_time = LocalTime.now();

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "due_date", nullable = false)
    private LocalDate due_date;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "inbound_order_id")
    private InboundOrder inboundOrder;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private WarehouseSection warehouseSection;

    @OneToMany(mappedBy = "purchaseOrder")
    private Set<PurchaseOrderItem> purchaseOrderItems;
}