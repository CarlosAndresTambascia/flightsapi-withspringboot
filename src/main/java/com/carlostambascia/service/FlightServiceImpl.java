package com.carlostambascia.service;

import com.carlostambascia.dao.FlightDAO;
import lombok.RequiredArgsConstructor;
import com.carlostambascia.model.Flight;
import com.carlostambascia.model.FlightPrice;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.Date;
import java.util.List;

@Named
@Transactional
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightDAO flightDAO;

    @Override
    public List<Flight> getFlightsByDate(Date date) {
        return flightDAO.getFlightsByDate(date);
    }

    @Override
    public Flight getFlightsById(Integer flightNumber) {
        return flightDAO.getFlightsById(flightNumber);
    }

    @Override
    public List<Flight> getFlightsFromDepartureByDate(String iataCodeDeparture, Date date) {
        return flightDAO.getFlightsFromDepartureByDate(iataCodeDeparture, date);
    }

    @Override
    public List<Flight> getFlightsFromDestinationByDate(String iataCodeDestination, Date date) {
        return flightDAO.getFlightsFromDestinationByDate(iataCodeDestination, date);
    }

    @Override
    public List<Flight> getFlightsAirlineByDate(String airline, Date date) {
        return flightDAO.getFlightsAirlineByDate(airline, date);
    }

    @Override
    public Integer addFlight(Flight flight) {
        return flightDAO.addFlight(flight);
    }

    @Override
    public FlightPrice getFlightPrice(Integer flightNumber) {
        return flightDAO.getFlightPrice(flightNumber);
    }

    @Override
    public Integer addFlightPrice(Integer flightNumber, FlightPrice price) {
        return flightDAO.addFlightPrice(flightNumber, price);
    }

    @Override
    public Integer updateFlightPrice(Integer flightNumber, FlightPrice price, Integer priceId) {
        return flightDAO.updateFlightPrice(flightNumber, price, priceId);
    }

    @Override
    public Integer removeFlightPrice(Integer flightNumber, Integer flightPriceId) {
        return flightDAO.removeFlightPrice(flightNumber, flightPriceId);
    }
}
