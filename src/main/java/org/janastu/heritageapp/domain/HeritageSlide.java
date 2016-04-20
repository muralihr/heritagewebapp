package org.janastu.heritageapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HeritageSlide.
 */
@Entity
@Table(name = "heritage_slide")
public class HeritageSlide implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "index_val", nullable = false)
    private Integer indexVal;
    
    @Column(name = "notes")
    private String notes;
    
    @ManyToOne
    @JoinColumn(name = "heritage_media_id")
    private HeritageMedia heritageMedia;

    @ManyToOne
    @JoinColumn(name = "heritage_walk_id")
    private HeritageWalk heritageWalk;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

    public HeritageMedia getHeritageMedia() {
        return heritageMedia;
    }

    public void setHeritageMedia(HeritageMedia heritageMedia) {
        this.heritageMedia = heritageMedia;
    }

    public HeritageWalk getHeritageWalk() {
        return heritageWalk;
    }

    public void setHeritageWalk(HeritageWalk heritageWalk) {
        this.heritageWalk = heritageWalk;
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
        HeritageSlide heritageSlide = (HeritageSlide) o;
        if(heritageSlide.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, heritageSlide.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageSlide{" +
            "id=" + id +
            ", indexVal='" + indexVal + "'" +
            ", notes='" + notes + "'" +
            '}';
    }
}
