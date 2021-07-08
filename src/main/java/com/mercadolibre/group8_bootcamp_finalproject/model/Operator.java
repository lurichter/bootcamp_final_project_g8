package com.mercadolibre.group8_bootcamp_finalproject.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
