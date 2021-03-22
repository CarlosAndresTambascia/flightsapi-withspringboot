package com.carlostambascia.dao;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.carlostambascia.model.Flight;
import com.carlostambascia.model.FlightPrice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Named;
import java.util.*;

import static io.vavr.API.*;

@Slf4j
@Named
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class FlightDAOImpl implements FlightDAO {
    private final SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Flight> getFlightsByDate(Date date) {
        final String hql = "from Flight f where f.scheduledDepartureDateTime = ?";
        return getCurrentSession().createQuery(hql)
                .setParameter(0, new Date(date.getTime() + (1000 * 60 * 60 * 24)))
                .list();
    }

    @Override
    public Flight getFlightsById(Integer flightNumber) {
        final String hql = "from Flight f where f.flightNumber = ?";
        final Optional<Flight> flight = getCurrentSession().createQuery(hql)
                .setParameter(0, flightNumber)
                .uniqueResultOptional();
        return flight.orElse(null);
    }

    @Override
    public List<Flight> getFlightsFromDepartureByDate(String iataCodeDeparture, Date date) {
        final String hql = "from Flight f where f.scheduledDepartureDateTime = ? AND f.departureCity.iataCode = ?";
        return getCurrentSession().createQuery(hql)
                .setParameter(0, new Date(Option.of(date).map(date1 -> date1.getTime() + (1000 * 60 * 60 * 24)).getOrElse(0L)))
                .setParameter(1, iataCodeDeparture)
                .list();
    }

    @Override
    public List<Flight> getFlightsFromDestinationByDate(String iataCodeDestination, Date date) {
        final String hql = "from Flight f where f.destinationCity.iataCode = ? AND f.scheduledDepartureDateTime = ?";
        return getCurrentSession().createQuery(hql)
                .setParameter(0, iataCodeDestination)
                .setParameter(1, new Date(Option.of(date).map(date1 -> date1.getTime() + (1000 * 60 * 60 * 24)).getOrElse(0L)))
                .list();
    }

    @Override
    public List<Flight> getFlightsAirlineByDate(String airline, Date date) {
        final String hql = "from Flight f where f.airline = ? AND f.scheduledDepartureDateTime = ?";
        return getCurrentSession().createQuery(hql)
                .setParameter(0, airline)
                .setParameter(1, new Date(Option.of(date).map(date1 -> date1.getTime() + (1000 * 60 * 60 * 24)).getOrElse(0L)))
                .list();
    }

    @Override
    public Integer addFlight(Flight flight) {
        Try.of(() -> getCurrentSession().save(flight))
                .onFailure(e -> log.warn("There was an issue while saving the flight with the following exception -> " + e.getLocalizedMessage()));
        return flight.getFlightNumber();
    }

    @Override
    public FlightPrice getFlightPrice(Integer flightNumber) {
        final String hql = "select f.price from Flight f where f.flightNumber = ?";
        final Optional<FlightPrice> price = getCurrentSession().createQuery(hql)
                .setParameter(0, flightNumber)
                .uniqueResultOptional();
        return price.orElse(null);
    }

    @Override
    public Integer addFlightPrice(Integer flightNumber, FlightPrice price) {
        final Flight flight = getFlightsById(flightNumber);
        final List<FlightPrice> prices = Option.of(flight)
                .flatMap(f -> Option.of(f.getPrice()))
                .peek(listOfPrices -> listOfPrices.add(price))
                .getOrElse(Collections.emptyList());
         return Match(prices.isEmpty()).of(
                Case($(false), () -> {
                    Flight flightWithNewPrice = flight.toBuilder().price(prices).build();
                    getCurrentSession().merge(flightWithNewPrice);
                    return prices.size();
                }),
                Case($(), () -> null));
    }

    @Override
    public Integer updateFlightPrice(Integer flightNumber, FlightPrice price, Integer priceId) {
        final Flight flight = getFlightsById(flightNumber);
        final List<FlightPrice> flightPrices = Option.of(flight)
                .filter(Objects::nonNull)
                .map(Flight::getPrice)
                .getOrNull();
        Option.of(flightPrices)
                .getOrNull()
                .stream()
                .filter(id -> id.getId().equals(priceId))
                .findFirst()
                .map(flightPrices::remove);
        flightPrices.add(price.withId(priceId));
        flightPrices.sort(Comparator.comparing(FlightPrice::getId));
        final Flight flightWithNewPrice = flight.toBuilder().price(flightPrices).build();
        sessionFactory.getCurrentSession().merge(flightWithNewPrice);
        return priceId;
    }

    @Override
    public Integer removeFlightPrice(Integer flightNumber, Integer priceId) {
        final Flight flight = getFlightsById(flightNumber);
        final List<FlightPrice> flightPrices = Option.of(flight)
                .filter(Objects::nonNull)
                .map(Flight::getPrice)
                .getOrNull();
        Option.of(flightPrices)
                .getOrNull()
                .stream()
                .filter(price -> price.getId().equals(priceId))
                .findFirst()
                .map(flightPrices::remove);
        final Flight flightWithNewPrice = flight.toBuilder().price(flightPrices).build();
        sessionFactory.getCurrentSession().merge(flightWithNewPrice);
        return priceId;
    }
}
