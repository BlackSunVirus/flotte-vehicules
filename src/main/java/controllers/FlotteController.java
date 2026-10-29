package controllers;

import exceptions.RegleGestionException;
import models.Vehicule;
import services.VehiculeService;
import utils.ControleSaisie;

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
        } catch (RegleGestionException e) {
            System.err.println(e.getMessage());
        }
    }
}
