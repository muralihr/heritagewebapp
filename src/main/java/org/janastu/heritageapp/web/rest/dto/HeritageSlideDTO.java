package org.janastu.heritageapp.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the HeritageSlide entity.
 */
public class HeritageSlideDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer indexVal;


    private String notes;


    private Long heritageMediaId;

    private String heritageMediaTitle;

    private Long heritageWalkId;

    private String heritageWalkName;

    private Long userId;

    private String userLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getIndexVal() {
        return indexVal;
    }

    public void setIndexVal(Integer indexVal) {
        this.indexVal = indexVal;
    }
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Long getHeritageMediaId() {
        return heritageMediaId;
    }

    public void setHeritageMediaId(Long heritageMediaId) {
        this.heritageMediaId = heritageMediaId;
    }

    public String getHeritageMediaTitle() {
        return heritageMediaTitle;
    }

    public void setHeritageMediaTitle(String heritageMediaTitle) {
        this.heritageMediaTitle = heritageMediaTitle;
    }

    public Long getHeritageWalkId() {
        return heritageWalkId;
    }

    public void setHeritageWalkId(Long heritageWalkId) {
        this.heritageWalkId = heritageWalkId;
    }

    public String getHeritageWalkName() {
        return heritageWalkName;
    }

    public void setHeritageWalkName(String heritageWalkName) {
        this.heritageWalkName = heritageWalkName;
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

        HeritageSlideDTO heritageSlideDTO = (HeritageSlideDTO) o;

        if ( ! Objects.equals(id, heritageSlideDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageSlideDTO{" +
            "id=" + id +
            ", indexVal='" + indexVal + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
