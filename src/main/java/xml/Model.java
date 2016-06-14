package xml;

import entity.City;
import entity.Distance;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Александр on 12.06.2016.
 */

/**
 * Class which used as xml root element in serialization/deserialization.
 * Contains list of city and list of distance
 */
@XmlRootElement
public class Model {

    private List<City> cities;

    private List<Distance> distances;
    @XmlElementWrapper(name = "cities")
    @XmlElements({
            @XmlElement(name = "city", type = City.class)
    })
    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
    @XmlElementWrapper(name = "distances")
    @XmlElements({
            @XmlElement(name = "distance", type = Distance.class)
    })
    public List<Distance> getDistances() {
        return distances;
    }

    public void setDistances(List<Distance> distances) {
        this.distances = distances;
    }
}
