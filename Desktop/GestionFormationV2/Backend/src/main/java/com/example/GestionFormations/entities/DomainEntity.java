package com.example.GestionFormations.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class DomainEntity {
    @Id
    @SequenceGenerator(
            name = "domain_sequence",
            sequenceName = "domain_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "domain_sequence"
    )
    private Long id;
    private String libelle;

    @OneToMany(mappedBy="domain")
    @JsonIgnore
    private Set<FormationEntity> formations;

    public DomainEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    //@JsonBackReference
    public Set<FormationEntity> getFormations() {
        return formations;
    }

    public void setFormations(Set<FormationEntity> formations) {
        this.formations = formations;
    }

    public DomainEntity(String libelle) {
        this.libelle = libelle;
    }

    public DomainEntity(Long id, String libelle, Set<FormationEntity> formations) {
        this.id = id;
        this.libelle = libelle;
        this.formations = formations;
    }

    @Override
    public String toString() {
        return "DomainEntity{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", formations=" + formations +
                '}';
    }
}