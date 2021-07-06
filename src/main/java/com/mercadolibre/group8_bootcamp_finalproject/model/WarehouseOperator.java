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
public class WarehouseOperator {
    @EmbeddedId
    private WarehouseOperatorKey id;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("warehouse_id")
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("operator_id")
    @JoinColumn(name = "operator_id")
    private Operator operator;
}
