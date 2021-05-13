package com.example.GestionFormations.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class OrganismeEntity {
    @Id
    @SequenceGenerator(
            name = "organisme_sequence",
            sequenceName = "organisme_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "organisme_sequence"
    )

    private Long id;
    private String libelle;

    @OneToMany(mappedBy="organisme",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<FormateurEntity> formateurs;


    @OneToMany(mappedBy="organisme",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<SessionEntity> sessions;


    public OrganismeEntity(Long id, String libelle, Set<FormateurEntity> formateurs, Set<SessionEntity> sessions) {
        this.id = id;
        this.libelle = libelle;
        this.formateurs = formateurs;
        this.sessions = sessions;
    }

    public Set<FormateurEntity> getFormateurs() {
        return formateurs;
    }

    //@JsonBackReference
    public void setFormateurs(Set<FormateurEntity> formateurs) {
        this.formateurs = formateurs;
    }

    public OrganismeEntity() {
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
    public Set<SessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(Set<SessionEntity> sessions) {
        this.sessions = sessions;
    }
}
