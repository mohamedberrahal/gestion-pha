package com.fsl.ma.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Maladie.
 */
@Entity
@Table(name = "maladie")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Maladie implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "maladie_name", nullable = false)
    private String maladieName;

    /**
     * A relationship
     */
    @ApiModelProperty(value = "A relationship")
    @OneToMany(mappedBy = "maladie")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Medicament> medicaments = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaladieName() {
        return maladieName;
    }

    public Maladie maladieName(String maladieName) {
        this.maladieName = maladieName;
        return this;
    }

    public void setMaladieName(String maladieName) {
        this.maladieName = maladieName;
    }

    public Set<Medicament> getMedicaments() {
        return medicaments;
    }

    public Maladie medicaments(Set<Medicament> medicaments) {
        this.medicaments = medicaments;
        return this;
    }

    public Maladie addMedicament(Medicament medicament) {
        this.medicaments.add(medicament);
        medicament.setMaladie(this);
        return this;
    }

    public Maladie removeMedicament(Medicament medicament) {
        this.medicaments.remove(medicament);
        medicament.setMaladie(null);
        return this;
    }

    public void setMedicaments(Set<Medicament> medicaments) {
        this.medicaments = medicaments;
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
        Maladie maladie = (Maladie) o;
        if (maladie.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), maladie.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Maladie{" +
            "id=" + getId() +
            ", maladieName='" + getMaladieName() + "'" +
            "}";
    }
}
