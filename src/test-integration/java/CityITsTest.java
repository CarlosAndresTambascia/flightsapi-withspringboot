import com.carlostambascia.config.AppConfig;
import com.carlostambascia.dao.CityDAO;
import com.carlostambascia.model.City;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.Test;

import javax.inject.Inject;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
@TestExecutionListeners( {
        DependencyInjectionTestExecutionListener.class,
        SqlScriptsTestExecutionListener.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CityITsTest extends AbstractTestNGSpringContextTests {
    private static final String IATA_CODE = "MDP";
    private static final String CITY_NAME = "Mar del Plata";
    @Inject
    private CityDAO dao;

    @Test
    @Sql("classpath:city.sql")
    public void getCityShouldRetrieveCityIfPresent() {
        //when
        final City city = dao.getCity(IATA_CODE);
        //then
        assertThat(city.getIataCode()).isEqualTo(IATA_CODE);
        assertThat(city.getName()).isEqualTo(CITY_NAME);
    }

    @Test
    @Sql("classpath:city.sql")
    public void getCityShouldRetrieveNullIfNotFound() {
        //when
        final City city = dao.getCity("any iata code");
        //then
        assertThat(city).isNull();
    }

    @Test
    public void getCityShouldRetrieveNullIfNoCityIsPresent() {
        //when
        final City city = dao.getCity("any string");
        //then
        assertThat(city).isNull();
    }

    @Test
    @Sql("classpath:city.sql")
    public void listCitiesShouldRetrieveCitiesIfPresent() {
        //when
        final List<City> cities = dao.list();
        //then
        assertThat(cities).hasSize(1);
        assertThat(cities.get(0)).satisfies(city -> {
            assertThat(city.getIataCode()).isEqualTo(IATA_CODE);
            assertThat(city.getName()).isEqualTo(CITY_NAME);
        });
    }

    @Test
    public void listCitiesShouldRetrieveEmptyListIfNoElementsPresent() {
        //when
        final List<City> cities = dao.list();
        //then
        assertThat(cities).hasSize(0);
    }
}
