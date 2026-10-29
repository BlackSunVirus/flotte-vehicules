package models;

import java.util.List;

public class Flotte {

    private List<Vehicule> vehicules;

    public Flotte(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public void setVehicules(List<Vehicule> vehicules) {
        this.vehicules = vehicules;
    }

    @Override
    public String toString() {
        return "Flotte{" +
                "vehicules=" + vehicules +
                '}';
    }
}
