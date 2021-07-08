package com.mercadolibre.group8_bootcamp_finalproject.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name", nullable = false, unique = true, length = 45)
    private String name;

    @Column(name = "product_description", nullable = false)
    private String description;

    @Column(name = "minimum_temperature", nullable = false, precision = 4, scale = 2)
    private Double minTemperature;

    @Column(name = "maximum_temperature", nullable = false, precision = 4, scale = 2)
    private Double maxTemperature;

    @Column(name = "price", nullable = false, precision = 8, scale = 2)
    private Double price;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToMany(mappedBy = "product", fetch=FetchType.LAZY)
    private Set<Batch> batch;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;
}
