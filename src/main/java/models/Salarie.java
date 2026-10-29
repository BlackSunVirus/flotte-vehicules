package models;

import java.sql.Timestamp;
import java.util.HashMap;

public class Salarie {

    private String nom;
    private String prenom;
    private String matricule;
    private HashMap<Timestamp, Vehicule> reservations;

    public Salarie(String nom, String prenom, String matricule) {
        // matricule à 6 chiffres
        if (!matricule.matches("\\d{6}")) {
            throw new IllegalArgumentException("Le matricule doit contenir exactement 6 chiffres");
        }
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.reservations = new HashMap<>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public HashMap<Timestamp, Vehicule> getReservations() {
        return reservations;
    }

    public void setReservations(HashMap<Timestamp, Vehicule> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Salarie{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", matricule='" + matricule + '\'' +
                ", reservations=" + reservations +
                '}';
    }
}
