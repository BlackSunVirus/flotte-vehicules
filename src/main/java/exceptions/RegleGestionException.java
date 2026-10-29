package exceptions;

public class RegleGestionException extends RuntimeException {
    public RegleGestionException(String message) { super("\u001B[31m" + message + "\u001B[0m"); }
}
