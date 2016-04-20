package org.janastu.heritageapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HeritageWalk.
 */
@Entity
@Table(name = "heritage_walk")
public class HeritageWalk implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "background")
    private String background;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getBackground() {
        return background;
    }
    
    public void setBackground(String background) {
        this.background = background;
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
        HeritageWalk heritageWalk = (HeritageWalk) o;
        if(heritageWalk.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, heritageWalk.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageWalk{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", background='" + background + "'" +
            '}';
    }
}
