package com.example.GestionFormations.repositories;

import com.example.GestionFormations.entities.ERole;
import com.example.GestionFormations.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findRoleEntityByName(ERole name) ;
}