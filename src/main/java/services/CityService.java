package services;

import entity.City;
import entity.Distance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Александр on 07.06.2016.
 */
@Stateless
public class CityService {
    @PersistenceContext
    private EntityManager em;
    public City getCityById(int id) {
        TypedQuery<City> query = em.createQuery("select c from City c where c.id=:id", City.class);
        List<City> cities = query.setParameter("id", id).getResultList();
        if (cities.size() != 1) {
            return null;
        }
        return cities.get(0);
    }
    public City getCityByName(String name){
        TypedQuery<City> query = em.createQuery("select c from City c where c.name=:name", City.class);
        List<City> cities = query.setParameter("name", name).getResultList();
        if(cities.size() != 1){
            return null;
        }
        return cities.get(0);
    }
    public List<City> getAll(){
        TypedQuery<City> query = em.createQuery("select c from City c", City.class);
        return query.getResultList();
    }
    public void save(City city){
        em.merge(city);
    }
}
