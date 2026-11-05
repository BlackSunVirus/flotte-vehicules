import controllers.FlotteController;
import controllers.MainController;
import exceptions.SaisieInvalideException;
import services.MailService;
import utils.GroupeThread;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static void main() throws ClassNotFoundException {
        try {
            MailService mailService = new MailService();
            String reponse = mailService.envoyer("data/test.pdf");
            System.out.println(reponse);
        }  catch (sendinblue.ApiException e) {
            System.err.println("Erreur API Brevo (" + e.getCode() + ") : " + e.getResponseBody());
        } catch (IOException e) {
            System.err.println("Erreur d'accès au fichier : " + e.getMessage());
        }

//        Scanner sc = new Scanner(System.in);
//        MainController c = new MainController(sc);
//        c.bouclePrincipale();
//        // fermeture du Scanner
//        sc.close();


    }


}
