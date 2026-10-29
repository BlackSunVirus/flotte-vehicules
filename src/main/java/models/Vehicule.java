package models;

import java.time.LocalDate;

public class Vehicule {
    private int id;
    private String immatriculation;
    private String marque;
    private String modele;
    private LocalDate dateMiseEnCirculation;
    private int kilometrage;

    public Vehicule(int id, String immatriculation, String marque, String modele, LocalDate dateMiseEnCirculation, int kilometrage) {

        // check le format de l'immatriculation --> "AB-123-CD"
        String regex = "[A-Z]{2}-\\d{3}-[A-Z]{2}$";
        if(!immatriculation.matches(regex))
            throw new IllegalArgumentException("immatriculation invalide");
        if(kilometrage < 0)
            throw new IllegalArgumentException("kilometrage < 0");
        this.id = id;
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.miseEnCirculation = miseEnCirculation;
        this.kilometrage = kilometrage;

    }
    public Vehicule(String immatriculation, String marque, String modele, LocalDate miseEnCirculation, int kilometrage) {

        // check le format de l'immatriculation --> "AB-123-CD"
        String regex = "[A-Z]{2}-\\d{3}-[A-Z]{2}$";
        if(!immatriculation.matches(regex))
            throw new IllegalArgumentException("immatriculation invalide");
        if(kilometrage < 0)
            throw new IllegalArgumentException("kilometrage < 0");
        this.immatriculation = immatriculation;
        this.marque = marque;
        this.modele = modele;
        this.dateMiseEnCirculation = dateMiseEnCirculation;
        this.kilometrage = kilometrage;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public LocalDate getDateMiseEnCirculation() {
        return dateMiseEnCirculation;
    }

    public void setDateMiseEnCirculation(LocalDate miseEnCirculation) {
        this.dateMiseEnCirculation = miseEnCirculation;
    }

    public int getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(int kilometrage) {
        this.kilometrage = kilometrage;
    }

    @Override
    public String toString() {
        return "Vehicule{" +
                "id=" + id +
                ", immatriculation='" + immatriculation + '\'' +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", dateMiseEnCirculation=" + dateMiseEnCirculation +
                ", kilometrage=" + kilometrage +
                '}';
    }
}
