package com.simulation.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class DetectionId implements Serializable {
    private Long idCapteur;
    private Long idFeuSimule;
}
