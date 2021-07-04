package exceptions;

public class ParkingLotFullException extends Exception {
    public ParkingLotFullException() {
        super("The parking lot is already full !");
    }
}
