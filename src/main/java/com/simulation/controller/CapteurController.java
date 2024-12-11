package com.simulation.controller;

import com.simulation.model.Capteur;
import com.simulation.service.CapteurService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/capteurs")
public class CapteurController {
    private final CapteurService service;

    @GetMapping
    public List<Capteur> getAllCapteurs() {
        return service.getAllCapteurs();
    }

    @PostMapping
    public Capteur addCapteur(@RequestBody Capteur capteur) {
        return service.addCapteur(capteur);
    }
}
