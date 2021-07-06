package com.mercadolibre.group8_bootcamp_finalproject.model;

import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
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
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name", nullable = false)
    private ProductCategoryEnum name;

    @OneToMany(mappedBy = "productCategory")
    private Set<Product> products;

    @OneToMany(mappedBy = "productCategory")
    private Set<WarehouseSection> warehouseSections;
}
