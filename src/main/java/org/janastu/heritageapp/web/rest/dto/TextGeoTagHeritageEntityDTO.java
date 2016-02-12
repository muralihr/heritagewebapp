package org.janastu.heritageapp.web.rest.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the TextGeoTagHeritageEntity entity.
 */
public class TextGeoTagHeritageEntityDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 5)
    private String title;

    @NotNull
    @Size(min = 5)
    private String description;

    private String address;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;

    private String consolidatedTags;

    private String textDetails;

    @NotNull
    private ZonedDateTime uploadTime;

    private Long heritageCategoryId;

    private String heritageCategoryCategoryName;

    private Long heritageLanguageId;

    private String heritageLanguageHeritageLanguage;

    private Long customerId;

    private String customerLogin;

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

    public String getTextDetails() {
        return textDetails;
    }

    public void setTextDetails(String textDetails) {
        this.textDetails = textDetails;
    }

    public ZonedDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(ZonedDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Long getHeritageCategoryId() {
        return heritageCategoryId;
    }

    public void setHeritageCategoryId(Long heritageCategoryId) {
        this.heritageCategoryId = heritageCategoryId;
    }

    public String getHeritageCategoryCategoryName() {
        return heritageCategoryCategoryName;
    }

    public void setHeritageCategoryCategoryName(String heritageCategoryCategoryName) {
        this.heritageCategoryCategoryName = heritageCategoryCategoryName;
    }

    public Long getHeritageLanguageId() {
        return heritageLanguageId;
    }

    public void setHeritageLanguageId(Long heritageLanguageId) {
        this.heritageLanguageId = heritageLanguageId;
    }

    public String getHeritageLanguageHeritageLanguage() {
        return heritageLanguageHeritageLanguage;
    }

    public void setHeritageLanguageHeritageLanguage(String heritageLanguageHeritageLanguage) {
        this.heritageLanguageHeritageLanguage = heritageLanguageHeritageLanguage;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long UserId) {
        this.customerId = UserId;
    }

    public String getCustomerLogin() {
        return customerLogin;
    }

    public void setCustomerLogin(String UserLogin) {
        this.customerLogin = UserLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TextGeoTagHeritageEntityDTO textGeoTagHeritageEntityDTO = (TextGeoTagHeritageEntityDTO) o;

        if ( ! Objects.equals(id, textGeoTagHeritageEntityDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TextGeoTagHeritageEntityDTO{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", address='" + address + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            ", consolidatedTags='" + consolidatedTags + "'" +
            ", textDetails='" + textDetails + "'" +
            ", uploadTime='" + uploadTime + "'" +
            '}';
    }
}
