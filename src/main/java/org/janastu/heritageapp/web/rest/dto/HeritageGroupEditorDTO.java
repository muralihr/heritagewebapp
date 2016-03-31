package org.janastu.heritageapp.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the HeritageGroupEditor entity.
 */
public class HeritageGroupEditorDTO implements Serializable {

    private Long id;

    private String about;


    private Integer status;


    private Long groupId;

    private String groupName;

    private Long editorId;

    private String editorLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getEditorId() {
        return editorId;
    }

    public void setEditorId(Long userId) {
        this.editorId = userId;
    }

    public String getEditorLogin() {
        return editorLogin;
    }

    public void setEditorLogin(String userLogin) {
        this.editorLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HeritageGroupEditorDTO heritageGroupEditorDTO = (HeritageGroupEditorDTO) o;

        if ( ! Objects.equals(id, heritageGroupEditorDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageGroupEditorDTO{" +
            "id=" + id +
            ", about='" + about + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
