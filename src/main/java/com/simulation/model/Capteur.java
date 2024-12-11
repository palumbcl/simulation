package com.simulation.model;

import com.simulation.model.enums.Etat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Capteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "coordonnee_x")
    private double coordonneeX;
    @Column(nullable = false, name = "coordonnee_y")
    private double coordonneeY;
    @Column(nullable = false)
    private double temperature;
    @Column(nullable = false)
    private int portee = 100;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Etat etat;
}
