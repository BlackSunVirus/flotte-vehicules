package DAO;

import models.Reservations;
import models.Salarie;

import java.util.List;

public class SalarieDAOImpl implements SalarieDAO{
    @Override
    public boolean creation(Salarie salarie) {
        return false;
    }

    @Override
    public List<Reservations> afficherHistorique() {
        return List.of();
    }
    //TODO
}
