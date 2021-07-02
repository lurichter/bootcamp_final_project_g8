package com.mercadolibre.group8_bootcamp_finalproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseOperator {
    @EmbeddedId
    private WarehouseOperatorKey id;

    @ManyToOne
    @MapsId("warehouse_id")
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @ManyToOne
    @MapsId("operator_id")
    @JoinColumn(name = "operator_id")
    private Operator operator;
}
