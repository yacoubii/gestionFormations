package com.example.GestionFormations.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
//@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class CountryEntity {
    @Id
    @SequenceGenerator(
            name = "country_sequence",
            sequenceName = "country_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "country_sequence"
    )
    private Long id;
    private String name;

    @Lob
    private String flag;

    @OneToMany(mappedBy="country")
    @JsonIgnore
    private Set<ParticipantEntity> participants;


    public CountryEntity(String name, String flag) {
        this.name = name;
        this.flag = flag;
    }


    public CountryEntity(Long id, String name, Set<ParticipantEntity> participants, String flag) {
        this.id = id;
        this.name = name;
        this.participants = participants;
        this.flag = flag;
    }

    public CountryEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    //@JsonBackReference
    public Set<ParticipantEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<ParticipantEntity> participants) {
        this.participants = participants;
    }
}