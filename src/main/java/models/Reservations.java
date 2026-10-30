package models;

public class Reservations<S,K,V>{
    private S  salarie;
    // TODO timestamp date ? ou idReservation ?
    private K idReservation;
    private V reservation;

    public Reservations(S salarie, K idReservation, V reservation) {
        this.salarie = salarie;
        this.idReservation = idReservation;
        this.reservation = reservation;
    }

    public S getSalarie() {
        return salarie;
    }

    public void setSalarie(S salarie) {
        this.salarie = salarie;
    }

    public K getDate() {
        return idReservation;
    }

    public void setDate(K idReservation) {
        this.idReservation = idReservation;
    }

    public V getVehicule() {
        return reservation;
    }

    public void setVehicule(V reservation) {
        this.reservation = reservation;
    }

    @Override
    public String toString() {
        return "Reservations{" +
                "salarie=" + salarie +
                ", idReservation=" + idReservation +
                ", reservation=" + reservation +
                '}';
    }
}
