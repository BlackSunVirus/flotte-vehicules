package services;

import DAO.ReservationDAO;
import DAO.ReservationDAOImpl;
import DAO.VehiculeDAO;
import DAO.VehiculeDAOImpl;
import models.Flotte;
import models.Vehicule;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class VehiculeService {
    private VehiculeDAO vehiculeDAO;
    private ReservationDAO reservationDAO;
    private Flotte flotte;

    public VehiculeService() {
        this.vehiculeDAO = new VehiculeDAOImpl();
        this.reservationDAO = new ReservationDAOImpl();
        this.flotte = new Flotte(new ArrayList<>());
    }

    public void ajouterVehicule(Vehicule v) {
        vehiculeDAO.creer(v);
        flotte.getVehicules().add(v);
    }

    public void supprimerVehicule(String immat) throws SQLException {
        Vehicule v = vehiculeDAO.trouverParImmat(immat);
        if (v == null) {
            throw new SQLException("Le véhicule n'existe pas");
        }
        int vehiculeDispo = reservationDAO.vehiculeDisponibleParImmat(immat);
        if(vehiculeDispo == 0) {
            throw new SQLException("Le véhicule n'est pas disponible");
        }
        vehiculeDAO.supprimer(immat);
        flotte.getVehicules().removeIf(vehicule -> vehicule.getImmatriculation() == immat);
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
