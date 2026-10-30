import controllers.FlotteController;
import controllers.MainController;
import exceptions.SaisieInvalideException;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static void main() throws ClassNotFoundException {
//        DAOFactory factory = DAOFactory.getInstance();
//        // réservation
//        ReservationService reservationService =
//                new ReservationService(factory.getVehiculeDAO(),
//                        factory.getReservationDAO());
//        ReservationController reservationController = new
//                ReservationController(reservationService);
//
//        new MenuConsole(reservationController,  /* , autres contrôleurs */).demarrer();
        Scanner sc = new Scanner(System.in);

        MainController c = new MainController(sc);
        c.bouclePrincipale();
        sc.close();
    }


}
