package com.example.GestionFormations.repositories;

import com.example.GestionFormations.entities.DomainEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomainRepository extends JpaRepository<DomainEntity, Long> {
    Optional<DomainEntity> findDomainEntityByLibelle(String libelle);
}
