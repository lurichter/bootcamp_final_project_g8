package com.mercadolibre.group8_bootcamp_finalproject.model;

import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
