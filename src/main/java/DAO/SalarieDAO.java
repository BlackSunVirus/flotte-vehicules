package DAO;

import models.Reservations;
import models.Salarie;

import java.util.List;

public interface SalarieDAO {
    boolean creation(Salarie salarie);
    List<Reservations> afficherHistorique();

}
