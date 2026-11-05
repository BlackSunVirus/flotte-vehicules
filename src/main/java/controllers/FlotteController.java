package controllers;

import exceptions.RegleGestionException;
import models.Flotte;
import models.Vehicule;
import services.PdfService;
import services.VehiculeService;
import view.FlotteVue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FlotteController {

    private VehiculeService vehiculeService;
    private FlotteVue vueFlotte;
    private PdfService pdfService;

    public FlotteController(Scanner scan) {
        this.vehiculeService = new VehiculeService();
        this.vueFlotte = new FlotteVue(scan);
        this.pdfService = new PdfService("etat-flotte.pdf");
    }

    public void etatFlottePdf(){
        try {
            Flotte flotteActuelle = vehiculeService.afficherFlotte();
            Map<String, Boolean> disponibilites = new HashMap<>();

            // check la disponibilité des véhicules
            if(flotteActuelle != null) {
                for(Vehicule v : flotteActuelle.getVehicules()) {
                    boolean dispo = vehiculeService.estDisponible(v.getImmatriculation());
                    disponibilites.put(v.getImmatriculation(), dispo);
                }
            }
            pdfService.exporterFlottePdf(vueFlotte.exportTableauFlotte(flotteActuelle,disponibilites));
        } catch (RegleGestionException e) {
            System.err.println(e.getMessage());
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }


    }

    public void ajouterVehicule() {
        try {
            Vehicule v = vueFlotte.creationVehicule();
            if(v == null) {
                throw new RegleGestionException("Erreur lors de la création du véhicule");
            }
            Vehicule vExistant = vehiculeService.obtenirParImmat(v.getImmatriculation());
            if(vExistant != null) {
                throw new RegleGestionException("Erreur : le véhicule existe déjà");
            }
            vehiculeService.ajouterVehicule(v);
            vueFlotte.afficherSucces("Demande d'ajout enregistrée ! Le traitement se fait en arrière plan");
        } catch (RegleGestionException | IllegalArgumentException | SQLException e) {
            vueFlotte.afficherErreur(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void supprimerVehicule() {
        try {
            String immatriculation = vueFlotte.saisieString("Immatriculation du véhicule à retirer :");
            boolean estDisponible = vehiculeService.estDisponible(immatriculation);
            if(!estDisponible){
                vueFlotte.afficherErreur("Le véhicule " +  immatriculation +
                        " est en déplacement. Enregistrer son retour d'abord.");
                return;
            }
            String confirmationSuppression = vueFlotte.saisieString("Confirmer le retrait de " +
                    immatriculation + " ? (o/n) : ");
            if(confirmationSuppression.equalsIgnoreCase("o") ||
            confirmationSuppression.equalsIgnoreCase("oui")){
                vehiculeService.supprimerVehicule(immatriculation);
                vueFlotte.afficherSucces("La demande pour retirer le véhicule " + immatriculation +
                        " est enregistrée. Le traitement se fait en arrière plan");
                return;
            }
            vueFlotte.afficherSucces("Annulation...");
        } catch (SQLException e) {
            vueFlotte.afficherErreur(e.getMessage());
        }
    }

    public void afficherVehicule() {
        try {
            String immatriculation = vueFlotte.saisieString("Immatriculation : ");
            vueFlotte.afficherSucces(vehiculeService.obtenirParImmat(immatriculation).toString());
        } catch (RegleGestionException e) {
            vueFlotte.afficherErreur(e.getMessage());
        }
    }

    public void afficherFlotte() {
        try {
            Flotte flotteActuelle = vehiculeService.afficherFlotte();
            Map<String, Boolean> disponibilites = new HashMap<>();

            // check la disponibilité des véhicules
            if(flotteActuelle != null) {
                for(Vehicule v : flotteActuelle.getVehicules()) {
                    boolean dispo = vehiculeService.estDisponible(v.getImmatriculation());
                    disponibilites.put(v.getImmatriculation(), dispo);
                }
            }

            // envoie de la flotte avec états à la vue
            vueFlotte.afficherTableauFlotte(flotteActuelle, disponibilites);

        } catch (RegleGestionException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void trierFlotte() {
        try {
            int choix = vueFlotte.saisieInt("1. Par marque, puis modèle\n" +
                    "2. Par date de mise en circulation (plus ancien d'abord)\n" +
                    "3. Par kilométrage décroissant\n" +
                    "Tri : ");
            if (choix > 3 || choix < 1) {
                throw new RegleGestionException("Erreur : Le choix n'est pas valide !");
            }

            vueFlotte.affichageTrie(choix, vehiculeService.afficherFlotte());
        } catch (RegleGestionException e) {
            vueFlotte.afficherErreur(e.getMessage());
        }

    }

}
