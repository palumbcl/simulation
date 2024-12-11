package com.simulation.repository;

import com.simulation.model.Capteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapteurRepository extends JpaRepository<Capteur, Long> {
}
