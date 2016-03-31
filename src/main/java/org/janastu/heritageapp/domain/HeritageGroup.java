package org.janastu.heritageapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A HeritageGroup.
 */
@Entity
@Table(name = "heritage_group")
public class HeritageGroup implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @Lob
    @Column(name = "icon")
    private byte[] icon;
    
    @Column(name = "icon_content_type")        private String iconContentType;
    @NotNull
    @Column(name = "details", nullable = false)
    private String details;
    
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

    public byte[] getIcon() {
        return icon;
    }
    
    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getIconContentType() {
        return iconContentType;
    }

    public void setIconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
    }

    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HeritageGroup heritageGroup = (HeritageGroup) o;
        if(heritageGroup.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, heritageGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageGroup{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", icon='" + icon + "'" +
            ", iconContentType='" + iconContentType + "'" +
            ", details='" + details + "'" +
            '}';
    }
}
