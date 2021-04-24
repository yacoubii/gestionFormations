package com.example.GestionFormations.repositories;

import com.example.GestionFormations.entities.OrganismeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

    @Repository
    public interface OrganismeRepository extends JpaRepository<OrganismeEntity, Long> {
        Optional<OrganismeEntity> findOrganismeEntityByLibelle(String libelle);

    }
