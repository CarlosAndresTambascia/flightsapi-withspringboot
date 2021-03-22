package com.carlostambascia.service;


import com.carlostambascia.model.City;

import java.util.List;

public interface CityService {
    City getCity(String iataCode);
    List<City> list();
}
