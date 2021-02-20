package com.aeon.payment.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Builder
@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private BigDecimal balans;

    @ManyToOne
    private User user;

}
