package com.fsl.ma.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * The Medicament entity.
 */
@ApiModel(description = "The Medicament entity.")
@Entity
@Table(name = "medicament")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Medicament implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "nom")
    private String nom;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "discription")
    private String discription;

    @Column(name = "prix")
    private Long prix;

    @Column(name = "date_expiraction")
    private Instant dateExpiraction;

    @ManyToOne
    @JsonIgnoreProperties("medicaments")
    private Maladie maladie;

    @OneToMany(mappedBy = "medicament")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Effet> effets = new HashSet<>();
    @OneToMany(mappedBy = "medicament")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Destination> destinations = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Medicament nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLibelle() {
        return libelle;
    }

    public Medicament libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDiscription() {
        return discription;
    }

    public Medicament discription(String discription) {
        this.discription = discription;
        return this;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Long getPrix() {
        return prix;
    }

    public Medicament prix(Long prix) {
        this.prix = prix;
        return this;
    }

    public void setPrix(Long prix) {
        this.prix = prix;
    }

    public Instant getDateExpiraction() {
        return dateExpiraction;
    }

    public Medicament dateExpiraction(Instant dateExpiraction) {
        this.dateExpiraction = dateExpiraction;
        return this;
    }

    public void setDateExpiraction(Instant dateExpiraction) {
        this.dateExpiraction = dateExpiraction;
    }

    public Maladie getMaladie() {
        return maladie;
    }

    public Medicament maladie(Maladie maladie) {
        this.maladie = maladie;
        return this;
    }

    public void setMaladie(Maladie maladie) {
        this.maladie = maladie;
    }

    public Set<Effet> getEffets() {
        return effets;
    }

    public Medicament effets(Set<Effet> effets) {
        this.effets = effets;
        return this;
    }

    public Medicament addEffet(Effet effet) {
        this.effets.add(effet);
        effet.setMedicament(this);
        return this;
    }

    public Medicament removeEffet(Effet effet) {
        this.effets.remove(effet);
        effet.setMedicament(null);
        return this;
    }

    public void setEffets(Set<Effet> effets) {
        this.effets = effets;
    }

    public Set<Destination> getDestinations() {
        return destinations;
    }

    public Medicament destinations(Set<Destination> destinations) {
        this.destinations = destinations;
        return this;
    }

    public Medicament addDestination(Destination destination) {
        this.destinations.add(destination);
        destination.setMedicament(this);
        return this;
    }

    public Medicament removeDestination(Destination destination) {
        this.destinations.remove(destination);
        destination.setMedicament(null);
        return this;
    }

    public void setDestinations(Set<Destination> destinations) {
        this.destinations = destinations;
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
        Medicament medicament = (Medicament) o;
        if (medicament.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), medicament.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Medicament{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", libelle='" + getLibelle() + "'" +
            ", discription='" + getDiscription() + "'" +
            ", prix=" + getPrix() +
            ", dateExpiraction='" + getDateExpiraction() + "'" +
            "}";
    }
}
