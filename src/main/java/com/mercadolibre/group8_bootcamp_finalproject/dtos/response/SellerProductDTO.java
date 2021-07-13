package com.mercadolibre.group8_bootcamp_finalproject.dtos.response;

import com.mercadolibre.group8_bootcamp_finalproject.model.enums.ProductCategoryEnum;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
//@AllArgsConstructor
public class SellerProductDTO {
        private Long id;
        private String name;
        private String description;
        private ProductCategoryEnum productCategory;
        private Double price;

        public SellerProductDTO(Long id, String name, String description, ProductCategoryEnum productCategory, Double price) {
                this.id = id;
                this.name = name;
                this.description = description;
                this.productCategory = productCategory;
                this.price = price;
        }
}
