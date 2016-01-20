package org.janastu.heritageapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ImageGeoTagHeritageEntity.
 */
@Entity
@Table(name = "image_geo_tag_heritage_entity")
public class ImageGeoTagHeritageEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;

    @NotNull
    @Size(min = 5)
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotNull
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "consolidated_tags")
    private String consolidatedTags;

    @Column(name = "url_orfile_link")
    private String urlOrfileLink;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")        private String photoContentType;
    @ManyToOne
    @JoinColumn(name = "heritage_category_id")
    private HeritageCategory heritageCategory;

    @ManyToOne
    @JoinColumn(name = "heritage_language_id")
    private HeritageLanguage heritageLanguage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getConsolidatedTags() {
        return consolidatedTags;
    }

    public void setConsolidatedTags(String consolidatedTags) {
        this.consolidatedTags = consolidatedTags;
    }

    public String getUrlOrfileLink() {
        return urlOrfileLink;
    }

    public void setUrlOrfileLink(String urlOrfileLink) {
        this.urlOrfileLink = urlOrfileLink;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public HeritageCategory getHeritageCategory() {
        return heritageCategory;
    }

    public void setHeritageCategory(HeritageCategory heritageCategory) {
        this.heritageCategory = heritageCategory;
    }

    public HeritageLanguage getHeritageLanguage() {
        return heritageLanguage;
    }

    public void setHeritageLanguage(HeritageLanguage heritageLanguage) {
        this.heritageLanguage = heritageLanguage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ImageGeoTagHeritageEntity imageGeoTagHeritageEntity = (ImageGeoTagHeritageEntity) o;
        return Objects.equals(id, imageGeoTagHeritageEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ImageGeoTagHeritageEntity{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", address='" + address + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            ", consolidatedTags='" + consolidatedTags + "'" +
            ", urlOrfileLink='" + urlOrfileLink + "'" +
            ", photo='" + photo + "'" +
            ", photoContentType='" + photoContentType + "'" +
            '}';
    }
}
