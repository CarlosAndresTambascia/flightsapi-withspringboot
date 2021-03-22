package com.carlostambascia.controller;

import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import com.carlostambascia.model.City;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.carlostambascia.service.CityService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/city")
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping("/{iataCode}")
    public ResponseEntity<City> get(@PathVariable("iataCode")  String iataCode) {
        return Try.of(() -> cityService.getCity(iataCode))
                .filter(Objects::nonNull)
                .map(city1 -> ResponseEntity.ok().body(city1))
                .getOrElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/")
    public ResponseEntity<List<City>> list() {
        return Try.of(cityService::list)
                .filter(Objects::nonNull)
                .map(cities -> ResponseEntity.ok().body(cities))
                .getOrElse(ResponseEntity.notFound().build());
    }
}
