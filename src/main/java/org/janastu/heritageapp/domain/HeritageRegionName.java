package org.janastu.heritageapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A HeritageRegionName.
 */
@Entity
@Table(name = "heritage_region_name")
public class HeritageRegionName implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotNull
    @Column(name = "center_latitude", nullable = false)
    private Double centerLatitude;
    
    @NotNull
    @Column(name = "center_longitude", nullable = false)
    private Double centerLongitude;
    
    @NotNull
    @Column(name = "top_latitude", nullable = false)
    private Double topLatitude;
    
    @NotNull
    @Column(name = "top_longitude", nullable = false)
    private Double topLongitude;
    
    @NotNull
    @Column(name = "bottom_latitude", nullable = false)
    private Double bottomLatitude;
    
    @NotNull
    @Column(name = "bottom_longitude", nullable = false)
    private Double bottomLongitude;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public Double getCenterLatitude() {
        return centerLatitude;
    }
    
    public void setCenterLatitude(Double centerLatitude) {
        this.centerLatitude = centerLatitude;
    }

    public Double getCenterLongitude() {
        return centerLongitude;
    }
    
    public void setCenterLongitude(Double centerLongitude) {
        this.centerLongitude = centerLongitude;
    }

    public Double getTopLatitude() {
        return topLatitude;
    }
    
    public void setTopLatitude(Double topLatitude) {
        this.topLatitude = topLatitude;
    }

    public Double getTopLongitude() {
        return topLongitude;
    }
    
    public void setTopLongitude(Double topLongitude) {
        this.topLongitude = topLongitude;
    }

    public Double getBottomLatitude() {
        return bottomLatitude;
    }
    
    public void setBottomLatitude(Double bottomLatitude) {
        this.bottomLatitude = bottomLatitude;
    }

    public Double getBottomLongitude() {
        return bottomLongitude;
    }
    
    public void setBottomLongitude(Double bottomLongitude) {
        this.bottomLongitude = bottomLongitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HeritageRegionName heritageRegionName = (HeritageRegionName) o;
        if(heritageRegionName.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, heritageRegionName.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageRegionName{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", centerLatitude='" + centerLatitude + "'" +
            ", centerLongitude='" + centerLongitude + "'" +
            ", topLatitude='" + topLatitude + "'" +
            ", topLongitude='" + topLongitude + "'" +
            ", bottomLatitude='" + bottomLatitude + "'" +
            ", bottomLongitude='" + bottomLongitude + "'" +
            '}';
    }
}
