import exceptions.AllLotsAreFullException;
import exceptions.AlreadyParkedException;
import exceptions.NotParkedException;
import exceptions.ParkingLotFullException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Attendant implements ParkingLotObserver{
    List<ParkingLot> availableParkingLots = new ArrayList<>();
    HashMap<Parkable, ParkingLot> allocation = new HashMap<>();

    public void isResponsibleFor(ParkingLot parkingLot) {
        parkingLot.assignObserver(this);
        if (!parkingLot.isFull()){
            availableParkingLots.add(parkingLot);
        }
    }

    public void direct(Parkable car) throws AlreadyParkedException, ParkingLotFullException,
            AllLotsAreFullException {
        if(allocation.containsKey(car)){
            throw new AlreadyParkedException();
        }
        if (availableParkingLots.isEmpty()){
            throw new AllLotsAreFullException();
        }

        ParkingLot lotToBeParked = availableParkingLots.get(0);
        lotToBeParked.park(car);
        allocation.put(car, lotToBeParked);
    }

    public void unpark(Parkable car) throws NotParkedException {

        if (!allocation.containsKey(car))
        {
            throw new NotParkedException();
        }

        ParkingLot lotToBeUnparkedFrom = allocation.get(car);
        lotToBeUnparkedFrom.unpark(car);
        allocation.remove(car);
    }

    @Override
    public void intimateParkingLotIsFull(ParkingLot parkingLot) {
        availableParkingLots.remove(parkingLot);

    }

    @Override
    public void intimateParkingLotIsFree(ParkingLot parkingLot) {
        availableParkingLots.add(parkingLot);

    }

}
