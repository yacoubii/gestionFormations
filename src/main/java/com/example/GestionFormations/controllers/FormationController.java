package com.example.GestionFormations.controllers;



import com.example.GestionFormations.entities.FormationEntity;
import com.example.GestionFormations.services.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/formation")
public class FormationController {

    private final FormationService formationService;

    @Autowired
    public FormationController(FormationService formationService){
        this.formationService=formationService;
    }

    @GetMapping
    public List<FormationEntity> getFormations(){
        return formationService.getFormations();
    }


    @GetMapping(path = "{formationId}")
    FormationEntity getFormation(@PathVariable Long formationId) {
        return formationService.getFormation(formationId);
    }

    @PostMapping
    public FormationEntity registerNewFormation(@Valid @RequestBody FormationEntity formation, BindingResult bindingResult){
        formationService.addNewFormation(formation,bindingResult);
        return formation;
    }


    @DeleteMapping("{id}")
    public void DeleteFormationById(@PathVariable Long id) {
        formationService.DeleteFormation(id);
    }


    @PutMapping(path="{formationId}")
    public void updateFormation(
            @PathVariable("formationId") Long formationId,
            @RequestBody FormationEntity formationUpdate
    ){
        formationService.updateFormation(formationId, formationUpdate);
    }

    @GetMapping(path="/link/{sessionId}/{formationId}")
    public void linkNewSessionToFormation(@PathVariable("sessionId") Long sessionId, @PathVariable("formationId") Long formationId){
        formationService.linkNewSessionToFormation(sessionId, formationId);
    }

}