package exceptions;

public class DAOException extends RuntimeException {
    public DAOException(String message) {
        super("\u001B[31m" + message + "\u001B[0m");
    }
}
