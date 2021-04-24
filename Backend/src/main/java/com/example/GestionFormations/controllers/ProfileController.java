package com.example.GestionFormations.controllers;

import com.example.GestionFormations.entities.ProfileEntity;
import com.example.GestionFormations.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<ProfileEntity> getProfiles(){
        return profileService.getProfiles();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public void registerNewProfile(@RequestBody ProfileEntity profile){
        profileService.addNewProfile(profile);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @DeleteMapping(path="{profileId}")
    public void deleteProfile(@PathVariable("profileId") Long profileId){
        profileService.deleteProfile(profileId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path="{profileId}")
    public void updateProfile(
            @PathVariable("profileId") Long profileId,
            @RequestBody ProfileEntity profileUpdate) {
        profileService.updateProfile(profileId, profileUpdate);

    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(path="/link/{participantId}/{profileId}")
    public void linkParticipantToProfile(@PathVariable("participantId") Long participantId, @PathVariable("profileId") Long profileId){
        profileService.linkNewParticipantToProfile(participantId, profileId);
    }

}