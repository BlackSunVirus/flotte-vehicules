import controllers.FlotteController;
import controllers.MainController;
import exceptions.SaisieInvalideException;
import sendinblue.ApiException;
import services.MailService;
import utils.GroupeThread;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static void main() throws ClassNotFoundException, IOException, ApiException {
//        try {
//            MailService mailService = new MailService();
//            String reponse = mailService.envoyer("data/test.pdf");
//            System.out.println(reponse);
//        } catch ( ApiException | IOException e) {
//            System.out.println(e.getMessage());
//        }

        Scanner sc = new Scanner(System.in);
        MainController c = new MainController(sc);
        c.bouclePrincipale();
        // fermeture du Scanner
        sc.close();


    }


}
