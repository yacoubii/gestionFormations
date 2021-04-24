package com.example.GestionFormations.services;


import com.example.GestionFormations.entities.DomainEntity;
import com.example.GestionFormations.entities.FormationEntity;
import com.example.GestionFormations.repositories.DomainRepository;
import com.example.GestionFormations.repositories.FormationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service(value = "domainService")
public class DomainService {
    private final DomainRepository domainRepository;
    private final FormationRepository formationRepository;

    @Autowired
    public DomainService(DomainRepository domainRepository, FormationRepository formationRepository) {
        this.domainRepository = domainRepository;
        this.formationRepository = formationRepository;
    }

    public List<DomainEntity> getDomains() {
        return domainRepository.findAll();
    }

    public DomainEntity getDomain(Long id) {
        DomainEntity domain = getDomains().stream().filter(t -> id.equals(t.getId()))
                .findFirst()
                .orElse(null);
        return domain;
    }

    public void addNewDomain(DomainEntity domain) {
        Optional<DomainEntity> domainOptional = domainRepository.findDomainEntityByLibelle(domain.getLibelle());
        if (domainOptional.isPresent()){
            throw new IllegalStateException("Libelle already exists");
        }
        domainRepository.save(domain);
    }

    public void deleteDomain(Long domainId) {
        domainRepository.findById(domainId);
        boolean exists= domainRepository.existsById(domainId);
        if(!exists){
            throw new IllegalStateException("Domain with id " + domainId + " does not exists");
        }
        domainRepository.deleteById(domainId);
    }

    public void updateDomain(Long domainId, DomainEntity domainUpdate) {
        DomainEntity domain = domainRepository.findById(domainId).orElseThrow(()-> new IllegalStateException(
                "domain with id " + domainId + " does not exist"));

        if (domainUpdate.getLibelle()!=null &&
                domain.getLibelle().length() > 0 &&
                !Objects.equals(domain.getLibelle(), domainUpdate.getLibelle())){
            domain.setLibelle(domainUpdate.getLibelle());
        }
        if (domainUpdate.getFormations()!=null &&
                domainUpdate.getFormations().toString().length() > 0 &&
                !Objects.equals(domain.getFormations(), domainUpdate.getFormations())){
            domain.setFormations(domainUpdate.getFormations());
        }
        domainRepository.save(domain);
    }

    public void linkNewFormationToDomain(Long formationId, Long domainId) {
        FormationEntity formation = formationRepository.findById(formationId).orElseThrow(()-> new IllegalStateException(
                "formation with id " + formationId + " does not exist"));
        DomainEntity domain = domainRepository.findById(domainId).orElseThrow(()-> new IllegalStateException(
                "domain with id " + domainId + " does not exist"));
        formation.setDomain(domain);
        formationRepository.save(formation);

        Set<FormationEntity> formations = domain.getFormations();
        formations.add(formation);
        domain.setFormations(formations);
        domainRepository.save(domain);
    }

}