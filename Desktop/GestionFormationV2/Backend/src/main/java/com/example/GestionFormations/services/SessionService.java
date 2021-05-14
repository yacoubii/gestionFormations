package com.example.GestionFormations.services;


import com.example.GestionFormations.entities.ParticipantEntity;
import com.example.GestionFormations.entities.SessionEntity;
import com.example.GestionFormations.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service(value = "sessionService")
public class SessionService {
    private final SessionRepository sessionRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<SessionEntity> getSessions() {
        return sessionRepository.findAll();
    }

    public Set<ParticipantEntity> getParticipantsOfSession(Long sessionId) {
        SessionEntity session = getSessions().stream().filter(t -> sessionId.equals(t.getId()))
                .findFirst()
                .orElse(null);
        return session.getParticipants();
    }

    public SessionEntity getSession(Long id) {
        SessionEntity session = getSessions().stream().filter(t -> id.equals(t.getId()))
                .findFirst()
                .orElse(null);
        return session;
    }

    public void addNewSession(SessionEntity session, BindingResult bindingResult) {
        sessionRepository.save(session);
    }

    public void DeleteSession(Long id) {
        Optional<SessionEntity> sessionId = sessionRepository.findById(id);
        if(!sessionId.isPresent()){
            throw new IllegalStateException("session does not exist");
        }
        sessionId.get().setOrganisme(null);
        sessionId.get().setFormateur(null);
        sessionId.get().setFormations(null);

        sessionRepository.deleteById(id);
    }

    public void updateSession(Long sessionId, SessionEntity sessionUpdate) {
        SessionEntity session = sessionRepository.findById(sessionId).orElseThrow(()-> new IllegalStateException(
                "session with id " + sessionId + " does not exist"));

        if (sessionUpdate.getLieu()!=null &&
                session.getLieu().length() > 0 &&
                !Objects.equals(session.getLieu(), sessionUpdate.getLieu())){
            session.setLieu(sessionUpdate.getLieu());
        }

        if (sessionUpdate.getDateDeb()!=null &&
                !Objects.equals(session.getDateDeb(), sessionUpdate.getDateDeb())){
            session.setDateDeb(sessionUpdate.getDateDeb());
        }

        if (sessionUpdate.getDateFin()!=null &&
                !Objects.equals(session.getDateFin(), sessionUpdate.getDateFin())){
            session.setDateFin(sessionUpdate.getDateFin());
        }

        if (sessionUpdate.getOrganisme()!=null &&
                sessionUpdate.getOrganisme().toString().length() > 0 &&
                !Objects.equals(sessionUpdate.getOrganisme(), sessionUpdate.getOrganisme())){
            session.setOrganisme(sessionUpdate.getOrganisme());
        }

        if (sessionUpdate.getFormateur()!=null &&
                sessionUpdate.getFormateur().toString().length() > 0 &&
                !Objects.equals(sessionUpdate.getFormateur(), sessionUpdate.getFormateur())){
            session.setFormateur(sessionUpdate.getFormateur());
        }

        if (sessionUpdate.getFormations()!=null &&
                sessionUpdate.getFormations().toString().length() > 0 &&
                !Objects.equals(session.getFormations(), sessionUpdate.getFormations())){
            session.setFormations(sessionUpdate.getFormations());
        }

        if (sessionUpdate.getNbrParticipants()!=null &&
                sessionUpdate.getNbrParticipants().toString().length() > 0 &&
                !Objects.equals(session.getNbrParticipants(), sessionUpdate.getNbrParticipants())){
            session.setNbrParticipants(sessionUpdate.getNbrParticipants());
        }


        sessionRepository.save(session);
    }

    public void clearFormationsForSessions(Long sessionId){
        SessionEntity session = sessionRepository.findById(sessionId).orElseThrow(()-> new IllegalStateException(
                "sessions with id " + sessionId + " does not exist"));
        session.setFormations(null);
        sessionRepository.save(session);
    }
}
