import controllers.FlotteController;
import controllers.MainController;
import exceptions.SaisieInvalideException;
import utils.GroupeThread;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static void main() throws ClassNotFoundException {
        Scanner sc = new Scanner(System.in);

        MainController c = new MainController(sc);
        c.bouclePrincipale();
        // fermeture du Scanner
        sc.close();


    }


}
