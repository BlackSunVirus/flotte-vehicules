package controllers;

import exceptions.RegleGestionException;
import models.Flotte;
import models.Vehicule;
import services.VehiculeService;
import utils.ControleSaisie;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class FlotteController extends BaseController {

    private VehiculeService vehiculeService;
    private ControleSaisie controleSaisie;

    public FlotteController(Scanner scanner) {
        super(scanner);
        this.vehiculeService = new VehiculeService();
        this.controleSaisie = new ControleSaisie(scanner);
    }

    public void ajouterVehicule() {
        try {
            String immatriculation = saisieString("Immatriculation (AA-123-AA)      : ");
            String marque = saisieString("Marque                           : ");
            String modele = saisieString("Modèle                           : ");
            LocalDate dateMiseEnCirculation = saisieDate("Mise en circulation (jj/mm/aaaa) : ");
            int kilometrage = saisieInt("Kilométrage                      : ");
            if(kilometrage < 0){
                throw new RegleGestionException("Le kilomètrage ne peut pas être négatif");
            }
            Vehicule v = new Vehicule(0, immatriculation, marque, modele, dateMiseEnCirculation, kilometrage);

            if(v == null) {
                throw new RegleGestionException("Erreur lors de la création du véhicule");
            }
            vehiculeService.ajouterVehicule(v);
            System.out.println("Véhicule ajouté !");
        } catch (RegleGestionException e) {
            System.err.println(e.getMessage());
        }
    }

    public void supprimerVehicule() {
        try {
            String immatriculation = saisieString("Immatriculation du véhicule à retirer : ");
            boolean estDisponible = vehiculeService.estDisponible(immatriculation);
            if(!estDisponible){
                System.out.println("Le véhicule " +  immatriculation +
                        " est en déplacement. Enregistrer son retour d'abord.");
                return;
            }
            String confirmationSuppression = saisieString("Confirmer le retrait de " +
                    immatriculation + " ? (o/n) : ");
            if(confirmationSuppression.equalsIgnoreCase("o") ||
            confirmationSuppression.equalsIgnoreCase("oui")){
                vehiculeService.supprimerVehicule(immatriculation);
                System.out.println("Véhicule " + immatriculation + " retiré de la flotte.");
                return;
            }
            System.out.println("Annulation...");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void afficherVehicule() {
        try {
            String immatriculation =  saisieString("Immatriculation (AA-123-AA) : ");
            System.out.println(vehiculeService.obtenirParImmat(immatriculation));
        } catch (RegleGestionException e) {
            System.err.println(e.getMessage());
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
        int choix = saisieInt("1. Par marque, puis modèle\n" +
                "2. Par date de mise en circulation (plus ancien d'abord)\n" +
                "3. Par kilométrage décroissant\n" +
                "Tri : ");
        if (choix == 1){
            vehiculeService.trierParMarque();
        } else if (choix == 2){
            vehiculeService.trierParDate();
        } else if (choix == 3){
            vehiculeService.trierParKilometrage();
        }

        //TODO
    }

}
