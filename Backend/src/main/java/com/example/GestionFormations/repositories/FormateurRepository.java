package com.example.GestionFormations.repositories;

import com.example.GestionFormations.entities.FormateurEntity;
import com.example.GestionFormations.entities.ParticipantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FormateurRepository extends JpaRepository<FormateurEntity, Long> {

    Optional<FormateurEntity> findFormateurEntityByEmail(String email);
    Optional<FormateurEntity> findById (Long id);

}

