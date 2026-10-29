package models;

import java.sql.Timestamp;

public class Reservations <S,K,V>{
    private S  salarie;
    private K date;
    private V vehicule;

    public Reservations(S salarie, K date, V vehicule) {
        this.salarie = salarie;
        this.date = date;
        this.vehicule = vehicule;
    }

    public S getSalarie() {
        return salarie;
    }

    public void setSalarie(S salarie) {
        this.salarie = salarie;
    }

    public K getDate() {
        return date;
    }

    public void setDate(K date) {
        this.date = date;
    }

    public V getVehicule() {
        return vehicule;
    }

    public void setVehicule(V vehicule) {
        this.vehicule = vehicule;
    }

    @Override
    public String toString() {
        return "Reservations{" +
                "salarie=" + salarie +
                ", date=" + date +
                ", vehicule=" + vehicule +
                '}';
    }
}
