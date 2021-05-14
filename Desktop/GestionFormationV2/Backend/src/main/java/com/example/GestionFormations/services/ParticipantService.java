package com.example.GestionFormations.services;

import com.example.GestionFormations.entities.ParticipantEntity;
import com.example.GestionFormations.entities.SessionEntity;
import com.example.GestionFormations.repositories.ParticipantRepository;
import com.example.GestionFormations.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
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
        participantId.get().setSessions(null);
        participantId.get().setCountry(null);
        participantRepository.deleteById(id);
    }

    public void updateParticipant(Long participantId, ParticipantEntity participantUpdate) {
        System.out.println("=======================");
        System.out.println(participantUpdate);
        ParticipantEntity participant = participantRepository.findById(participantId).orElseThrow(()-> new IllegalStateException(
                "participant with id " + participantId + " does not exist"));

        /*participant.setFirstName(participantUpdate.getFirstName());
        participant.setLastName(participantUpdate.getLastName());
        participant.setEmail(participantUpdate.getEmail());
        participant.setTel(participantUpdate.getTel());
        participant.setType(participantUpdate.getType());*/

        if (participantUpdate.getFirstName()!=null && !participantUpdate.getFirstName().isEmpty() && !participantUpdate.getFirstName().isBlank()){
            participant.setFirstName(participantUpdate.getFirstName());
        }
        if (participantUpdate.getLastName()!=null && !participantUpdate.getLastName().isEmpty() && !participantUpdate.getLastName().isBlank()){
            participant.setLastName(participantUpdate.getLastName());
        }

        if (participantUpdate.getEmail()!=null && !participantUpdate.getEmail().isEmpty() && !participantUpdate.getEmail().isBlank()){
            participant.setEmail(participantUpdate.getEmail());
        }

        if (participantUpdate.getTel()!=null && !participantUpdate.getEmail().isEmpty() && !participantUpdate.getEmail().isBlank()){
            participant.setTel(participantUpdate.getTel());
        }

        if (participantUpdate.getType()!=null && !participantUpdate.getType().toString().isEmpty() && !participantUpdate.getType().toString().isBlank() ){
            participant.setType(participantUpdate.getType());
        }

        if (participantUpdate.getProfile()!=null){
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

    public void clearSessionsForParticipant(Long participantId){
        ParticipantEntity participant = participantRepository.findById(participantId).orElseThrow(()-> new IllegalStateException(
                "participant with id " + participantId + " does not exist"));
        Set<SessionEntity> sessions = participant.getSessions();
        sessions.clear();
        participant.setSessions(sessions);
        participantRepository.save(participant);
    }


}
