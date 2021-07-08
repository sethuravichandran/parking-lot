package exceptions;

public class AllLotsAreFullException extends Exception{
    public AllLotsAreFullException() {
        super("All the Parking Lots are full !");
    }
}
