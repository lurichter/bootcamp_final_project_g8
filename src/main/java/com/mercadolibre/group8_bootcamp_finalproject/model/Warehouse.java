package com.mercadolibre.group8_bootcamp_finalproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private Boolean acceptFresh;

    @OneToMany(mappedBy = "warehouse")
    private Set<WarehouseSection> warehouseSections;

    @OneToMany(mappedBy = "warehouse")
    private Set<WarehouseOperator> warehouseOperators;
}
