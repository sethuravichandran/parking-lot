package exceptions;

public class NotParkedException extends Exception {
    public NotParkedException() {
        super("The car is already parked !");
    }
}