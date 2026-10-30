package view;

import exceptions.RegleGestionException;
import models.Flotte;
import models.Vehicule;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class vueFlotte extends BaseVue {

    public vueFlotte(Scanner scan) {
        super(scan);
    }

    // Affiche le menu principal
    public void afficherMenu() {
        System.out.println("===== GESTION DE LA VIDÉOTHÈQUE =====");
        System.out.println("1. Ajouter un produit");
        System.out.println("2. Lister le stock");
        System.out.println("3. Rechercher un produit");
        System.out.println("4. Altérer un produit");
        System.out.println("5. Supprimer un produit");
        System.out.println("6. Sauvegarder le stock");
        System.out.println("7. Charger le stock depuis BDD");
        System.out.println("0. Quitter");
        System.out.println("=====================================");
    }

    public Vehicule creationVehicule() {
        try {
            String immatriculation = saisieString("Immatriculation (AA-123-AA)      : ");
            String marque = saisieString("Marque                           : ");
            String modele = saisieString("Modèle                           : ");
            LocalDate dateMiseEnCirculation = saisieDate("Mise en circulation (jj/mm/aaaa) : ");
            int kilometrage = saisieInt("Kilométrage                      : ");
            return new Vehicule(0, immatriculation, marque, modele, dateMiseEnCirculation, kilometrage);

        } catch(RegleGestionException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public String saisieImmatriculation() {
        String immatriculation = saisieString("Immatriculation du véhicule à retirer : ");
        return immatriculation;
    }

    public String saisieConfirmationSuppression (String immatriculation) {
        String res = saisieString("Confirmer le retrait de " +
                immatriculation + " ? (o/n) : ");
        return res;
    }

    public int saisieChoixTrie() {
        int choix = saisieInt("1. Par marque, puis modèle\n" +
                "2. Par date de mise en circulation (plus ancien d'abord)\n" +
                "3. Par kilométrage décroissant\n" +
                "Tri : ");
        return choix;
    }

    public String affichageTrie(int choix) {
        StringBuilder sb = new StringBuilder();

        if (choix == 1){
            sb.append("MARQUE PUIS MODÈLE");


        } else if (choix == 2){
            sb.append("DATE PLUS ANCIEN D'ABORD");
        } else if (choix == 3){
            sb.append("KILOMÉTRAGE DÉCROISSANT");
        }
    }

    public String affichageTrieMarque() {
        StringBuilder sb = new StringBuilder();
        sb.append("FLOTTE TRIÉE PAR MARQUE PUIS MODÈLE").append("\n");


        return sb.toString();

    }

}
