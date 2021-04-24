package com.example.GestionFormations.controllers;

import com.example.GestionFormations.entities.FormationEntity;
import com.example.GestionFormations.entities.OrganismeEntity;
import com.example.GestionFormations.entities.ProfileEntity;
import com.example.GestionFormations.services.OrganismeService;
import com.example.GestionFormations.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/organisme")
public class OrganismeController {
    private final OrganismeService organismeService;

    @Autowired
    public OrganismeController(OrganismeService organismeService) {
        this.organismeService = organismeService;
    }


    @GetMapping
    public List<OrganismeEntity> getOrganismes(){
        return organismeService.getOrganismes();
    }

    @PostMapping
    public void registerNewOrganisme(@RequestBody OrganismeEntity organisme){
        organismeService.addNewOrganisme(organisme);
    }


    @DeleteMapping(path="{organismeId}")
    public void deleteOrganisme(@PathVariable("organismeId") Long organismeId){
        organismeService.deleteOrganisme(organismeId);
    }


    @PutMapping(path="{organismeId}")
    public void updateOrganisme(
            @PathVariable("organismeId") Long organismeId,
            @RequestBody OrganismeEntity organismeUpdate
    ){
        organismeService.updateOrganisme(organismeId, organismeUpdate);
    }


    @GetMapping(path="/link1/{formateurId}/{organismeId}")
    public void linkNewFormateurToOrganisme(@PathVariable("formateurId") Long formateurId, @PathVariable("organismeId") Long organismeId){
        organismeService.linkNewFormateurToOrganisme(formateurId, organismeId);
    }


    @GetMapping(path="/link2/{sessionId}/{organismeId}")
    public void linkNewSessionToOrganisme(@PathVariable("sessionId") Long sessionId, @PathVariable("organismeId") Long organismeId){
        organismeService.linkNewSessionToOrganisme(sessionId, organismeId);
    }

}
