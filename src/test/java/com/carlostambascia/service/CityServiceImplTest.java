package com.carlostambascia.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import com.carlostambascia.dao.CityDAO;
import com.carlostambascia.model.City;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class CityServiceImplTest {
    private static final String IATA_CODE = "aIataCode";
    private static final String CITY_NAME = "Mar del Plata";
    private static final String CITY_NAME_2 = "Madrid";
    private static final String CITY_NAME_3 = "Seattle";
    private static final String IATA_CODE_2 = "iataCode 2";
    private static final String IATA_CODE_3 = "iataCode 3";
    private CityService cityService;
    @Mock
    private CityDAO cityDAO;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
        cityService = new CityServiceImpl(cityDAO);
    }

    @Test
    public void getCityShouldRetrieveCityIfPresent() {
        //given
        final City city = new City(IATA_CODE, CITY_NAME);
        when(cityDAO.getCity(IATA_CODE)).thenReturn(city);
        //when
        final City foundCity = cityService.getCity(IATA_CODE);
        //then
        assertThat(foundCity.getIataCode()).isEqualTo(IATA_CODE);
        assertThat(foundCity.getName()).isEqualTo(CITY_NAME);
    }

    @DataProvider
    public Object[][] notFoundScenarios() {
        return new Object[][]{{null},
                              {"some random iata code"}};
    }

    @Test(dataProvider = "notFoundScenarios")
    public void getCityShouldRetrieveNullIfNotPresent(String iataCode) {
        //given
        final City city = new City(IATA_CODE, CITY_NAME);
        when(cityDAO.getCity(IATA_CODE)).thenReturn(city);
        //when
        final City foundCity = cityService.getCity(iataCode);
        //then
        assertThat(foundCity).isNull();
    }

    @Test
    public void listCitiesShouldRetrieveCitiesIfPresent() {
        //given
        final City city1 = new City(IATA_CODE, CITY_NAME);
        final City city2 = new City(IATA_CODE_2, CITY_NAME_2);
        final City city3 = new City(IATA_CODE_3, CITY_NAME_3);
        when(cityDAO.list()).thenReturn(List.of(city1, city2, city3));
        //when
        final List<City> cities = cityService.list();
        //then
        assertThat(cities).satisfies(city -> {
            assertThat(city.get(0).getIataCode()).isEqualTo(IATA_CODE);
            assertThat(city.get(0).getName()).isEqualTo(CITY_NAME);
            assertThat(city.get(1).getIataCode()).isEqualTo(IATA_CODE_2);
            assertThat(city.get(1).getName()).isEqualTo(CITY_NAME_2);
            assertThat(city.get(2).getIataCode()).isEqualTo(IATA_CODE_3);
            assertThat(city.get(2).getName()).isEqualTo(CITY_NAME_3);

        });
    }

    @Test
    public void listOfCitiesShouldRetrieveNullIfNotPresent() {
        //given
        when(cityDAO.list()).thenReturn(null);
        //when
        final List<City> foundCity = cityService.list();
        //then
        assertThat(foundCity).isNull();
    }
}