package org.janastu.heritageapp.web.rest.dto;

import javax.validation.constraints.*;

import org.janastu.heritageapp.domain.HeritageCategory;
import org.janastu.heritageapp.domain.HeritageGroup;
import org.janastu.heritageapp.domain.HeritageLanguage;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the HeritageApp entity.
 */
public class HeritageAppDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;


    @NotNull
    private String contact;


    private Set<HeritageRegionNameDTO> regions = new HashSet<>();

    private Set<HeritageGroup > groups = new HashSet<>();

    private Set<HeritageLanguage > languages = new HashSet<>();

    private Set<HeritageCategory > categorys = new HashSet<>();

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
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Set<HeritageRegionNameDTO> getRegions() {
        return regions;
    }

    public void setRegions(Set<HeritageRegionNameDTO> heritageRegionNames) {
        this.regions = heritageRegionNames;
    }

    public Set<HeritageGroup > getGroups() {
        return groups;
    }

    public void setGroups(Set<HeritageGroup > heritageGroups) {
        this.groups = heritageGroups;
    }

    public Set<HeritageLanguage > getLanguages() {
        return languages;
    }

    public void setLanguages(Set<HeritageLanguage > heritageLanguages) {
        this.languages = heritageLanguages;
    }

    public Set<HeritageCategory > getCategorys() {
        return categorys;
    }

    public void setCategorys(Set<HeritageCategory > heritageCategorys) {
        this.categorys = heritageCategorys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HeritageAppDTO heritageAppDTO = (HeritageAppDTO) o;

        if ( ! Objects.equals(id, heritageAppDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageAppDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", contact='" + contact + "'" +
            '}';
    }
}
