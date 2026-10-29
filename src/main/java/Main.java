import DAO.DAOFactory;
import controllers.ReservationController;
import services.ReservationService;
import vue.MenuConsole;

public class Main {
    static void main() throws ClassNotFoundException {
        DAOFactory factory = DAOFactory.getInstance();
        // réservation
        ReservationService reservationService =
                new ReservationService(factory.getVehiculeDAO(),
                        factory.getReservationDAO());
        ReservationController reservationController = new
                ReservationController(reservationService);

        new MenuConsole(reservationController,  /* , autres contrôleurs */).demarrer();
    }
}
