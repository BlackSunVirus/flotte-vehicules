package services;

import DAO.ReservationDAO;
import DAO.VehiculeDAO;
import models.Flotte;
import models.Vehicule;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class VehiculeService {
    private VehiculeDAO vehiculeDAO;
    private ReservationDAO reservationDAO;
    private Flotte flotte;

    public VehiculeService(VehiculeDAO vehiculeDAO, ReservationDAO reservationDAO, Flotte flotte) {
        this.vehiculeDAO = vehiculeDAO;
        this.reservationDAO = reservationDAO;
        this.flotte = flotte;
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
        vehiculeDAO.supprimerParImmat(immat);
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

        Comparator<Vehicule> comparator = Comparator.comparing(Vehicule::getMarque)
                .thenComparing(Vehicule::getModele);
        this.flotte.getVehicules().sort(comparator);
    }

    // date de mise en circulation du plus ancien au plus récent
    public void trierParDate() {

    }

    // décroissant
    public void trierParKilometrage() {

    }
}
