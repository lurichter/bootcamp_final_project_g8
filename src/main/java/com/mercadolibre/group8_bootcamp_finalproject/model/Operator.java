package com.mercadolibre.group8_bootcamp_finalproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "operator_id")
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "operator")
    private List<InboundOrder> inboundOrders;

    @OneToMany(mappedBy = "operator")
    private List<WarehouseOperator> warehouseOperators;
}
