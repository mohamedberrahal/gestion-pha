package com.fsl.ma.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "destination")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Destination implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "population")
    private String population;

    @ManyToOne
    @JsonIgnoreProperties("destinations")
    private Medicament medicament;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPopulation() {
        return population;
    }

    public Destination population(String population) {
        this.population = population;
        return this;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public Destination medicament(Medicament medicament) {
        this.medicament = medicament;
        return this;
    }

    public void setMedicament(Medicament medicament) {
        this.medicament = medicament;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Destination destination = (Destination) o;
        if (destination.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), destination.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Destination{" +
            "id=" + getId() +
            ", population='" + getPopulation() + "'" +
            "}";
    }
}
