package com.mercadolibre.group8_bootcamp_finalproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseOperatorKey implements Serializable {
    @Column(name = "warehouse_id")
    private Long warehouse_id;

    @Column(name = "operator_id")
    private Long operator_id;
}
