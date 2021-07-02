package com.mercadolibre.group8_bootcamp_finalproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "warehouse_id")
    private Long id;

    @Column(name = "warehouse_name", nullable = false, unique = true)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "accept_fresh", nullable = false)
    private Boolean accept_fresh;

    @OneToMany(mappedBy = "warehouse")
    private Set<WarehouseSection> warehouseSections;

    @OneToMany(mappedBy = "warehouse")
    private Set<WarehouseOperator> warehouseOperators;
}
