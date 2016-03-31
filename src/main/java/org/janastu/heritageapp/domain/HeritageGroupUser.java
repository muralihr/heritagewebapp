package org.janastu.heritageapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HeritageGroupUser.
 */
@Entity
@Table(name = "heritage_group_user")
public class HeritageGroupUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "reason", nullable = false)
    private String reason;
    
    @Column(name = "status")
    private Integer status;
    
    @ManyToOne
    @JoinColumn(name = "group_id")
    private HeritageGroup group;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private User member;

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

    public HeritageGroup getGroup() {
        return group;
    }

    public void setGroup(HeritageGroup heritageGroup) {
        this.group = heritageGroup;
    }

    public User getMember() {
        return member;
    }

    public void setMember(User user) {
        this.member = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HeritageGroupUser heritageGroupUser = (HeritageGroupUser) o;
        if(heritageGroupUser.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, heritageGroupUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageGroupUser{" +
            "id=" + id +
            ", reason='" + reason + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
