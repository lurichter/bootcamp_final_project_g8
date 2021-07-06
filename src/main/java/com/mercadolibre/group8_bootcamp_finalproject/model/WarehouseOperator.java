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
public class WarehouseOperator {
    @EmbeddedId
    private WarehouseOperatorKey id;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("warehouse_id")
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @MapsId("operator_id")
    @JoinColumn(name = "operator_id")
    private Operator operator;
}
