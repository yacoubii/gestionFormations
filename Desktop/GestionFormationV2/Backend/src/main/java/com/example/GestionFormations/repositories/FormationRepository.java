package com.example.GestionFormations.repositories;

import com.example.GestionFormations.entities.FormationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormationRepository extends JpaRepository<FormationEntity, Long> {
    Optional<FormationEntity> findFormationEntityByTitre(String titre);
}
