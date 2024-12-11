package com.simulation.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@IdClass(DetectionId.class)
public class Detection {
    @Id
    private Long idCapteur;
    @Id
    private Long idFeuSimule;
    private double temperature;
    private LocalDateTime dateDetection;
}
