package controllers;

import exceptions.RegleGestionException;
import models.Flotte;
import models.Vehicule;
import services.VehiculeService;
import view.vueFlotte;

import java.sql.SQLException;
import java.util.Scanner;

public class FlotteController {

    private VehiculeService vehiculeService;
    private vueFlotte vueFlotte;

    public FlotteController(Scanner scan) {
        this.vehiculeService = new VehiculeService();
        this.vueFlotte = new vueFlotte(scan);
    }

    public void ajouterVehicule() {
        try {
            Vehicule v = vueFlotte.creationVehicule();
            if(v == null) {
                throw new RegleGestionException("Erreur lors de la création du véhicule");
            }

            if(v.getKilometrage() < 0){
                throw new RegleGestionException("Le kilomètrage ne peut pas être négatif");
            }

            vehiculeService.ajouterVehicule(v);
            vueFlotte.afficherSucces("Véhicule ajouté !");
        } catch (RegleGestionException e) {
            vueFlotte.afficherErreur(e.getMessage());
        }
    }

    public void supprimerVehicule() {
        try {
            String immatriculation = vueFlotte.saisieImmatriculation();
            boolean estDisponible = vehiculeService.estDisponible(immatriculation);
            if(!estDisponible){
                vueFlotte.afficherErreur("Le véhicule " +  immatriculation +
                        " est en déplacement. Enregistrer son retour d'abord.");
                return;
            }
            String confirmationSuppression = vueFlotte.saisieConfirmationSuppression(immatriculation);
            if(confirmationSuppression.equalsIgnoreCase("o") ||
            confirmationSuppression.equalsIgnoreCase("oui")){
                vehiculeService.supprimerVehicule(immatriculation);
                vueFlotte.afficherSucces("Véhicule " + immatriculation + " retiré de la flotte.");
                return;
            }
            vueFlotte.afficherSucces("Annulation...");
        } catch (SQLException e) {
            vueFlotte.afficherErreur(e.getMessage());
        }
    }

    public void afficherVehicule() {
        try {
            String immatriculation = vueFlotte.saisieImmatriculation();
            System.out.println(vehiculeService.obtenirParImmat(immatriculation));
        } catch (RegleGestionException e) {
            vueFlotte.afficherErreur(e.getMessage());
        }
    }

    public void afficherFlotte() {
        try {
            Flotte flotteActuelle = vehiculeService.afficherFlotte();
            StringBuilder sb = new StringBuilder();
            if(flotteActuelle == null || flotteActuelle.getVehicules().isEmpty()) {
                sb.append("La flotte est actuellement vide.");

            } else {
                boolean estDisponible;
                String etat = "";
                String separateur = "+----------------+----------------+----------------+" +
                "---------------+---------------+---------------+\n";
                sb.append("FLOTTE - ").append(flotteActuelle.getVehicules().size()).append(" véhicules").append("\n");
                sb.append("\n");
                sb.append(separateur).append("\n");
                sb.append(String.format("| %-16s | %-16s | %-16s | %-16s |", "Immat.", "Marque",
                        "Modèle", "Mise circ.", "Km", "État")).append("\n");
                sb.append(separateur).append("\n");
                for(Vehicule v : flotteActuelle.getVehicules()) {
                    etat = "En déplacement";
                    estDisponible = vehiculeService.estDisponible(v.getImmatriculation());
                    if(estDisponible){
                        etat = "Disponible";
                    }
                    sb.append(String.format("| %-16s | %-16s | %-16s | %-16s |", v.getImmatriculation(), v.getMarque(),
                            v.getModele(), v.getDateMiseEnCirculation(), v.getKilometrage(), etat)).append("\n");
                    System.out.println(v);
                }
                sb.append(separateur).append("\n");
            }
            System.out.println(sb);
        } catch (RegleGestionException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void trierFlotte() {
        int choix = vueFlotte.saisieChoixTrie();

        String trie = "";
        if (choix == 1){
            vehiculeService.trierParMarque();
            trie = "MARQUE PUIS MODÈLE";
        } else if (choix == 2){
            vehiculeService.trierParDate();
            trie = "DATE PLUS ANCIEN D'ABORD";
        } else if (choix == 3){
            vehiculeService.trierParKilometrage();
            trie = "KILOMÉTRAGE DÉCROISSANT";
        }

        //TODO
    }

}
