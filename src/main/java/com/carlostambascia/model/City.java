package com.carlostambascia.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "City")
@AllArgsConstructor
@NoArgsConstructor
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class City {
    @Id
    private String iataCode;
    private String name;
}
