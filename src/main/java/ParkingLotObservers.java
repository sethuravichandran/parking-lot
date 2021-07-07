import java.util.ArrayList;

public class ParkingLotObservers  extends ArrayList<ParkingLotObserver> {
    public void intimateParkingLotIsFull() {
        for (ParkingLotObserver observer : this)
        {
            observer.intimateParkingLotIsFull();
        }
    }

    public void intimateParkingLotIsFree() {
        for (ParkingLotObserver observer : this)
        {
            observer.intimateParkingLotIsFree();
        }
    }
}
