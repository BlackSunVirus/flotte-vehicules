package view;

import java.util.Scanner;

public class MenuVue extends BaseVue {

    public MenuVue(Scanner scan) {
        super(scan);
    }

    // Affiche le menu principal
    public void affichageMenu() {
        System.out.println("=============== MENU PRINCIPAL ===============");
        System.out.println("VÉHICULES");
        System.out.println("  1. Afficher la flotte");
        System.out.println("  2. Ajouter un véhicule");
        System.out.println("  3. Retirer un véhicule");
        System.out.println("  4. Rechercher par immatriculation");
        System.out.println("  5. Trier la flotte");
        System.out.println("SALARIÉS ET RÉSERVATIONS");
        System.out.println("  6. Créer un salarié");
        System.out.println("  7. Enregistrer un départ");
        System.out.println("  8. Enregistrer un retour");
        System.out.println("  9. Véhicules en déplacement");
        System.out.println("  0. Quitter");
        System.out.println("==============================================");
    }
}
