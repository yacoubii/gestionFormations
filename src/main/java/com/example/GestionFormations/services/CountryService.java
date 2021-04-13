package com.example.GestionFormations.services;

import com.example.GestionFormations.entities.CountryEntity;
import com.example.GestionFormations.entities.ParticipantEntity;
import com.example.GestionFormations.repositories.CountryRepository;
import com.example.GestionFormations.repositories.ParticipantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service

public class CountryService {
    private final CountryRepository countryRepository;
    private final ParticipantRepository participantRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository, ParticipantRepository participantRepository) {
        this.countryRepository = countryRepository;
        this.participantRepository=participantRepository;
    }

    public List<CountryEntity> getCountries() {
        return countryRepository.findAll();

    }

    public void addNewCountry(CountryEntity country) {
        Optional<CountryEntity> countryOptional = countryRepository.findCountryEntityByName(country.getName());
        if (countryOptional.isPresent()){
            throw new IllegalStateException("Name already exists");
        }
        countryRepository.save(country);
    }

    public void deleteCountry(Long countryId) {
        countryRepository.findById(countryId);
        boolean exists= countryRepository.existsById(countryId);
        if(!exists){
            throw new IllegalStateException("Country with id " + countryId + " does not exists");
        }
        countryRepository.deleteById(countryId);
    }


    @Transactional
    public void updateCountry(Long countryId, String name) {
        CountryEntity country = countryRepository.findById(countryId).orElseThrow(()-> new IllegalStateException(
                "Country with id " + countryId + " does not exist"));
        if (name!=null &&
                name.length() > 0 &&
                !Objects.equals(country.getName(), name)){
            country.setName(name);
        }
    }

    public void linkNewParticipantToCountry(Long participantId, Long countryId){
        ParticipantEntity participant = participantRepository.findById(participantId).orElseThrow(()-> new IllegalStateException(
                "participant with id " + participantId + " does not exist"));
        CountryEntity country = countryRepository.findById(countryId).orElseThrow(()-> new IllegalStateException(
                "country with id " + countryId + " does not exist"));
        participant.setCountry(country);
        participantRepository.save(participant);

        Set<ParticipantEntity> participants = country.getParticipants();
        participants.add(participant);
        country.setParticipants(participants);
        countryRepository.save(country);
    }
}
