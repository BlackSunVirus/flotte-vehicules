package DAO;

import java.sql.Timestamp;

public interface ReservationDAO {
    int vehiculeDisponibleParImmat(String immat);

    void cloturer(int id, Timestamp timestamp, int kmRetour);

    boolean estEnDeplacement(int id);
    //TODO
}
