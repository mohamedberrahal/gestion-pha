package com.fsl.ma.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Effet.
 */
@Entity
@Table(name = "effet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Effet implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "discription")
    private String discription;

    @ManyToOne
    @JsonIgnoreProperties("effets")
    private Medicament medicament;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Effet libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDiscription() {
        return discription;
    }

    public Effet discription(String discription) {
        this.discription = discription;
        return this;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Medicament getMedicament() {
        return medicament;
    }

    public Effet medicament(Medicament medicament) {
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
        Effet effet = (Effet) o;
        if (effet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), effet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Effet{" +
            "id=" + getId() +
            ", libelle='" + getLibelle() + "'" +
            ", discription='" + getDiscription() + "'" +
            "}";
    }
}
