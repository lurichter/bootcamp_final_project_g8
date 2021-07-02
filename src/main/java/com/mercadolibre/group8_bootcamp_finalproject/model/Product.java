package com.mercadolibre.group8_bootcamp_finalproject.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreshProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_description", nullable = false)
    private String description;

    @Column(name = "minimum_temperature", nullable = false, precision = 4, scale = 2)
    private Double min_temperature;

    @Column(name = "maximum_temperature", nullable = false, precision = 4, scale = 2)
    private Double max_temperature;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

}
