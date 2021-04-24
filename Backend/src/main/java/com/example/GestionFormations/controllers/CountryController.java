package com.example.GestionFormations.controllers;

import com.example.GestionFormations.entities.CountryEntity;
import com.example.GestionFormations.services.CountryService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/country")
@AllArgsConstructor

public class CountryController {

    private final CountryService countryService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<CountryEntity> getCountries(){
        return countryService.getCountries();
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public void registerNewCountry(@RequestBody CountryEntity country){
        countryService.addNewCountry(country);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path="{countryId}")
    public void deleteCountry(@PathVariable("countryId") Long countryId){
        countryService.deleteCountry(countryId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path="{countryId}")
    public void updateCountry(
            @PathVariable("countryId") Long countryId,
            @RequestBody CountryEntity countryUpdate) {
        countryService.updateCountry(countryId, countryUpdate);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(path="/link/{participantId}/{countryId}")
    public void linkParticipantToCountry(@PathVariable("participantId") Long participantId, @PathVariable("countryId") Long countryId){
        countryService.linkNewParticipantToCountry(participantId, countryId);
    }

}