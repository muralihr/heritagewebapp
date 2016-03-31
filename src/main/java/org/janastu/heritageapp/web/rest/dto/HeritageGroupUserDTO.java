package org.janastu.heritageapp.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the HeritageGroupUser entity.
 */
public class HeritageGroupUserDTO implements Serializable {

    private Long id;

    @NotNull
    private String reason;


    private Integer status;


    private Long groupId;

    private String groupName;

    private Long memberId;

    private String memberLogin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long userId) {
        this.memberId = userId;
    }

    public String getMemberLogin() {
        return memberLogin;
    }

    public void setMemberLogin(String userLogin) {
        this.memberLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HeritageGroupUserDTO heritageGroupUserDTO = (HeritageGroupUserDTO) o;

        if ( ! Objects.equals(id, heritageGroupUserDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageGroupUserDTO{" +
            "id=" + id +
            ", reason='" + reason + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
