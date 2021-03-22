package com.carlostambascia.dao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import com.carlostambascia.model.City;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named
@Getter
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class CityDAOImpl implements CityDAO {
    private final SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public City getCity(String iataCode) {
        final String hql = "from City c where c.iataCode = ?";
        final Optional<City> city = getCurrentSession().createQuery(hql)
                .setParameter(0, iataCode)
                .uniqueResultOptional();
        return city.orElse(null);
    }

    @Override
    public List<City> list() {
        return getCurrentSession().createQuery("from City").list();
    }
}
