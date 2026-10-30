package services;

import DAO.ReservationDAO;
import DAO.ReservationDAOImpl;
import DAO.VehiculeDAO;
import DAO.VehiculeDAOImpl;
import exceptions.DAOException;
import models.Flotte;
import models.Vehicule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.GroupeThread;

import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VehiculeService {
    private VehiculeDAO vehiculeDAO;
    private ReservationDAO reservationDAO;
    private Flotte flotte;
    // log
    private static final Logger log = LogManager.getLogger(VehiculeService.class);

    public VehiculeService() {
        this.vehiculeDAO = new VehiculeDAOImpl();
        this.reservationDAO = new ReservationDAOImpl();

        // sécurisation de la liste pour le multi-threading
        List<Vehicule> listeSecurisee = Collections.synchronizedList(new ArrayList<>(vehiculeDAO.lister()));
        this.flotte = new Flotte(listeSecurisee);
    }

    public void ajouterVehicule(Vehicule v) throws SQLException, ClassNotFoundException {
        // utilisation du thread global partagé
        GroupeThread.getExecutor().submit(() -> {
            try {
                vehiculeDAO.creer(v);
                flotte.getVehicules().add(v);
                log.info("Véhicule enregistré : {}", v.getImmatriculation());
            } catch (DAOException e) {
                log.error("Échec de l'enregistrement ", e.getMessage());
            }
        });
    }

    public void supprimerVehicule(String immat) throws SQLException {
        GroupeThread.getExecutor().submit(() -> {
            try {
                vehiculeDAO.supprimerParImmat(immat);
                flotte.getVehicules().removeIf(vehicule -> vehicule.getImmatriculation().equals(immat));
                log.info("Véhicule supprimé : {}", immat);
            } catch (DAOException e) {
                log.error("Échec de la suppression ", e.getMessage());
            }
        });

    }

    public boolean estDisponible(String immat) throws SQLException {
        Vehicule v = vehiculeDAO.trouverParImmat(immat);
        if (v == null) {
            throw new SQLException("Le véhicule n'existe pas");
        }

        int vehiculeDispo = reservationDAO.vehiculeDisponibleParImmat(immat);
        if(vehiculeDispo == 0) {
            return false;
        }

        return true;
    }

    public Vehicule obtenirParImmat(String immat) {
        return vehiculeDAO.trouverParImmat(immat);

    }

    public Flotte afficherFlotte() {
        return this.flotte;
    }

    // par marque puis par modèle
    public void trierParMarque() {
        Comparator<Vehicule> parMarque = Comparator.comparing(Vehicule::getMarque)
                .thenComparing(Vehicule::getModele);
        this.flotte.getVehicules().sort(parMarque);
    }

    // date de mise en circulation du plus ancien au plus récent
    public void trierParDate() {
        Comparator<Vehicule> parDate = Comparator.comparing(Vehicule::getDateMiseEnCirculation);
        this.flotte.getVehicules().sort(parDate);
    }

    // décroissant
    public void trierParKilometrage() {
        Comparator<Vehicule> parKmDecroissant = Comparator.comparing(Vehicule::getKilometrage).reversed();
        this.flotte.getVehicules().sort(parKmDecroissant);
    }

}
