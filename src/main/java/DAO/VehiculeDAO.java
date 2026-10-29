package DAO;

import models.Vehicule;

import java.util.List;

public interface VehiculeDAO {
    void creer(Vehicule vehicule);
    Vehicule trouverParId(int id);
    Vehicule trouverParImmatriculation(String immat);
    List<Vehicule> lister();
    void modifier(Vehicule vehicule);
    void supprimer(int id);

}
