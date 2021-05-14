package com.example.GestionFormations.services;


import com.example.GestionFormations.entities.FormationEntity;
import com.example.GestionFormations.entities.SessionEntity;
import com.example.GestionFormations.repositories.FormationRepository;
import com.example.GestionFormations.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service(value = "formationService")
public class FormationService {

    private final FormationRepository formationRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public FormationService(FormationRepository formationRepository, SessionRepository sessionRepository) {
        this.formationRepository = formationRepository;
        this.sessionRepository=sessionRepository;
    }

    public List<FormationEntity> getFormations() {
        return formationRepository.findAll();
    }


    public FormationEntity getFormation(Long id) {
        FormationEntity formation = getFormations().stream().filter(t -> id.equals(t.getId()))
                .findFirst()
                .orElse(null);
        return formation;
    }

    public void DeleteFormation(Long id) {
        Optional<FormationEntity> formationId = formationRepository.findById(id);
        if(!formationId.isPresent()){
            throw new IllegalStateException("formation does not exist");
        }
        formationId.get().getSessions().forEach(sessionEntity -> {
            sessionEntity.setFormations(null);
        });
        formationId.get().setSessions(null);

        formationId.get().getDomain().setFormations(null);
        formationId.get().setDomain(null);
        formationRepository.deleteById(id);
    }

    public void addNewFormation(FormationEntity formation, BindingResult bindingResult) {
        Optional<FormationEntity>formationOptional = formationRepository.findFormationEntityByTitre(formation.getTitre());
        if (formationOptional.isPresent()){
            throw new IllegalStateException("title is taken");
        }
        if(bindingResult.hasErrors()){
            throw new IllegalStateException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        formationRepository.save(formation);
    }

    public void updateFormation(Long formationId, FormationEntity formationUpdate) {
        FormationEntity formation = formationRepository.findById(formationId).orElseThrow(()-> new IllegalStateException(
                "formation with id " + formationId + " does not exist"));

        if (formationUpdate.getTitre()!=null &&
                formation.getTitre().length() > 0 &&
                !Objects.equals(formation.getTitre(), formationUpdate.getTitre())){
            formation.setTitre(formationUpdate.getTitre());
        }
        if (formationUpdate.getType()!=null &&
                formation.getType().toString().length() > 0 &&
                !Objects.equals(formation.getType(), formationUpdate.getType())){
            formation.setType(formationUpdate.getType());
        }

        if (formationUpdate.getDuree()!=null &&
                !Objects.equals(formation.getDuree(), formationUpdate.getDuree())){
            formation.setDuree(formationUpdate.getDuree());
        }

        if (formationUpdate.getBudget()!=null &&
                !Objects.equals(formation.getBudget(), formationUpdate.getBudget())){
            formation.setBudget(formationUpdate.getBudget());
        }

        if (formationUpdate.getNb_session()!=0 &&
                !Objects.equals(formation.getNb_session(), formationUpdate.getNb_session())){
            formation.setNb_session(formationUpdate.getNb_session());
        }


        if (formationUpdate.getDomain()!=null &&
                formationUpdate.getDomain().toString().length() > 0 &&
                !Objects.equals(formationUpdate.getDomain(), formationUpdate.getDomain())){
            formation.setDomain(formationUpdate.getDomain());
        }

        if (formationUpdate.getSessions()!=null &&
                formationUpdate.getSessions().toString().length() > 0 &&
                !Objects.equals(formation.getSessions(), formationUpdate.getSessions())){
            formation.setSessions(formationUpdate.getSessions());
        }
        formationRepository.save(formation);
    }

    public void linkNewSessionToFormation(Long sessionId, Long formationId) {
        SessionEntity session = sessionRepository.findById(sessionId).orElseThrow(()-> new IllegalStateException(
                "session with id " + sessionId + " does not exist"));
        FormationEntity formation = formationRepository.findById(formationId).orElseThrow(()-> new IllegalStateException(
                "formation with id " + formationId + " does not exist"));
        Set<FormationEntity> formations = session.getFormations();
        formations.add(formation);
        session.setFormations(formations);
        sessionRepository.save(session);

        Set<SessionEntity> sessions = formation.getSessions();
        sessions.add(session);
        formation.setSessions(sessions);
        formationRepository.save(formation);
    }
    }

