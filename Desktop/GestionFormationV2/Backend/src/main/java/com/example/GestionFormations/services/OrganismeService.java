package com.example.GestionFormations.services;

import com.example.GestionFormations.entities.FormateurEntity;
import com.example.GestionFormations.entities.OrganismeEntity;
import com.example.GestionFormations.entities.SessionEntity;
import com.example.GestionFormations.repositories.FormateurRepository;
import com.example.GestionFormations.repositories.OrganismeRepository;

import com.example.GestionFormations.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service(value = "organismeService")
public class OrganismeService
{
    private final OrganismeRepository organismeRepository;
    private final FormateurRepository formateurRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public OrganismeService(OrganismeRepository organismeRepository, FormateurRepository formateurRepository, SessionRepository sessionRepository) {
        this.organismeRepository = organismeRepository;
        this.formateurRepository = formateurRepository;
        this.sessionRepository = sessionRepository;
    }

    public List<OrganismeEntity> getOrganismes() {
        return organismeRepository.findAll();
    }

    public void addNewOrganisme(OrganismeEntity organisme) {
        Optional<OrganismeEntity> organismeOptional = organismeRepository.findOrganismeEntityByLibelle(organisme.getLibelle());
        if (organismeOptional.isPresent()){
            throw new IllegalStateException("Libelle already exists");
        }
        organismeRepository.save(organisme);
    }


    public void deleteOrganisme(Long organismeId) {
        organismeRepository.findById(organismeId);
        boolean exists= organismeRepository.existsById(organismeId);
        if(!exists){
            throw new IllegalStateException("Profile with id " + organismeId + " does not exists");
        }
        Optional<OrganismeEntity> organisme = organismeRepository.findById(organismeId);
        organisme.get().getFormateurs().forEach(f->{
            f.setOrganisme(null);
        });
        organisme.get().setFormateurs(null);
        organisme.get().getSessions().forEach(s->{
            s.setOrganisme(null);
        });
        organisme.get().setSessions(null);
        organismeRepository.deleteById(organismeId);
    }

    @Transactional
    public void updateOrganisme(Long organismeId, OrganismeEntity organismeUpdate) {
        OrganismeEntity organisme = organismeRepository.findById(organismeId).orElseThrow(()-> new IllegalStateException(
                "organisme with id " + organismeId + " does not exist"));

        if (organismeUpdate.getLibelle()!=null &&
                organisme.getLibelle().length() > 0 &&
                !Objects.equals(organisme.getLibelle(), organismeUpdate.getLibelle())){
            organisme.setLibelle(organismeUpdate.getLibelle());
        }
        if (organismeUpdate.getFormateurs()!=null &&
                organismeUpdate.getFormateurs().toString().length() > 0 &&
                !Objects.equals(organisme.getFormateurs(), organismeUpdate.getFormateurs())){
            organisme.setFormateurs(organismeUpdate.getFormateurs());
        }
    }

    public void linkNewFormateurToOrganisme(Long formateurId, Long organismeId) {
        FormateurEntity formateur = formateurRepository.findById(formateurId).orElseThrow(()-> new IllegalStateException(
                "formateur with id " + formateurId + " does not exist"));
        OrganismeEntity organisme = organismeRepository.findById(organismeId).orElseThrow(()-> new IllegalStateException(
                "organisme with id " + organismeId + " does not exist"));
        formateur.setOrganisme(organisme);
        formateurRepository.save(formateur);

        Set<FormateurEntity> formateurs = organisme.getFormateurs();
        formateurs.add(formateur);
        organisme.setFormateurs(formateurs);
        organismeRepository.save(organisme);
    }

    public void linkNewSessionToOrganisme(Long sessionId, Long organismeId) {
        SessionEntity session = sessionRepository.findById(sessionId).orElseThrow(()-> new IllegalStateException(
                "session with id " + sessionId + " does not exist"));
        OrganismeEntity organisme = organismeRepository.findById(organismeId).orElseThrow(()-> new IllegalStateException(
                "organisme with id " + organismeId + " does not exist"));
        session.setOrganisme(organisme);
        sessionRepository.save(session);

        Set<SessionEntity> sessions = organisme.getSessions();
        sessions.add(session);
        organisme.setSessions(sessions);
        organismeRepository.save(organisme);
    }
}
