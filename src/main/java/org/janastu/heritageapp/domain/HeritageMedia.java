package org.janastu.heritageapp.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HeritageMedia.
 */
@Entity
@Table(name = "heritage_media")
public class HeritageMedia implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "title", nullable = false)
    private String title;
    
    @NotNull
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
    
    @NotNull
    @Column(name = "media_type", nullable = false)
    private Integer mediaType;
    
    @Lob
    @Column(name = "media_file")
    private byte[] mediaFile;
    
    @Column(name = "media_file_content_type")        private String mediaFileContentType;
    @Column(name = "url_orfile_link")
    private String urlOrfileLink;
    
    @Column(name = "consolidated_tags")
    private String consolidatedTags;
    
    @Column(name = "user_agent")
    private String userAgent;
    
    @Column(name = "upload_time")
    private ZonedDateTime uploadTime;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private HeritageCategory category;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private HeritageLanguage language;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private HeritageGroup group;

    @ManyToOne
    @JoinColumn(name = "heritage_app_id")
    private HeritageApp heritageApp;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

    public HeritageCategory getCategory() {
        return category;
    }

    public void setCategory(HeritageCategory heritageCategory) {
        this.category = heritageCategory;
    }

    public HeritageLanguage getLanguage() {
        return language;
    }

    public void setLanguage(HeritageLanguage heritageLanguage) {
        this.language = heritageLanguage;
    }

    public HeritageGroup getGroup() {
        return group;
    }

    public void setGroup(HeritageGroup heritageGroup) {
        this.group = heritageGroup;
    }

    public HeritageApp getHeritageApp() {
        return heritageApp;
    }

    public void setHeritageApp(HeritageApp heritageApp) {
        this.heritageApp = heritageApp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HeritageMedia heritageMedia = (HeritageMedia) o;
        if(heritageMedia.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, heritageMedia.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageMedia{" +
            "id=" + id +
            ", title='" + title + "'" +
            ", description='" + description + "'" +
            ", address='" + address + "'" +
            ", latitude='" + latitude + "'" +
            ", longitude='" + longitude + "'" +
            ", mediaType='" + mediaType + "'" +
            ", mediaFile='" + mediaFile + "'" +
            ", mediaFileContentType='" + mediaFileContentType + "'" +
            ", urlOrfileLink='" + urlOrfileLink + "'" +
            ", consolidatedTags='" + consolidatedTags + "'" +
            ", userAgent='" + userAgent + "'" +
            ", uploadTime='" + uploadTime + "'" +
            '}';
    }
}
