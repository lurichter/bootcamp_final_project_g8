package com.mercadolibre.group8_bootcamp_finalproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class InboundOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(name = "order_datetime", nullable = false)
    private LocalDateTime dateTime = LocalDateTime.now();

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "operator_id")
    private Operator operator;

    @OneToMany(mappedBy = "inboundOrder", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Batch> batches;
}
