package com.example.GestionFormations.controllers;

import com.example.GestionFormations.entities.ParticipantEntity;
import com.example.GestionFormations.entities.SessionEntity;
import com.example.GestionFormations.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1/session")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService){
        this.sessionService=sessionService;
    }

    @GetMapping(path = "/participants/{sessionId}")
    public Set<ParticipantEntity> getParticipantsOfSessions(@PathVariable Long sessionId){
        return sessionService.getParticipantsOfSession(sessionId);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public List<SessionEntity> getsessions(){
        return sessionService.getSessions();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(path = "{sessionId}")
    SessionEntity getSession(@PathVariable Long sessionId) {
        return sessionService.getSession(sessionId);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public SessionEntity registerNewSession(@Valid @RequestBody SessionEntity session, BindingResult bindingResult){
        sessionService.addNewSession(session,bindingResult);
        return session;
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping("{id}")
    public void DeleteSessionById(@PathVariable Long id) {
        sessionService.DeleteSession(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping(path="{sessionId}")
    public void updateSession(
            @PathVariable("sessionId") Long sessionId,
            @RequestBody SessionEntity sessionUpdate
    ){
        sessionService.updateSession(sessionId, sessionUpdate);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(path="/clear/{sessionId}")
    public void clearFormationsForSessions(@PathVariable("sessionId") Long sessionId){
        sessionService.clearFormationsForSessions(sessionId);
    }
}