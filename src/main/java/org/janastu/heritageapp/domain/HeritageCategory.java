package org.janastu.heritageapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A HeritageCategory.
 */
@Entity
@Table(name = "heritage_category")
public class HeritageCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "category_name", nullable = false)
    private String categoryName;

    @Lob
    @Column(name = "category_icon")
    private byte[] categoryIcon;

    @Column(name = "category_icon_content_type")        private String categoryIconContentType;
    @Column(name = "category_decription")
    private String categoryDecription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public byte[] getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(byte[] categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getCategoryIconContentType() {
        return categoryIconContentType;
    }

    public void setCategoryIconContentType(String categoryIconContentType) {
        this.categoryIconContentType = categoryIconContentType;
    }

    public String getCategoryDecription() {
        return categoryDecription;
    }

    public void setCategoryDecription(String categoryDecription) {
        this.categoryDecription = categoryDecription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HeritageCategory heritageCategory = (HeritageCategory) o;
        return Objects.equals(id, heritageCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageCategory{" +
            "id=" + id +
            ", categoryName='" + categoryName + "'" +
            ", categoryIcon='" + categoryIcon + "'" +
            ", categoryIconContentType='" + categoryIconContentType + "'" +
            ", categoryDecription='" + categoryDecription + "'" +
            '}';
    }
}
