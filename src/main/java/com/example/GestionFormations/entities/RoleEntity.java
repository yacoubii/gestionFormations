package com.example.GestionFormations.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;


@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERole name;

    public RoleEntity() {
    }

    public RoleEntity(ERole name) {
        this.name = name;
    }

    public RoleEntity(long id, ERole name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

}