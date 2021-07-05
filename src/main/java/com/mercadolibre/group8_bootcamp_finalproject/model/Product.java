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
    private Double min_temperature;

    @Column(name = "maximum_temperature", nullable = false, precision = 4, scale = 2)
    private Double max_temperature;

    @Column(name = "price", nullable = false, precision = 8, scale = 2)
    private Double price;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @OneToMany(mappedBy = "product")
    private Set<Batch> batch;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;
}
