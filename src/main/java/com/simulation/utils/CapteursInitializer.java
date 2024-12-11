package com.simulation.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/*
* Script pour générer le fichier XML d'initialisation des capteurs pour Liquibase.
* */

public class CapteursInitializer {
    public static void main(String[] args) {
        // Zone géographique : Lyon/Villeurbanne
        double minLat = 45.730; // Latitude minimale
        double maxLat = 45.800; // Latitude maximale
        double minLon = 4.810;  // Longitude minimale
        double maxLon = 4.890;  // Longitude maximale
        double step = 0.001;    // Pas de grille (environ 1 km)

        // Chemin de sortie pour le fichier généré
        String outputFile = "src/main/resources/db/changelog/002-insert-capteurs.xml";

        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<databaseChangeLog xmlns=\"http://www.liquibase.org/xml/ns/dbchangelog\"\n");
            writer.write("                 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n");
            writer.write("                 xsi:schemaLocation=\"http://www.liquibase.org/xml/ns/dbchangelog\n");
            writer.write("                                     http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-3.8.xsd\">\n");
            writer.write("    <changeSet id=\"1-add-capteurs\" author=\"simulation-team\">\n");

            Random random = new Random();
            int id = 1;

            // Génération des capteurs en grille
            for (double lat = minLat; lat <= maxLat; lat += step) {
                for (double lon = minLon; lon <= maxLon; lon += step) {
                    // Générer une température initiale aléatoire entre 20.0 et 30.0 degrés
                    double temperature = 20.0 + (30.0 - 20.0) * random.nextDouble();
                    int portee = 100; // Portée par défaut de 100 mètres

                    writer.write("        <insert tableName=\"capteur\">\n");
                    writer.write("            <column name=\"id\" value=\"" + id + "\"/>\n");
                    writer.write("            <column name=\"coordonnee_X\" value=\"" + lon + "\"/>\n");
                    writer.write("            <column name=\"coordonnee_Y\" value=\"" + lat + "\"/>\n");
                    writer.write("            <column name=\"etat\" value=\"FONCTIONNEL\"/>\n");
                    writer.write("            <column name=\"temperature\" value=\"" + BigDecimal.valueOf(temperature).setScale(2, RoundingMode.HALF_UP) + "\"/>\n");
                    writer.write("            <column name=\"portee\" value=\"" + portee + "\"/>\n");
                    writer.write("        </insert>\n");
                    id++;
                }
            }

            writer.write("    </changeSet>\n");
            writer.write("</databaseChangeLog>\n");
            System.out.println("Fichier 002-insert-capteurs.xml généré avec succès dans : " + outputFile);
        } catch (IOException e) {
            System.err.println("Erreur lors de la génération du fichier XML : " + e.getMessage());
        }
    }
}
