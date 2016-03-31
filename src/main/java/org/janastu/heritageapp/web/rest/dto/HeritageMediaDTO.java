package org.janastu.heritageapp.web.rest.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;


/**
 * A DTO for the HeritageMedia entity.
 */
public class HeritageMediaDTO implements Serializable {

    private Long id;

    @NotNull
    private String title;


    @NotNull
    private String description;


    private String address;


    @NotNull
    private Double latitude;


    @NotNull
    private Double longitude;


    @NotNull
    private Integer mediaType;


    @Lob
    private byte[] mediaFile;

    private String mediaFileContentType;

    private String urlOrfileLink;


    private String consolidatedTags;


    private String userAgent;


    private ZonedDateTime uploadTime;


    private Long categoryId;

    private String categoryCategoryName;

    private Long languageId;

    private String languageHeritageLanguage;

    private Long groupId;

    private String groupName;

    private Long heritageAppId;

    private String heritageAppName;

    private Long userId;

    private String userLogin;

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
    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }
    public byte[] getMediaFile() {
        return mediaFile;
    }

    public void setMediaFile(byte[] mediaFile) {
        this.mediaFile = mediaFile;
    }

    public String getMediaFileContentType() {
        return mediaFileContentType;
    }

    public void setMediaFileContentType(String mediaFileContentType) {
        this.mediaFileContentType = mediaFileContentType;
    }
    public String getUrlOrfileLink() {
        return urlOrfileLink;
    }

    public void setUrlOrfileLink(String urlOrfileLink) {
        this.urlOrfileLink = urlOrfileLink;
    }
    public String getConsolidatedTags() {
        return consolidatedTags;
    }

    public void setConsolidatedTags(String consolidatedTags) {
        this.consolidatedTags = consolidatedTags;
    }
    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    public ZonedDateTime getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(ZonedDateTime uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long heritageCategoryId) {
        this.categoryId = heritageCategoryId;
    }

    public String getCategoryCategoryName() {
        return categoryCategoryName;
    }

    public void setCategoryCategoryName(String heritageCategoryCategoryName) {
        this.categoryCategoryName = heritageCategoryCategoryName;
    }

    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long heritageLanguageId) {
        this.languageId = heritageLanguageId;
    }

    public String getLanguageHeritageLanguage() {
        return languageHeritageLanguage;
    }

    public void setLanguageHeritageLanguage(String heritageLanguageHeritageLanguage) {
        this.languageHeritageLanguage = heritageLanguageHeritageLanguage;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long heritageGroupId) {
        this.groupId = heritageGroupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String heritageGroupName) {
        this.groupName = heritageGroupName;
    }

    public Long getHeritageAppId() {
        return heritageAppId;
    }

    public void setHeritageAppId(Long heritageAppId) {
        this.heritageAppId = heritageAppId;
    }

    public String getHeritageAppName() {
        return heritageAppName;
    }

    public void setHeritageAppName(String heritageAppName) {
        this.heritageAppName = heritageAppName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HeritageMediaDTO heritageMediaDTO = (HeritageMediaDTO) o;

        if ( ! Objects.equals(id, heritageMediaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageMediaDTO{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", address='" + address + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            ", mediaType='" + mediaType + "'" +
            ", mediaFile='" + mediaFile + "'" +
            ", urlOrfileLink='" + urlOrfileLink + "'" +
            ", consolidatedTags='" + consolidatedTags + "'" +
            ", userAgent='" + userAgent + "'" +
            ", uploadTime='" + uploadTime + "'" +
            '}';
    }
}
