package com.carlostambascia.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity(name = "FlightPrice")
@AllArgsConstructor
@NoArgsConstructor
@Setter(value = AccessLevel.PACKAGE)
@Getter
@With
public class FlightPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private BigDecimal firstClass;
    private BigDecimal economy;
    private BigDecimal economyPlus;
}
