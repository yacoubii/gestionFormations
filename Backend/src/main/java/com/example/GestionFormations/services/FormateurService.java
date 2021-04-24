package com.example.GestionFormations.services;

import com.example.GestionFormations.entities.FormateurEntity;
import com.example.GestionFormations.entities.SessionEntity;
import com.example.GestionFormations.repositories.FormateurRepository;
import com.example.GestionFormations.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service(value = "formateurService")

public class FormateurService {

    private final FormateurRepository formateurRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public FormateurService(FormateurRepository formateurRepository, SessionRepository sessionRepository) {
        this.formateurRepository = formateurRepository;
        this.sessionRepository = sessionRepository;
    }
    public List<FormateurEntity> getFormateurs() {
        return formateurRepository.findAll();
    }

    public void DeleteFormateur(Long id) {
        Optional<FormateurEntity> formateurId = formateurRepository.findById(id);
        if(!formateurId.isPresent()){
            throw new IllegalStateException("formateur does not exist");
        }
        formateurId.get().setOrganisme(null);
        formateurRepository.deleteById(id);
    }

    public void addNewFormateur(FormateurEntity formateur, BindingResult bindingResult) {
        Optional<FormateurEntity> formateurOptional = formateurRepository.findFormateurEntityByEmail(formateur.getEmail());
        if (formateurOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        if(bindingResult.hasErrors()){
            throw new IllegalStateException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        formateurRepository.save(formateur);
    }

    public FormateurEntity getFormateur(Long id) {
        FormateurEntity formateur = getFormateurs().stream().filter(t -> id.equals(t.getId()))
                .findFirst()
                .orElse(null);
        return formateur;
    }

    public void updateFormateur(Long formateurId, FormateurEntity formateurUpdate) {
        FormateurEntity formateur = formateurRepository.findById(formateurId).orElseThrow(()-> new IllegalStateException(
                "formateur with id " + formateurId + " does not exist"));

        if (formateurUpdate.getFirstName()!=null &&
                formateur.getFirstName().length() > 0 &&
                !Objects.equals(formateur.getFirstName(), formateurUpdate.getFirstName())){
            formateur.setFirstName(formateurUpdate.getFirstName());
        }
        if (formateurUpdate.getLastName()!=null &&
                formateur.getLastName().length() > 0 &&
                !Objects.equals(formateur.getLastName(), formateurUpdate.getLastName())){
            formateur.setLastName(formateurUpdate.getLastName());
        }

        if (formateurUpdate.getEmail()!=null &&
                formateur.getEmail().length() > 0 &&
                !Objects.equals(formateur.getEmail(), formateurUpdate.getEmail())){
            formateur.setEmail(formateurUpdate.getEmail());
        }

        if (formateurUpdate.getTel()!=null &&
                formateurUpdate.getTel().length() > 0 &&
                !Objects.equals(formateur.getTel(), formateurUpdate.getTel())){
            formateur.setTel(formateurUpdate.getTel());
        }

        if (formateurUpdate.getType()!=null &&
                formateurUpdate.getType().toString().length() > 0 &&
                !Objects.equals(formateur.getType(), formateurUpdate.getType())){
            formateur.setType(formateurUpdate.getType());
        }

        if (formateurUpdate.getOrganisme()!=null &&
                formateurUpdate.getOrganisme().toString().length() > 0 &&
                !Objects.equals(formateurUpdate.getOrganisme(), formateurUpdate.getOrganisme())){
            formateur.setOrganisme(formateurUpdate.getOrganisme());
        }

        formateurRepository.save(formateur);

    }

    public void linkNewSessionToFormateur(Long sessionId, Long formateurId) {
        SessionEntity session = sessionRepository.findById(sessionId).orElseThrow(()-> new IllegalStateException(
                "session with id " + sessionId + " does not exist"));
        FormateurEntity formateur = formateurRepository.findById(formateurId).orElseThrow(()-> new IllegalStateException(
                "formateur with id " + formateurId + " does not exist"));
        session.setFormateur(formateur);
        sessionRepository.save(session);

        Set<SessionEntity> sessions = formateur.getSessions();
        sessions.add(session);
        formateur.setSessions(sessions);
        formateurRepository.save(formateur);

    }

}
