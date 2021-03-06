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
    private List<WarehouseSection> warehouseSections;

    @OneToMany(mappedBy = "warehouse")
    private List<WarehouseOperator> warehouseOperators;
}
