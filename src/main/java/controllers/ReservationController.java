package controllers;

import exceptions.RegleGestionException;
import services.ReservationService;
import view.BaseVue;

import java.util.Scanner;

public class ReservationController {

    private final ReservationService reservationService;
    public ReservationController(Scanner scanner, ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    /** Renvoie le message à afficher par la vue. */
    public String enregistrerRetour(String immatriculation, int kmRetour) {
        try {
            reservationService.enregistrerRetour(immatriculation, kmRetour);
            return "Retour enregistré.";
        } catch (RegleGestionException e) {
            return "Refusé : " + e.getMessage();
        }
    }
}
