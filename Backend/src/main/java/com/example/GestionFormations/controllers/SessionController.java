package com.example.GestionFormations.controllers;

import com.example.GestionFormations.entities.SessionEntity;
import com.example.GestionFormations.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/session")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService){
        this.sessionService=sessionService;
    }

    @GetMapping
    public List<SessionEntity> getsessions(){
        return sessionService.getSessions();
    }


    @GetMapping(path = "{sessionId}")
    SessionEntity getSession(@PathVariable Long sessionId) {
        return sessionService.getSession(sessionId);
    }

    @PostMapping
    public SessionEntity registerNewSession(@Valid @RequestBody SessionEntity session, BindingResult bindingResult){
        sessionService.addNewSession(session,bindingResult);
        return session;
    }


    @DeleteMapping("{id}")
    public void DeleteSessionById(@PathVariable Long id) {
        sessionService.DeleteSession(id);
    }


    @PutMapping(path="{sessionId}")
    public void updateSession(
            @PathVariable("sessionId") Long sessionId,
            @RequestBody SessionEntity sessionUpdate
    ){
        sessionService.updateSession(sessionId, sessionUpdate);
    }
}