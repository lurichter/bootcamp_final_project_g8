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
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buyer_id")
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "buyer")
    private Set<PurchaseOrder> purchaseOrders;
}
