package com.simulation.model;

import com.simulation.model.enums.Statut;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Data
public class FeuSimule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, name = "coordonnee_x")
    private double coordonneeX;
    @Column(nullable = false, name = "coordonnee_y")
    private double coordonneeY;
    @Column(nullable = false)
    private int intensite;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Statut statut;
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false, updatable = false, name = "date_apparition")
    private LocalDateTime dateApparition;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "date_extinction")
    private LocalDateTime dateExtinction;
}
