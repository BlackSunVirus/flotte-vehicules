package DAO;

import models.Vehicule;

import java.util.List;

public interface VehiculeDAO {
    boolean creer(Vehicule vehicule);
    Vehicule trouverParId(int id);
    Vehicule trouverParImmat(String immat);
    List<Vehicule> lister();
    boolean modifier(Vehicule vehicule);
    boolean supprimer(int id);
    boolean supprimerParImmat(String immat);

}
