package com.example.GestionFormations.repositories;

import com.example.GestionFormations.entities.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<ParticipantEntity, Long> {
    Optional<ParticipantEntity> findParticipantEntityByEmail(String email);

}
