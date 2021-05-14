package com.example.GestionFormations.services;

import com.example.GestionFormations.entities.ParticipantEntity;
import com.example.GestionFormations.entities.ProfileEntity;
import com.example.GestionFormations.repositories.ParticipantRepository;
import com.example.GestionFormations.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, ParticipantRepository participantRepository) {
        this.profileRepository = profileRepository;
        this.participantRepository = participantRepository;
    }

    public List<ProfileEntity> getProfiles(){
        return profileRepository.findAll();
    }

    public void addNewProfile(ProfileEntity profile) {
        Optional<ProfileEntity> profileOptional = profileRepository.findProfileEntityByLibelle(profile.getLibelle());
        if (profileOptional.isPresent()){
            throw new IllegalStateException("Libelle already exists");
        }
        profileRepository.save(profile);
    }

    public void deleteProfile(Long profileId) {
        profileRepository.findById(profileId);
        boolean exists= profileRepository.existsById(profileId);
        if(!exists){
            throw new IllegalStateException("Profile with id " + profileId + " does not exists");
        }
        Optional<ProfileEntity> profile=profileRepository.findById(profileId);
        profile.get().getParticipants().forEach(p->{
            p.setProfile(null);
        });
        profile.get().setParticipants(null);
        profileRepository.deleteById(profileId);
    }
    @Transactional
    public void updateProfile(Long profileId, ProfileEntity profileUpdate) {
        ProfileEntity profile = profileRepository.findById(profileId).orElseThrow(()-> new IllegalStateException(
                "profile with id " + profileId + " does not exist"));
        if (profileUpdate.getLibelle()!=null &&
                profileUpdate.getLibelle().length() > 0 &&
                !Objects.equals(profile.getLibelle(), profileUpdate.getLibelle())){
            profile.setLibelle(profileUpdate.getLibelle());
        }

        profileRepository.save(profile);

    }


    public void linkNewParticipantToProfile(Long participantId, Long profileId){
        ParticipantEntity participant = participantRepository.findById(participantId).orElseThrow(()-> new IllegalStateException(
                "participant with id " + participantId + " does not exist"));
        ProfileEntity profile = profileRepository.findById(profileId).orElseThrow(()-> new IllegalStateException(
                "profile with id " + profileId + " does not exist"));
        participant.setProfile(profile);
        participantRepository.save(participant);

        Set<ParticipantEntity> participants = profile.getParticipants();
        participants.add(participant);
        profile.setParticipants(participants);
        profileRepository.save(profile);
    }
}