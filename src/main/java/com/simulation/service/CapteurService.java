package com.simulation.service;

import com.simulation.model.Capteur;
import com.simulation.repository.CapteurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CapteurService {

    private final CapteurRepository repository;

    public List<Capteur> getAllCapteurs() {
        return repository.findAll();
    }

    public Capteur addCapteur(Capteur capteur) {
        return repository.save(capteur);
    }
}