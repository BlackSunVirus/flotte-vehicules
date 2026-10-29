package DAO;

import java.sql.Timestamp;

public class ReservationDAOImpl implements ReservationDAO{
    @Override
    public int vehiculeDisponibleParImmat(String immat) {
        return 0;
    }

    @Override
    public void cloturer(int id, Timestamp timestamp, int kmRetour) {

    }

    @Override
    public boolean estEnDeplacement(int id) {
        return false;
    }
    //TODO
}
