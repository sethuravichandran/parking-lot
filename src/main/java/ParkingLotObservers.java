import java.util.ArrayList;

public class ParkingLotObservers  extends ArrayList<ParkingLotObserver> {
    public void intimateParkingLotIsFull(ParkingLot parkingLot) {
        for (ParkingLotObserver observer : this)
        {
            observer.intimateParkingLotIsFull(parkingLot);
        }
    }

    public void intimateParkingLotIsFree(ParkingLot parkingLot) {
        for (ParkingLotObserver observer : this)
        {
            observer.intimateParkingLotIsFree(parkingLot);
        }
    }
}
