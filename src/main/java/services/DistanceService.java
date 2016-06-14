package services;

import entity.City;
import entity.Distance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Александр on 13.06.2016.
 */
@Stateless
public class DistanceService {
    @PersistenceContext
    private EntityManager em;
    public Double getDistance(City cityFrom, City cityTo){
        TypedQuery<Distance> query = em.createQuery("select d from Distance d where d.cityByToCity=:cityTo and d.cityByFromCity=:cityFrom", Distance.class);
        query.setParameter("cityTo", cityTo);
        query.setParameter("cityFrom", cityFrom);
        List<Distance> distance = query.getResultList();
        if(distance.size()==1){
            return distance.get(0).getDistance();
        }
        return null;
    }
    public void save(Distance d){
        em.merge(d);
    }
}
