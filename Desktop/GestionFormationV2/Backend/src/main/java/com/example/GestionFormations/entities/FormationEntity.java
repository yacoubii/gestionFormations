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
import java.util.Set;

@Entity
//@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class FormationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @NotNull(message = "titre cannot be null")
    @Size(min = 2, max = 20, message = "Titre between 2 and 20 characters")
    private String titre;


    private EFormation type;


    @NotNull(message = "duree cannot be null")
    private Integer duree;


    @NotNull(message = "budget cannot be null")
    private Float budget;

    @NotNull(message = "number of sessions cannot be null")
    private Integer nb_session;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "formation_session",joinColumns = {
            @JoinColumn(name = "formation_id") }, inverseJoinColumns = {
            @JoinColumn(name = "session_id") })
    @JsonIgnore
    private Set<SessionEntity> sessions;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    @JoinColumn(name="domain_id")
    DomainEntity domain;

    public FormationEntity() {
    }

    public FormationEntity(long id, String titre, EFormation type, Integer duree, Float budget, int nb_session, Set<SessionEntity> sessions, DomainEntity domain) {
        this.id = id;
        this.titre = titre;
        this.type = type;
        this.duree = duree;
        this.budget = budget;
        this.nb_session = nb_session;
        this.sessions = sessions;
        this.domain = domain;
    }

    public FormationEntity(String titre, EFormation type, Integer duree, Float budget, Integer nb_session, Set<SessionEntity> sessions, DomainEntity domain) {
        this.titre = titre;
        this.type = type;
        this.duree = duree;
        this.budget = budget;
        this.nb_session = nb_session;
        this.sessions = sessions;
        this.domain = domain;
    }

    public Integer getNb_session() {
        return nb_session;
    }

    public void setNb_session(Integer nb_session) {
        this.nb_session = nb_session;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public EFormation getType() {
        return type;
    }

    public void setType(EFormation type) {
        this.type = type;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Float getBudget() {
        return budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    //@JsonBackReference
    public Set<SessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(Set<SessionEntity> sessions) {
        this.sessions = sessions;
    }

    public DomainEntity getDomain() {
        return domain;
    }

    public void setDomain(DomainEntity domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "FormationEntity{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", type='" + type + '\'' +
                ", duree=" + duree +
                ", budget=" + budget +
                ", sessions=" + sessions +
                ", domain=" + domain +
                '}';
    }
}