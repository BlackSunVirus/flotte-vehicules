package view;

import exceptions.RegleGestionException;
import models.Flotte;
import models.Vehicule;

import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

public class FlotteVue extends BaseVue {

    public FlotteVue(Scanner scan) {
        super(scan);
    }

    public Vehicule creationVehicule() {
        try {
            String immatriculation = saisieString("Immatriculation (AA-123-AA)      : ");
            String marque = saisieString("Marque                           : ");
            String modele = saisieString("Modèle                           : ");
            LocalDate dateMiseEnCirculation = saisieDate("Mise en circulation (jj/mm/aaaa) : ");
            int kilometrage = saisieInt("Kilométrage                      : ");
            return new Vehicule(0, immatriculation.toUpperCase(), marque, modele, dateMiseEnCirculation, kilometrage);

        } catch(RegleGestionException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public void afficherTableauFlotte(Flotte flotteActuelle, Map<String, Boolean> disponibilites) {
        if(flotteActuelle == null || flotteActuelle.getVehicules().isEmpty()) {
            System.out.println("La flotte est actuellement vide.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        String separateur = "+---------------+---------------+---------------+" +
                "---------------+---------------+----------------+";
        sb.append("FLOTTE - ").append(flotteActuelle.getVehicules().size()).append(" véhicules").append("\n");
        sb.append(separateur).append("\n");
        sb.append(String.format("| %-13s | %-13s | %-13s | %-13s | %-13s | %-13s  |", "Immat.", "Marque",
                "Modèle", "Mise circ.", "Km", "État")).append("\n");
        sb.append(separateur).append("\n");
        for (Vehicule v : flotteActuelle.getVehicules()) {
            boolean estDisponible = disponibilites.get(v.getImmatriculation());

            // vue prend dans le hashmap fourni par le controller
            estDisponible = disponibilites.getOrDefault(v.getImmatriculation(), false);
            // if dispo --> Disponible / else En déplacement
            String etat = estDisponible ? "Disponible" : "En déplacement";
            sb.append(String.format("| %-13s | %-13s | %-13s | %-13s | %-13s | %-13s |", v.getImmatriculation(), v.getMarque(),
                    v.getModele(), v.getDateMiseEnCirculation(), v.getKilometrage(), etat)).append("\n");
        }
        sb.append(separateur).append("\n");
        System.out.println(sb);
    }

    public void affichageTrie(int choix, Flotte flotte) {
        StringBuilder sb = new StringBuilder();
        if (choix == 1){
            sb.append("FLOTTE TRIÉE PAR MARQUE PUIS MODÈLE").append("\n");
        } else if (choix == 2){
            sb.append("FLOTTE TRIÉE PAR DATE PLUS ANCIEN D'ABORD").append("\n");
        } else if (choix == 3){
            sb.append("FLOTTE TRIÉE PAR KILOMÉTRAGE DÉCROISSANT").append("\n");
        }
        for(Vehicule v : flotte.getVehicules()){
            System.out.println("  " + v.getImmatriculation() + " " + v.getMarque() + " " + v.getModele());
        }
    }

}
