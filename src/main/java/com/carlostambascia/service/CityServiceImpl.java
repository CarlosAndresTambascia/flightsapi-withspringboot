package com.carlostambascia.service;

import com.carlostambascia.dao.CityDAO;
import lombok.RequiredArgsConstructor;
import com.carlostambascia.model.City;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

@Named
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityDAO cityDAO;

    @Override
    public City getCity(String iataCode) {
        return cityDAO.getCity(iataCode);
    }

    @Override
    public List<City> list() {
        return cityDAO.list();
    }
}
