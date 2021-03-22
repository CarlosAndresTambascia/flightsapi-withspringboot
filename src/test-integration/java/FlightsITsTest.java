import com.carlostambascia.config.AppConfig;
import com.carlostambascia.dao.FlightDAO;
import com.carlostambascia.model.City;
import com.carlostambascia.model.Flight;
import com.carlostambascia.model.FlightPrice;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static com.carlostambascia.model.FlightStatus.NOT_DELAYED;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        SqlScriptsTestExecutionListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FlightsITsTest extends AbstractTestNGSpringContextTests {
    private static final BigDecimal ECONOMY = BigDecimal.valueOf(80);
    private static final BigDecimal ECONOMY_PLUS = BigDecimal.valueOf(100);
    private static final BigDecimal FIRST_CLASS = BigDecimal.valueOf(150);
    private static final String DEPARTURE_CITY_NAME = "Buenos Aires";
    private static final String DEPARTURE_IATA_CODE = "bsas";
    private static final String DESTINATION_IATA_CODE = "PTG";
    private static final String DESTINATION_CITY_NAME = "Portugal";
    private static final String AIRLINE = "airline";
    private static final Date DATE_TIME = new Date(92139821893L);
    private static final String AIRLINE_CODE = "airline code";
    private static final Date CONVERTED_DATE = new Date(92053421893L);
    private static final String SOME_RANDOM_AIRLINE = "some random airline";
    private static final String A_DIFFERENT_AIRLINE = "a different airline";
    private static final Date NOT_FOUND_DATE = new Date(87827832173L);
    private static final String RANDOM_STRING = "some random";
    @Inject
    private FlightDAO dao;

    @Test
    public void saveFlightShouldAddFlightCorrectly() {
        //given
        final Flight flight = createFlightBuilder().build();
        //when
        final Integer flightId = dao.addFlight(flight);
        //then
        assertThat(flightId).isEqualTo(1);
    }

    @Test
    public void flightByDateShouldRetrieveCorrectFlight() {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        final Integer idFlight1 = dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final List<Flight> flightsByDate = dao.getFlightsByDate(CONVERTED_DATE);
        //then
        assertThat(flightsByDate).hasSize(1);
        assertThat(flightsByDate).satisfies(flights -> {
            assertThat(flights.get(0).getFlightNumber()).isEqualTo(idFlight1);
            assertThat(flights.get(0).getAirline()).isEqualTo(AIRLINE);
        });
    }

    @Test
    public void flightByDateShouldRetrieveEmptyListIfNotFound() {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final List<Flight> flightsByDate = dao.getFlightsByDate(new Date(23213213211L));
        //then
        assertThat(flightsByDate).hasSize(0);
        assertThat(flightsByDate).isEmpty();
    }

    @Test
    public void getFlightByIdShouldRetrieveCorrectFlight() {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        final Integer idFlight1 = dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final Flight flightsById = dao.getFlightsById(idFlight1);
        //then
        assertThat(flightsById.getFlightNumber()).isEqualTo(idFlight1);
        assertThat(flightsById.getAirline()).isEqualTo(AIRLINE);
    }

    @Test
    public void getFlightByIdShouldRetrieveNullIfNotFound() {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final Flight flightsById = dao.getFlightsById(5);
        //then
        assertThat(flightsById).isNull();
    }

    @Test
    public void getFlightsFromDepartureByDateShouldRetrieveCorrectFlight() {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        final Integer idFlight1 = dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final List<Flight> flightsFromDepartureByDate = dao.getFlightsFromDepartureByDate(DEPARTURE_IATA_CODE, CONVERTED_DATE);
        //then
        assertThat(flightsFromDepartureByDate).satisfies(flights -> {
            assertThat(flights.get(0).getFlightNumber()).isEqualTo(idFlight1);
            assertThat(flights.get(0).getAirline()).isEqualTo(AIRLINE);
        });
    }

    @DataProvider
    public Object[][] emptyFlightByDepartureDateScenarios() {
        return new Object[][]{{null, null},
                                {RANDOM_STRING, CONVERTED_DATE},
                                {DEPARTURE_IATA_CODE, NOT_FOUND_DATE},
        };
    }

    @Test(dataProvider = "emptyFlightByDepartureDateScenarios")
    public void getFlightsFromDepartureByDateShouldRetrieveEmptyListIfNotFound(String departureIataCode, Date date) {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final List<Flight> flightsFromDepartureByDate = dao.getFlightsFromDepartureByDate(departureIataCode, date);
        //then
        assertThat(flightsFromDepartureByDate).isEmpty();
    }

    @Test
    public void getFlightsFromDestinationByDateShouldRetrieveCorrectFlight() {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        final Integer idFlight1 = dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final List<Flight> flightsFromDestinationByDate = dao.getFlightsFromDestinationByDate(DESTINATION_IATA_CODE, CONVERTED_DATE);
        //then
        assertThat(flightsFromDestinationByDate).satisfies(flights -> {
            assertThat(flights.get(0).getFlightNumber()).isEqualTo(idFlight1);
            assertThat(flights.get(0).getAirline()).isEqualTo(AIRLINE);
        });
    }


    @DataProvider
    public Object[][] emptyFlightByDestinationDateScenarios() {
        return new Object[][]{{null, null},
                {RANDOM_STRING, CONVERTED_DATE},
                {DESTINATION_CITY_NAME, NOT_FOUND_DATE},
        };
    }


    @Test(dataProvider = "emptyFlightByDestinationDateScenarios")
    public void getFlightsFromDestinationByDateShouldRetrieveEmptyListIfNotPresent(String departureIataCode, Date date) {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final List<Flight> flightsFromDestinationByDate = dao.getFlightsFromDestinationByDate(departureIataCode, date);
        //then
        assertThat(flightsFromDestinationByDate).isEmpty();
    }

    @Test
    public void getFlightsAirlineByDateShouldRetrieveCorrectFlight() {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        final Integer idFlight1 = dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final List<Flight> flightsAirlineByDate = dao.getFlightsAirlineByDate(AIRLINE, CONVERTED_DATE);
        //then
        assertThat(flightsAirlineByDate).satisfies(flights -> {
            assertThat(flights.get(0).getFlightNumber()).isEqualTo(idFlight1);
            assertThat(flights.get(0).getAirline()).isEqualTo(AIRLINE);
        });
    }

    @DataProvider
    public Object[][] emptyFlightAirlineByDateScenarios() {
        return new Object[][]{{null, null},
                {RANDOM_STRING, CONVERTED_DATE},
                {DESTINATION_CITY_NAME, NOT_FOUND_DATE}
        };
    }

    @Test(dataProvider = "emptyFlightAirlineByDateScenarios")
    public void getFlightsAirlineByDateShouldRetrieveEmptyFlightIfNotPresent(String airline, Date date) {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final List<Flight> flightsAirlineByDate = dao.getFlightsAirlineByDate(airline, date);
        //then
        assertThat(flightsAirlineByDate).isEmpty();
    }

    @Test
    public void getFlightPriceShouldRetrieveCorrectFlight() {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        final Integer idFlight1 = dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final FlightPrice flightPrice = dao.getFlightPrice(idFlight1);
        //then
        assertThat(flightPrice.getId()).isEqualTo(2);
        assertThat(flightPrice.getEconomy()).isEqualTo(ECONOMY.setScale(2));
        assertThat(flightPrice.getEconomyPlus()).isEqualTo(ECONOMY_PLUS.setScale(2));
        assertThat(flightPrice.getFirstClass()).isEqualTo(FIRST_CLASS.setScale(2));
    }

    @DataProvider
    public Object[][] emptyFlightPriceScenarios() {
        return new Object[][]{{null},
                            {10}};
    }

    @Test(dataProvider = "emptyFlightPriceScenarios")
    public void getFlightPriceShouldRetrieveEmptyIfNotPresent(Integer flightId) {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final FlightPrice flightPrice = dao.getFlightPrice(flightId);
        //then
        assertThat(flightPrice).isNull();
    }

    @Test
    public void addFlightPriceShouldAddPriceCorrectly() {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        final Integer idFlight1 = dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final Integer prices = dao.addFlightPrice(idFlight1, createFlightPrice());
        //then
        assertThat(prices).isEqualTo(2);
    }

    @Test
    public void updateFlightPriceShouldUpdatePriceCorrectly() {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        final Integer idFlight1 = dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final Integer prices = dao.updateFlightPrice(idFlight1, createSecondPrice(), 2);
        //then
        assertThat(prices).isEqualTo(2);
    }

    @Test
    public void deleteFlightPriceShouldRemovePriceCorrectly() {
        //given
        final Flight flight = createFlightBuilder()
                .airline(SOME_RANDOM_AIRLINE)
                .estimatedDepartureDateTime(new Date(72139821893L))
                .scheduledDepartureDateTime(new Date(72139821893L))
                .build();
        final Flight flight1 = createFlightBuilder().build();
        final Flight flight2 = createFlightBuilder()
                .airline(A_DIFFERENT_AIRLINE)
                .estimatedDepartureDateTime(new Date(82139821893L))
                .scheduledDepartureDateTime(new Date(82139821893L))
                .build();
        dao.addFlight(flight);
        final Integer idFlight1 = dao.addFlight(flight1);
        dao.addFlight(flight2);
        //when
        final Integer prices = dao.removeFlightPrice(idFlight1, 2);
        //then
        assertThat(prices).isEqualTo(2);
    }

    private FlightPrice createSecondPrice() {
        return new FlightPrice()
                .withFirstClass(BigDecimal.valueOf(100))
                .withEconomy(BigDecimal.valueOf(60))
                .withEconomyPlus(BigDecimal.valueOf(90));
    }

    private Flight.FlightBuilder createFlightBuilder() {
        return Flight.builder()
                .flightNumber(null)
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
                .withId(null)
                .withEconomy(ECONOMY)
                .withEconomyPlus(ECONOMY_PLUS)
                .withFirstClass(FIRST_CLASS);
    }
}
