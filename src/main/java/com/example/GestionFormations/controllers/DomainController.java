package com.example.GestionFormations.controllers;

import com.example.GestionFormations.entities.DomainEntity;
import com.example.GestionFormations.services.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/domain")
public class DomainController {
    private final DomainService domainService;

    @Autowired
    public DomainController(DomainService domainService) {
        this.domainService = domainService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<DomainEntity> getDomains(){
        return domainService.getDomains();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void registerNewDomain(@RequestBody DomainEntity domain){
        domainService.addNewDomain(domain);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path="{domainId}")
    public void deleteDomain(@PathVariable("domainId") Long domainId){
        domainService.deleteDomain(domainId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path="{domainId}")
    public void updateDomain(
            @PathVariable("domainId") Long domainId,
            @RequestBody DomainEntity domainUpdate
    ){
        domainService.updateDomain(domainId, domainUpdate);
    }

    @GetMapping(path="/link/{formationId}/{domainId}")
    public void linkFormationToDomain(@PathVariable("formationId") Long formationId, @PathVariable("domainId") Long domainId){
        domainService.linkNewFormationToDomain(formationId, domainId);
    }
}