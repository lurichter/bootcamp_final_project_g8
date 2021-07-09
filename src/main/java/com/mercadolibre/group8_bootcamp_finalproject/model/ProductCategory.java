package com.mercadolibre.group8_bootcamp_finalproject.model;

import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "category_name", nullable = false)
    private ProductCategoryEnum name;

    @OneToMany(mappedBy = "productCategory")
    private List<Product> products;

    @OneToMany(mappedBy = "productCategory")
    private List<WarehouseSection> warehouseSections;
}
