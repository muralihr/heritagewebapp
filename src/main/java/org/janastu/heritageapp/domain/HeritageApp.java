package org.janastu.heritageapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HeritageApp.
 */
@Entity
@Table(name = "heritage_app")
public class HeritageApp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @NotNull
    @Column(name = "contact", nullable = false)
    private String contact;
    
    @ManyToMany
    @JoinTable(name = "heritage_app_region",
               joinColumns = @JoinColumn(name="heritage_apps_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="regions_id", referencedColumnName="ID"))
    private Set<HeritageRegionName> regions = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "heritage_app_group",
               joinColumns = @JoinColumn(name="heritage_apps_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="groups_id", referencedColumnName="ID"))
    private Set<HeritageGroup> groups = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "heritage_app_language",
               joinColumns = @JoinColumn(name="heritage_apps_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="languages_id", referencedColumnName="ID"))
    private Set<HeritageLanguage> languages = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "heritage_app_category",
               joinColumns = @JoinColumn(name="heritage_apps_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="categorys_id", referencedColumnName="ID"))
    private Set<HeritageCategory> categorys = new HashSet<>();

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

    public Set<HeritageRegionName> getRegions() {
        return regions;
    }

    public void setRegions(Set<HeritageRegionName> heritageRegionNames) {
        this.regions = heritageRegionNames;
    }

    public Set<HeritageGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<HeritageGroup> heritageGroups) {
        this.groups = heritageGroups;
    }

    public Set<HeritageLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<HeritageLanguage> heritageLanguages) {
        this.languages = heritageLanguages;
    }

    public Set<HeritageCategory> getCategorys() {
        return categorys;
    }

    public void setCategorys(Set<HeritageCategory> heritageCategorys) {
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
        HeritageApp heritageApp = (HeritageApp) o;
        if(heritageApp.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, heritageApp.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageApp{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", contact='" + contact + "'" +
            '}';
    }
}
