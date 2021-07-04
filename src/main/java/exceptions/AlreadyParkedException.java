package exceptions;

public class AlreadyParkedException extends Exception {
    public AlreadyParkedException() {
        super("The car is already parked !");
    }
}
