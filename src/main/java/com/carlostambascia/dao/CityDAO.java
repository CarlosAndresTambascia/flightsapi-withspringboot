package com.carlostambascia.dao;

import com.carlostambascia.model.City;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CityDAO {
    City getCity(String iataCode);
    List<City> list();
}
