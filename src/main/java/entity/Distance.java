package entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlIDREF;

/**
 * Created by Александр on 11.06.2016.
 */
@Entity
public class Distance {
    private int id;
    private double distance;
    private City cityByFromCity;
    private City cityByToCity;

    @Id
    @Column(name = "Id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Distance")
    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Distance distance1 = (Distance) o;

        if (id != distance1.id) return false;
        if (Double.compare(distance1.distance, distance) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(distance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @ManyToOne
    @XmlIDREF
    @JoinColumn(name = "From_city", referencedColumnName = "Id", nullable = false)
    public City getCityByFromCity() {
        return cityByFromCity;
    }

    public void setCityByFromCity(City cityByFromCity) {
        this.cityByFromCity = cityByFromCity;
    }

    @ManyToOne
    @XmlIDREF
    @JoinColumn(name = "To_city", referencedColumnName = "Id", nullable = false)
    public City getCityByToCity() {
        return cityByToCity;
    }

    public void setCityByToCity(City cityByToCity) {
        this.cityByToCity = cityByToCity;
    }
}
