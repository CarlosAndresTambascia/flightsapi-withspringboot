package com.carlostambascia.service;

import com.carlostambascia.dao.FlightDAO;
import com.carlostambascia.model.City;
import com.carlostambascia.model.Flight;
import com.carlostambascia.model.FlightPrice;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static com.carlostambascia.model.FlightStatus.NOT_DELAYED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class FlightServiceImplTest {
    private static final BigDecimal ECONOMY = BigDecimal.valueOf(80);
    private static final BigDecimal ECONOMY_PLUS = BigDecimal.valueOf(100);
    private static final BigDecimal FIRST_CLASS = BigDecimal.valueOf(150);
    private static final String DEPARTURE_CITY_NAME = "Buenos Aires";
    private static final String DEPARTURE_IATA_CODE = "bsas";
    private static final String DESTINATION_IATA_CODE = "PTG";
    private static final String DESTINATION_CITY_NAME = "Portugal";
    private static final String AIRLINE = "airline";
    private static final Date DATE_TIME = new Date(9832139821893L);
    private static final String AIRLINE_CODE = "airline code";
    public static final int FLIGHT_NUMBER = 1;
    private FlightService flightService;
    @Mock
    private FlightDAO flightDAO;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
        flightService = new FlightServiceImpl(flightDAO);
    }

    @Test
    public void getFlightsByDateShouldRetrieveFlightIfPresent() {
        //given
        final Flight flight = createFlightBuilder().build();
        when(flightDAO.getFlightsByDate(DATE_TIME)).thenReturn(List.of(flight));
        //when
        final List<Flight> flightsByDate = flightService.getFlightsByDate(DATE_TIME);
        //then
        assertThat(flightsByDate).hasSize(1);
        assertThat(flightsByDate).satisfies(flights -> {
            assertThat(flights.get(0).getFlightNumber()).isEqualTo(FLIGHT_NUMBER);
            assertThat(flights.get(0).getDepartureCity().getIataCode()).isEqualTo(DEPARTURE_IATA_CODE);
            assertThat(flights.get(0).getDepartureCity().getName()).isEqualTo(DEPARTURE_CITY_NAME);
            assertThat(flights.get(0).getDestinationCity().getIataCode()).isEqualTo(DESTINATION_IATA_CODE);
            assertThat(flights.get(0).getDestinationCity().getName()).isEqualTo(DESTINATION_CITY_NAME);
            assertThat(flights.get(0).getAirline()).isEqualTo(AIRLINE);
            assertThat(flights.get(0).getAirlineCode()).isEqualTo(AIRLINE_CODE);
            assertThat(flights.get(0).getScheduledDepartureDateTime()).isEqualTo(DATE_TIME);
            assertThat(flights.get(0).getEstimatedDepartureDateTime()).isEqualTo(DATE_TIME);
            assertThat(flights.get(0).getPrice().get(0).getId()).isEqualTo(1);
            assertThat(flights.get(0).getPrice().get(0).getEconomy()).isEqualTo(ECONOMY);
            assertThat(flights.get(0).getPrice().get(0).getEconomyPlus()).isEqualTo(ECONOMY_PLUS);
            assertThat(flights.get(0).getPrice().get(0).getFirstClass()).isEqualTo(FIRST_CLASS);
            assertThat(flights.get(0).getStatus()).isEqualTo(NOT_DELAYED);
            assertThat(flights.get(0).getGate()).isEqualTo(1);

        });
    }

    @Test
    public void getFlightsByIdShouldRetrieveFlightIfPresent() {
        //given
        final Flight flight = createFlightBuilder().build();
        when(flightDAO.getFlightsById(FLIGHT_NUMBER)).thenReturn(flight);
        //when
        final Flight flightsById = flightService.getFlightsById(FLIGHT_NUMBER);
        //then
        assertThat(flightsById.getFlightNumber()).isEqualTo(FLIGHT_NUMBER);
        assertThat(flightsById.getDepartureCity().getIataCode()).isEqualTo(DEPARTURE_IATA_CODE);
        assertThat(flightsById.getDepartureCity().getName()).isEqualTo(DEPARTURE_CITY_NAME);
        assertThat(flightsById.getDestinationCity().getIataCode()).isEqualTo(DESTINATION_IATA_CODE);
        assertThat(flightsById.getDestinationCity().getName()).isEqualTo(DESTINATION_CITY_NAME);
        assertThat(flightsById.getAirline()).isEqualTo(AIRLINE);
        assertThat(flightsById.getAirlineCode()).isEqualTo(AIRLINE_CODE);
        assertThat(flightsById.getScheduledDepartureDateTime()).isEqualTo(DATE_TIME);
        assertThat(flightsById.getEstimatedDepartureDateTime()).isEqualTo(DATE_TIME);
        assertThat(flightsById.getPrice().get(0).getId()).isEqualTo(1);
        assertThat(flightsById.getPrice().get(0).getEconomy()).isEqualTo(ECONOMY);
        assertThat(flightsById.getPrice().get(0).getEconomyPlus()).isEqualTo(ECONOMY_PLUS);
        assertThat(flightsById.getPrice().get(0).getFirstClass()).isEqualTo(FIRST_CLASS);
        assertThat(flightsById.getStatus()).isEqualTo(NOT_DELAYED);
        assertThat(flightsById.getGate()).isEqualTo(1);
    }

    @Test
    public void getFlightsByDepartureDateRetrieveFlightIfPresent() {
        //given
        final Flight flight = createFlightBuilder().build();
        when(flightDAO.getFlightsFromDepartureByDate(DEPARTURE_IATA_CODE, DATE_TIME)).thenReturn(List.of(flight));
        //when
        final List<Flight> flightsFromDepartureByDate = flightService.getFlightsFromDepartureByDate(DEPARTURE_IATA_CODE, DATE_TIME);
        //then
        assertThat(flightsFromDepartureByDate).hasSize(1);
        assertThat(flightsFromDepartureByDate).satisfies(flights -> {
            assertThat(flights.get(0).getFlightNumber()).isEqualTo(FLIGHT_NUMBER);
            assertThat(flights.get(0).getDepartureCity().getIataCode()).isEqualTo(DEPARTURE_IATA_CODE);
            assertThat(flights.get(0).getDepartureCity().getName()).isEqualTo(DEPARTURE_CITY_NAME);
            assertThat(flights.get(0).getDestinationCity().getIataCode()).isEqualTo(DESTINATION_IATA_CODE);
            assertThat(flights.get(0).getDestinationCity().getName()).isEqualTo(DESTINATION_CITY_NAME);
            assertThat(flights.get(0).getAirline()).isEqualTo(AIRLINE);
            assertThat(flights.get(0).getAirlineCode()).isEqualTo(AIRLINE_CODE);
            assertThat(flights.get(0).getScheduledDepartureDateTime()).isEqualTo(DATE_TIME);
            assertThat(flights.get(0).getEstimatedDepartureDateTime()).isEqualTo(DATE_TIME);
            assertThat(flights.get(0).getPrice().get(0).getId()).isEqualTo(1);
            assertThat(flights.get(0).getPrice().get(0).getEconomy()).isEqualTo(ECONOMY);
            assertThat(flights.get(0).getPrice().get(0).getEconomyPlus()).isEqualTo(ECONOMY_PLUS);
            assertThat(flights.get(0).getPrice().get(0).getFirstClass()).isEqualTo(FIRST_CLASS);
            assertThat(flights.get(0).getStatus()).isEqualTo(NOT_DELAYED);
            assertThat(flights.get(0).getGate()).isEqualTo(1);

        });
    }

    @Test
    public void getFlightsFromDestinationByDateRetrieveFlightIfPresent() {
        //given
        final Flight flight = createFlightBuilder().build();
        when(flightDAO.getFlightsFromDestinationByDate(DESTINATION_IATA_CODE, DATE_TIME)).thenReturn(List.of(flight));
        //when
        final List<Flight> flightsFromDestinationByDate = flightService.getFlightsFromDestinationByDate(DESTINATION_IATA_CODE, DATE_TIME);
        //then
        assertThat(flightsFromDestinationByDate).hasSize(1);
        assertThat(flightsFromDestinationByDate).satisfies(flights -> {
            assertThat(flights.get(0).getFlightNumber()).isEqualTo(FLIGHT_NUMBER);
            assertThat(flights.get(0).getDepartureCity().getIataCode()).isEqualTo(DEPARTURE_IATA_CODE);
            assertThat(flights.get(0).getDepartureCity().getName()).isEqualTo(DEPARTURE_CITY_NAME);
            assertThat(flights.get(0).getDestinationCity().getIataCode()).isEqualTo(DESTINATION_IATA_CODE);
            assertThat(flights.get(0).getDestinationCity().getName()).isEqualTo(DESTINATION_CITY_NAME);
            assertThat(flights.get(0).getAirline()).isEqualTo(AIRLINE);
            assertThat(flights.get(0).getAirlineCode()).isEqualTo(AIRLINE_CODE);
            assertThat(flights.get(0).getScheduledDepartureDateTime()).isEqualTo(DATE_TIME);
            assertThat(flights.get(0).getEstimatedDepartureDateTime()).isEqualTo(DATE_TIME);
            assertThat(flights.get(0).getPrice().get(0).getId()).isEqualTo(1);
            assertThat(flights.get(0).getPrice().get(0).getEconomy()).isEqualTo(ECONOMY);
            assertThat(flights.get(0).getPrice().get(0).getEconomyPlus()).isEqualTo(ECONOMY_PLUS);
            assertThat(flights.get(0).getPrice().get(0).getFirstClass()).isEqualTo(FIRST_CLASS);
            assertThat(flights.get(0).getStatus()).isEqualTo(NOT_DELAYED);
            assertThat(flights.get(0).getGate()).isEqualTo(1);

        });
    }

    @Test
    public void getFlightsAirlineByDateRetrieveFlightIfPresent() {
        //given
        final Flight flight = createFlightBuilder().build();
        when(flightDAO.getFlightsAirlineByDate(AIRLINE, DATE_TIME)).thenReturn(List.of(flight));
        //when
        final List<Flight> flightsAirlineByDate = flightService.getFlightsAirlineByDate(AIRLINE, DATE_TIME);
        //then
        assertThat(flightsAirlineByDate).hasSize(1);
        assertThat(flightsAirlineByDate).satisfies(flights -> {
            assertThat(flights.get(0).getFlightNumber()).isEqualTo(FLIGHT_NUMBER);
            assertThat(flights.get(0).getDepartureCity().getIataCode()).isEqualTo(DEPARTURE_IATA_CODE);
            assertThat(flights.get(0).getDepartureCity().getName()).isEqualTo(DEPARTURE_CITY_NAME);
            assertThat(flights.get(0).getDestinationCity().getIataCode()).isEqualTo(DESTINATION_IATA_CODE);
            assertThat(flights.get(0).getDestinationCity().getName()).isEqualTo(DESTINATION_CITY_NAME);
            assertThat(flights.get(0).getAirline()).isEqualTo(AIRLINE);
            assertThat(flights.get(0).getAirlineCode()).isEqualTo(AIRLINE_CODE);
            assertThat(flights.get(0).getScheduledDepartureDateTime()).isEqualTo(DATE_TIME);
            assertThat(flights.get(0).getEstimatedDepartureDateTime()).isEqualTo(DATE_TIME);
            assertThat(flights.get(0).getPrice().get(0).getId()).isEqualTo(1);
            assertThat(flights.get(0).getPrice().get(0).getEconomy()).isEqualTo(ECONOMY);
            assertThat(flights.get(0).getPrice().get(0).getEconomyPlus()).isEqualTo(ECONOMY_PLUS);
            assertThat(flights.get(0).getPrice().get(0).getFirstClass()).isEqualTo(FIRST_CLASS);
            assertThat(flights.get(0).getStatus()).isEqualTo(NOT_DELAYED);
            assertThat(flights.get(0).getGate()).isEqualTo(1);

        });
    }

    @Test
    public void getFlightPriceRetrieveFlightIfPresent() {
        //given
        when(flightDAO.getFlightPrice(FLIGHT_NUMBER)).thenReturn(createFlightPrice());
        //when
        final FlightPrice flightPrice = flightService.getFlightPrice(FLIGHT_NUMBER);
        //then
        assertThat(flightPrice.getId()).isEqualTo(1);
        assertThat(flightPrice.getEconomy()).isEqualTo(ECONOMY);
        assertThat(flightPrice.getEconomyPlus()).isEqualTo(ECONOMY_PLUS);
        assertThat(flightPrice.getFirstClass()).isEqualTo(FIRST_CLASS);
    }

    private Flight.FlightBuilder createFlightBuilder() {
        return Flight.builder()
                .flightNumber(FLIGHT_NUMBER)
                .departureCity(new City(DEPARTURE_IATA_CODE, DEPARTURE_CITY_NAME))
                .destinationCity(new City(DESTINATION_IATA_CODE, DESTINATION_CITY_NAME))
                .airline(AIRLINE)
                .airlineCode(AIRLINE_CODE)
                .scheduledDepartureDateTime(DATE_TIME)
                .estimatedDepartureDateTime(DATE_TIME)
                .price(List.of(createFlightPrice()))
                .gate(1)
                .status(NOT_DELAYED);
    }

    private FlightPrice createFlightPrice() {
        return new FlightPrice()
                .withId(1)
                .withEconomy(ECONOMY)
                .withEconomyPlus(ECONOMY_PLUS)
                .withFirstClass(FIRST_CLASS);
    }
}