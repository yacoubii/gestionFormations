package com.example.GestionFormations.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table
//@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class SessionEntity {
    @Id
    @SequenceGenerator(
            name = "session_sequence",
            sequenceName = "session_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "session_sequence"
    )
    private Long id;


    @NotBlank
    @NotNull(message = "lieu cannot be null")
    @Size(min = 2, max = 20, message = "Firstname between 2 and 20 characters")
    private String lieu;

    @NotNull(message = "dateDebut cannot be null")
    private LocalDate dateDeb;

    @NotNull(message = "dateDebut cannot be null")
    private LocalDate dateFin;

    @NotNull(message = "must be higher than 4 ")
    private Integer nbrParticipants;


    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    @JoinColumn(name = "organisme_id")
    OrganismeEntity organisme;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    @JoinColumn(name = "formateur_id")
    FormateurEntity formateur;

    @ManyToMany(mappedBy = "sessions", cascade = CascadeType.REMOVE)
    //@JsonIgnore
    private Set<FormationEntity> formations;

    @ManyToMany(mappedBy = "sessions", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<ParticipantEntity> participants ;

    public SessionEntity() {
    }

    public SessionEntity(Long id, String lieu, LocalDate dateDeb, LocalDate dateFin, OrganismeEntity organisme, FormateurEntity formateur, Set<FormationEntity> formations, Set<ParticipantEntity> participants, Integer nbrParticipants) {
        this.id = id;
        this.lieu = lieu;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.organisme = organisme;
        this.formateur = formateur;
        this.formations = formations;
        this.participants = participants;
        this.nbrParticipants = nbrParticipants;
    }

    public SessionEntity(String lieu, LocalDate dateDeb, LocalDate dateFin, Integer nbrParticipants, OrganismeEntity organisme, FormateurEntity formateur, Set<FormationEntity> formations, Set<ParticipantEntity> participants) {
        this.lieu = lieu;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.nbrParticipants = nbrParticipants;
        this.organisme = organisme;
        this.formateur = formateur;
        this.formations = formations;
        this.participants = participants;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public LocalDate getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(LocalDate dateDeb) {
        this.dateDeb = dateDeb;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public OrganismeEntity getOrganisme() {
        return organisme;
    }

    public void setOrganisme(OrganismeEntity organisme) {
        this.organisme = organisme;
    }

    public FormateurEntity getFormateur() {
        return formateur;
    }

    public void setFormateur(FormateurEntity formateur) {
        this.formateur = formateur;
    }

    //@JsonBackReference
    public Set<FormationEntity> getFormations() {
        return formations;
    }

    public void setFormations(Set<FormationEntity> formations) {
        this.formations = formations;
    }

    //@JsonBackReference
    public Set<ParticipantEntity> getParticipants() {
        return participants;
    }


    public void setParticipants(Set<ParticipantEntity> participants) {
        this.participants = participants;
    }

    public Integer getNbrParticipants() {
        return nbrParticipants;
    }

    public void setNbrParticipants(Integer nbrParticipants) {
        this.nbrParticipants = nbrParticipants;
    }

    @Override
    public String toString() {
        return "SessionEntity{" +
                "id=" + id +
                ", lieu='" + lieu + '\'' +
                ", dateDeb=" + dateDeb +
                ", dateFin=" + dateFin +
                ", organisme=" + organisme +
                ", formateur=" + formateur +
                ", formations=" + formations +
                '}';
    }
}

