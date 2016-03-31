package org.janastu.heritageapp.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the HeritageRegionName entity.
 */
public class HeritageRegionNameDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


    @NotNull
    private Double centerLatitude;


    @NotNull
    private Double centerLongitude;


    @NotNull
    private Double topLatitude;


    @NotNull
    private Double topLongitude;


    @NotNull
    private Double bottomLatitude;


    @NotNull
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

        HeritageRegionNameDTO heritageRegionNameDTO = (HeritageRegionNameDTO) o;

        if ( ! Objects.equals(id, heritageRegionNameDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageRegionNameDTO{" +
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
