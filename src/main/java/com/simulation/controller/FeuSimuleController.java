package com.simulation.controller;

import com.simulation.model.FeuSimule;
import com.simulation.service.FeuSimuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feu")
public class FeuSimuleController {

    private final FeuSimuleService service;

    @GetMapping
    public List<FeuSimule> getAllFeuSimules() {
        return service.getAllFeuSimules();
    }

    @PostMapping
    public FeuSimule addFeuSimule(@RequestBody FeuSimule feuSimule) {
        return service.addFeuSimule(feuSimule);
    }

    @PostMapping("/genererFeu")
    public FeuSimule genererFeuAleatoire() {
        return service.genererFeuAleatoire();
    }

    @PostMapping("/genererDesFeux")
    public List<FeuSimule> genererFeuxAleatoires(@RequestBody int nbFeux) {
        return service.genererFeuxAleatoires(nbFeux);
    }
}
