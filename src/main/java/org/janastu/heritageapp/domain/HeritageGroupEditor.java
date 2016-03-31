package org.janastu.heritageapp.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HeritageGroupEditor.
 */
@Entity
@Table(name = "heritage_group_editor")
public class HeritageGroupEditor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "about")
    private String about;
    
    @Column(name = "status")
    private Integer status;
    
    @ManyToOne
    @JoinColumn(name = "group_id")
    private HeritageGroup group;

    @ManyToOne
    @JoinColumn(name = "editor_id")
    private User editor;

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

    public HeritageGroup getGroup() {
        return group;
    }

    public void setGroup(HeritageGroup heritageGroup) {
        this.group = heritageGroup;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User user) {
        this.editor = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HeritageGroupEditor heritageGroupEditor = (HeritageGroupEditor) o;
        if(heritageGroupEditor.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, heritageGroupEditor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageGroupEditor{" +
            "id=" + id +
            ", about='" + about + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
