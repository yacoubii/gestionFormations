package com.example.GestionFormations.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
//@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class ProfileEntity {
    @Id
    @SequenceGenerator(
            name = "profile_sequence",
            sequenceName = "profile_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "profile_sequence"
    )
    private Long id;
    private String libelle;

    @OneToMany(mappedBy="profile")
    @JsonIgnore
    private Set<ParticipantEntity> participants;

    public ProfileEntity(String libelle) {
        this.libelle = libelle;
    }

    public ProfileEntity(Long id, String libelle, Set<ParticipantEntity> participants) {
        this.id = id;
        this.libelle = libelle;
        this.participants = participants;
    }

    public ProfileEntity() {
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

    public Set<ParticipantEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<ParticipantEntity> participants) {
        this.participants = participants;
    }
}


