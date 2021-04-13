package com.example.GestionFormations.services;

import com.example.GestionFormations.entities.ParticipantEntity;
import com.example.GestionFormations.entities.SessionEntity;
import com.example.GestionFormations.repositories.ParticipantRepository;
import com.example.GestionFormations.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service(value = "participantService")
public class ParticipantService {
    private final ParticipantRepository participantRepository;
    private final SessionRepository sessionRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository, SessionRepository sessionRepository) {
        this.participantRepository = participantRepository;
        this.sessionRepository = sessionRepository;
    }
    public List<ParticipantEntity> getParticipants(){
        return participantRepository.findAll();
    }

    public ParticipantEntity getParticipant(Long id) {
        ParticipantEntity participant = getParticipants().stream().filter(t -> id.equals(t.getId()))
                .findFirst()
                .orElse(null);
        return participant;
    }

    public void addNewParticipant(ParticipantEntity participant, BindingResult bindingResult) {
        Optional<ParticipantEntity> participantOptional = participantRepository.findParticipantEntityByEmail(participant.getEmail());
        if (participantOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        if(bindingResult.hasErrors()){
            throw new IllegalStateException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        participantRepository.save(participant);
    }

    public void DeleteParticipant(Long id){
        Optional<ParticipantEntity> participantId = participantRepository.findById(id);
        if(!participantId.isPresent()){
            throw new IllegalStateException("participant does not exist");
        }
        participantId.get().setProfile(null);
        participantRepository.deleteById(id);
    }

    public void updateParticipant(Long participantId, ParticipantEntity participantUpdate) {
        ParticipantEntity participant = participantRepository.findById(participantId).orElseThrow(()-> new IllegalStateException(
                "participant with id " + participantId + " does not exist"));

        if (participantUpdate.getFirstName()!=null &&
                participant.getFirstName().length() > 0 &&
                !Objects.equals(participant.getFirstName(), participantUpdate.getFirstName())){
            participant.setFirstName(participantUpdate.getFirstName());
        }
        if (participantUpdate.getLastName()!=null &&
                participant.getLastName().length() > 0 &&
                !Objects.equals(participant.getLastName(), participantUpdate.getLastName())){
            participant.setLastName(participantUpdate.getLastName());
        }

        if (participantUpdate.getEmail()!=null &&
                participant.getEmail().length() > 0 &&
                !Objects.equals(participant.getEmail(), participantUpdate.getEmail())){
            participant.setEmail(participantUpdate.getEmail());
        }

        if (participantUpdate.getTel()!=null &&
                participantUpdate.getTel().length() > 0 &&
                !Objects.equals(participant.getTel(), participantUpdate.getTel())){
            participant.setTel(participantUpdate.getTel());
        }

        if (participantUpdate.getType()!=null &&
                participantUpdate.getType().toString().length() > 0 &&
                !Objects.equals(participant.getType(), participantUpdate.getType())){
            participant.setType(participantUpdate.getType());
        }

        if (participantUpdate.getProfile()!=null &&
                participantUpdate.getProfile().toString().length() > 0 &&
                !Objects.equals(participantUpdate.getProfile(), participantUpdate.getProfile())){
            participant.setProfile(participantUpdate.getProfile());
        }

        participantRepository.save(participant);

    }

    public void linkNewSessionToParticipant(Long sessionId, Long participantId) {
        SessionEntity session = sessionRepository.findById(sessionId).orElseThrow(()-> new IllegalStateException(
                "session with id " + sessionId + " does not exist"));
        ParticipantEntity participant = participantRepository.findById(participantId).orElseThrow(()-> new IllegalStateException(
                "participant with id " + participantId + " does not exist"));

        Set<ParticipantEntity> participants = session.getParticipants();
        participants.add(participant);
        session.setParticipants(participants);
        sessionRepository.save(session);

        Set<SessionEntity> sessions = participant.getSessions();
        sessions.add(session);
        participant.setSessions(sessions);
        participantRepository.save(participant);
    }


}
