package com.carlostambascia.dao;

import com.carlostambascia.model.Flight;
import com.carlostambascia.model.FlightPrice;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface FlightDAO {
    List<Flight> getFlightsByDate(Date date);
    Flight getFlightsById(Integer flightNumber);
    List<Flight> getFlightsFromDepartureByDate(String iataCodeDeparture, Date date);
    List<Flight> getFlightsFromDestinationByDate(String iataCodeDestination, Date date);
    List<Flight> getFlightsAirlineByDate(String airline, Date date);
    Integer addFlight(Flight flight);
    FlightPrice getFlightPrice(Integer flightNumber);
    Integer addFlightPrice(Integer flightNumber, FlightPrice price);
    Integer updateFlightPrice(Integer flightNumber, FlightPrice price, Integer priceId);
    Integer removeFlightPrice(Integer flightNumber, Integer priceId);
}
