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
public class ParticipantEntity {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
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

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone number cannot be null")
    @Size(min = 8, max = 15, message = "Email between 8 and 15 characters")
    private String tel;


    @Enumerated(EnumType.STRING)
    private EParticipant type;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="profile_id")
    ProfileEntity profile;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="country_id")
    CountryEntity country;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="participant_session",
                    joinColumns = @JoinColumn(name="participantEntity_id"), inverseJoinColumns = @JoinColumn(name="sessionEntity_id"))
    private Set<SessionEntity> sessions;


    public ParticipantEntity(Long id, @NotBlank @NotNull(message = "Firstname cannot be null") @Size(min = 2, max = 20, message = "Firstname between 2 and 20 characters") String firstName, @NotBlank @NotNull(message = "Lastname cannot be null") @Size(min = 2, max = 20, message = "Lastname between 2 and 20 characters") String lastName, @NotBlank @NotNull(message = "Email cannot be null") @Email(message = "Email should be valid") String email, @NotBlank @NotNull(message = "Phone number cannot be null") @Size(min = 8, max = 15, message = "Email between 8 and 15 characters") String tel, @NotBlank @NotNull(message = "type cannot be null") EParticipant type, ProfileEntity profile, Set<SessionEntity> sessions) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tel = tel;
        this.type = type;
        this.profile = profile;
        this.sessions = sessions;
    }

    public ParticipantEntity() {
    }

    public ParticipantEntity(String firstName, String lastName, String email, String tel, EParticipant type, ProfileEntity profile, CountryEntity country, Set<SessionEntity> sessions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tel = tel;
        this.type = type;
        this.profile = profile;
        this.country = country;
        this.sessions = sessions;
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

    public EParticipant getType() {
        return type;
    }

    public void setType(EParticipant type) {
        this.type = type;
    }

    //@JsonManagedReference
    public ProfileEntity getProfile() {
        return profile;
    }

    //@JsonBackReference
    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

    //@JsonManagedReference
    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }


    public Set<SessionEntity> getSessions() {
        return sessions;
    }

    public void setSessions(Set<SessionEntity> sessions) {
        this.sessions = sessions;
    }

    @Override
    public String toString() {
        return "ParticipantEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", type='" + type + '\'' +
                ", profile=" + profile +
                ", country=" + country +
                '}';
    }
}
