package org.janastu.heritageapp.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A HeritageLanguage.
 */
@Entity
@Table(name = "heritage_language")
public class HeritageLanguage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "heritage_language", nullable = false)
    private String heritageLanguage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeritageLanguage() {
        return heritageLanguage;
    }

    public void setHeritageLanguage(String heritageLanguage) {
        this.heritageLanguage = heritageLanguage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HeritageLanguage heritageLanguage = (HeritageLanguage) o;
        return Objects.equals(id, heritageLanguage.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "HeritageLanguage{" +
            "id=" + id +
            ", heritageLanguage='" + heritageLanguage + "'" +
            '}';
    }
}
