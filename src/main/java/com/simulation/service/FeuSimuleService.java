package com.simulation.service;

import com.simulation.model.Capteur;
import com.simulation.model.Detection;
import com.simulation.model.FeuSimule;
import com.simulation.model.enums.Statut;
import com.simulation.repository.CapteurRepository;
import com.simulation.repository.DetectionRepository;
import com.simulation.repository.FeuSimuleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class FeuSimuleService {
    @Autowired
    private RestTemplate restTemplate;

    private final FeuSimuleRepository repository;
    private final DetectionRepository detectionRepository;
    private final CapteurRepository capteurRepository;
    private final CapteurService capteurService;

    public List<FeuSimule> getAllFeuSimules() {
        return repository.findAll();
    }

    public FeuSimule addFeuSimule(FeuSimule feuSimule) {
        return repository.save(feuSimule);
    }

    public FeuSimule genererFeuAleatoire() {
        Random random = new Random();

        // Générer des coordonnées aléatoires dans la zone Lyon/Villeurbanne
        double coordonneeX = 4.810 + (4.890 - 4.810) * random.nextDouble();
        double coordonneeY = 45.730 + (45.800 - 45.730) * random.nextDouble();

        // Générer une intensité aléatoire (exemple : entre 0 et 100)
        int intensite = random.nextInt(101);

        // Créer un feu avec les valeurs générées
        FeuSimule feuSimule = new FeuSimule();
        feuSimule.setCoordonneeX(coordonneeX);
        feuSimule.setCoordonneeY(coordonneeY);
        feuSimule.setIntensite(intensite);
        feuSimule.setStatut(Statut.ACTIF);
        feuSimule.setDateApparition(LocalDateTime.now());

        // Sauvegarder le feu
        FeuSimule feu = repository.save(feuSimule);

        // Mettre à jour les capteurs à proximité
        mettreAJourCapteurs(feu);
        // Mettre à jour les détections à proximité
        mettreAJourDetections(feu);

        return feu;
    }

    public List<FeuSimule> genererFeuxAleatoires(int nbFeux) {
        for (int i = 0; i < nbFeux; i++) {
            genererFeuAleatoire();
        }
        return getAllFeuSimules();
    }

    private void mettreAJourCapteurs(FeuSimule feuSimule) {
        List<Capteur> capteurs = capteurService.getAllCapteurs();

        capteurs.parallelStream().forEach(capteur -> {
            double distance = calculerDistance(
                    capteur.getCoordonneeX(),
                    capteur.getCoordonneeY(),
                    feuSimule.getCoordonneeX(),
                    feuSimule.getCoordonneeY()
            );

            // Si le capteur est dans la portée du feu
            if (distance <= capteur.getPortee()) {
                // Augmenter la température du capteur proportionnellement à la proximité
                double temperatureMax = feuSimule.getIntensite() * 9 + 100;
                double temperature = Math.max(100, temperatureMax - (distance / capteur.getPortee()) * (temperatureMax - 100));
                capteur.setTemperature(temperature);
                capteurRepository.save(capteur);
            }
        });

        // Envoyer la requête POST
        String url = "http://10.56.0.247:8080/api/data";
        try {
            restTemplate.postForObject(url, capteurs, Void.class);
            System.out.println("Requête API envoyée avec succès.");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de la requête API : " + e.getMessage());
        }
    }

    private void mettreAJourDetections(FeuSimule feuSimule){
        List<Capteur> capteurs = capteurRepository.findAll();

        for (Capteur capteur : capteurs) {
            double distance = calculerDistance(
                    capteur.getCoordonneeX(),
                    capteur.getCoordonneeY(),
                    feuSimule.getCoordonneeX(),
                    feuSimule.getCoordonneeY()
            );

            if (distance <= capteur.getPortee()) {

                // Créer une nouvelle détection
                Detection detection = new Detection();
                detection.setIdCapteur(capteur.getId());
                detection.setIdFeuSimule(feuSimule.getId());
                detection.setTemperature(capteur.getTemperature());
                detection.setDateDetection(java.time.LocalDateTime.now());

                detectionRepository.save(detection);
            }
        }
    }

    private double calculerDistance(double x1, double y1, double x2, double y2) {
        // Distance approximative en mètres en utilisant Pythagore (2D)
        double dx = (x2 - x1) * 111_000; // Conversion degrés -> mètres
        double dy = (y2 - y1) * 111_000;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
