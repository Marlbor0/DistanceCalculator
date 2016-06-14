package xml;

/**
 * Created by Александр on 11.06.2016.
 */

import entity.City;
import entity.Distance;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.hibernate.Session;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Deserialization {

    private static final String FILE_NAME = "C:\\Igri\\jaxb-model-same-big.xml";

    public static void main(String[] args) throws Exception {
        Model m = new Model();
        List<City> cities = new ArrayList<City>();
        City c = null;
        City temp = null;
        Distance d = null;
        List<Distance> distances = new ArrayList<Distance>();
        for (int i = 0; i < 100; i++) {
            c = new City();
            c.setId(i);
            c.setName("Name" + i);
            c.setLatitude(Math.random() * 360);
            c.setLongitude(Math.random() * 360);
            cities.add(c);
            if (temp != null) {
                d = new Distance();
                d.setId(i * 2);
                d.setCityByFromCity(c);
                d.setCityByToCity(temp);
                d.setDistance(Math.random() * 10000);
                distances.add(d);

                d = new Distance();
                d.setId(i * 2 + 1);
                d.setCityByFromCity(temp);
                d.setCityByToCity(c);
                d.setDistance(Math.random() * 10000);
                distances.add(d);
            }
            temp = c;
        }
        m.setCities(cities);
        m.setDistances(distances);
        Serialization.ObjectToXML(m, FILE_NAME);
    }

    /**
     * Convert xml file to a model
     */
    public static Model XMLToObject(File f) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Model.class);
        Unmarshaller un = context.createUnmarshaller();
        Model m = (Model) un.unmarshal(f);
        return m;
    }

}
