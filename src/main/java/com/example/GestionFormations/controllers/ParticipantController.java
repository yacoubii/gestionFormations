package com.example.GestionFormations.controllers;

import com.example.GestionFormations.entities.ParticipantEntity;
import com.example.GestionFormations.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/participant")
public class ParticipantController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService){
        this.participantService=participantService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ParticipantEntity> getParticipants(){
        return participantService.getParticipants();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path = "{participantId}")
    ParticipantEntity getParticipant(@PathVariable Long participantId) {
        return participantService.getParticipant(participantId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ParticipantEntity registerNewParticipant(@Valid @RequestBody ParticipantEntity participant, BindingResult bindingResult){
        participantService.addNewParticipant(participant,bindingResult);
        return participant;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public void DeleteParticipantById(@PathVariable Long id) {
        participantService.DeleteParticipant(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path="{participantId}")
    public void updateParticipant(
            @PathVariable("participantId") Long participantId,
            @RequestBody ParticipantEntity participantUpdate
    ){
        participantService.updateParticipant(participantId, participantUpdate);

    }

    @GetMapping(path="/link/{sessionId}/{participantId}")
    public void linkNewSessionToParticipant(@PathVariable("sessionId") Long sessionId, @PathVariable("participantId") Long participantId){
        participantService.linkNewSessionToParticipant(sessionId, participantId);
    }
}
