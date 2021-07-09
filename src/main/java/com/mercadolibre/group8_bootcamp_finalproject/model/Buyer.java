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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buyer_id")
    private Long id;

    @OneToOne
    private Users user;

    @OneToMany(mappedBy = "buyer")
    private Set<PurchaseOrder> purchaseOrders;
}
