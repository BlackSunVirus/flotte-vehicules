package controllers;

import exceptions.SaisieInvalideException;
import view.MenuVue;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainController {
    MenuVue menuVue;
    AuthController authC;
    EtatController etatC;
    FlotteController flotteC;
    ReservationController reservationC;

    public MainController(Scanner scan) {
        this.menuVue = new MenuVue(scan);
        //this.authC = new AuthController(scan);
        this.etatC = new EtatController(scan);
        this.flotteC = new FlotteController(scan);
        //this.reservationC = new ReservationController(scan);
    }

    private void afficherMenu() {
        menuVue.affichageMenu();
    }

    public void bouclePrincipale() {
        int choix;
        while (true) {
            try {
                afficherMenu();
                choix = menuVue.saisieInt("Choix : ");
                switch (choix) {
                    case 1:
                        flotteC.afficherFlotte();
                        break;
                    case 2:
                        flotteC.ajouterVehicule();
                        break;
                    case 3:
                        flotteC.supprimerVehicule();
                        break;
                    case 4:
                        flotteC.afficherVehicule();
                        break;
                    case 5:
                        flotteC.trierFlotte();
                        break;
                    case 6:
                        //TODO
                        break;
                    case 7:
                        //TODO
                        break;
                    case 8:
                        //TODO
                        break;
                    case 9:
                        //TODO
                        break;
                    case 0:
                        menuVue.afficherSucces("Au revoir !");
                        System.exit(0);
                        break;
                    default:
                        menuVue.afficherErreur("Choix invalide, veuillez réessayer.");
                }
            } catch (SaisieInvalideException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException ime) {
                menuVue.afficherErreur("Saisie non valide");
            }
        }
    }
}
