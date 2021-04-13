package com.example.GestionFormations.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class FormateurEntity {
    @Id
    @SequenceGenerator(
            name = "formation_sequence",
            sequenceName = "formation_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "formation_sequence"
    )
    private Long id;


    @NotBlank
    @NotNull(message = "Firstname cannot be null")
    @Size(min = 2, max = 20, message = "Firstname between 2 and 20 characters")
    private String firstName;

    @NotBlank
    @NotNull(message = "Lastname cannot be null")
    @Size(min = 2, max = 20, message = "Lastname between 2 and 20 characters")
    private String lastName;

    @NotBlank
    @NotNull(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank
    @NotNull(message = "Phone number cannot be null")
    @Size(min = 8, max = 15, message = "Email between 8 and 15 characters")
    private String tel;

    @Enumerated(EnumType.STRING)
    private EFormateur type;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    //@JsonIgnore
    @JoinColumn(name="organisme_id")
    OrganismeEntity organisme;


    @OneToMany(mappedBy="formateur",cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<SessionEntity> sessions;

    public FormateurEntity(Long id, String firstName, String lastName, String email, String tel, EFormateur type, OrganismeEntity organisme, Set<SessionEntity> sessions) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tel = tel;
        this.type = type;
        this.organisme = organisme;
        this.sessions = sessions;
    }

    public FormateurEntity(Long id, String firstName, String lastName, String tel, EFormateur type, OrganismeEntity organisme) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.tel = tel;
        this.type = type;
        this.organisme = organisme;
    }

    public FormateurEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public EFormateur getType() {
        return type;
    }

    public void setType(EFormateur type) {
        this.type = type;
    }

    public OrganismeEntity getOrganisme() {
        return organisme;
    }

    public void setOrganisme(OrganismeEntity organisme) {
        this.organisme = organisme;
    }

    //@JsonBackReference
    public Set<SessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(Set<SessionEntity> sessions) {
        this.sessions = sessions;
    }

    @Override
    public String toString() {
        return "FormateurEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", type='" + type + '\'' +
                ", organisme=" + organisme +
                ", sessions=" + sessions +
                '}';
    }
}