package com.example.GestionFormations.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;



@Entity
@Table
public class UserEntity {
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
    private Long code;


    @NotBlank
    @NotNull(message = "Username cannot be null")
    @Size(min = 3, message = "Userename should not be less than 3")
    private String login;

    @NotBlank
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password should not be less than 8")
    private String password;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",joinColumns = {
            @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID") })
    private Set<RoleEntity> roles;

    public UserEntity() {
    }

    public UserEntity(Long code, @NotBlank @NotNull(message = "Username cannot be null") @Size(min = 3, message = "Userename should not be less than 3") String login, @NotBlank @NotNull(message = "Password cannot be null") @Size(min = 8, message = "Password should not be less than 8") String password, Set<RoleEntity> roles) {
        this.code = code;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public UserEntity(@NotBlank @NotNull(message = "Username cannot be null") @Size(min = 3, message = "Userename should not be less than 3") String login, @NotBlank @NotNull(message = "Password cannot be null") @Size(min = 8, message = "Password should not be less than 8") String password, Set<RoleEntity> roles) {
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}

