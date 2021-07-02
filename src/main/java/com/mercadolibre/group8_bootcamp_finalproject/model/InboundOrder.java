package com.mercadolibre.group8_bootcamp_finalproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InboundOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "order_datetime", nullable = false)
    private LocalDateTime date_time = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Operator operator;

    @OneToMany(mappedBy = "inboundOrder")
    private Set<Batch> batch;
}
