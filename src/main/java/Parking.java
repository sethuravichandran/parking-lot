import exceptions.AlreadyParkedException;
import exceptions.NotParkedException;
import exceptions.ParkingLotFullException;

import java.util.HashSet;
import java.util.Set;

public class Parking {
    private final int size;
    private final Set<Parkable> parkingLot = new HashSet<>();
    private ParkingLotObservers observers = new ParkingLotObservers();

    public Parking(int size)
    {
        this.size = size;
    }

    public void park(Parkable parkable) throws AlreadyParkedException, ParkingLotFullException {
        if (parkingLot.contains(parkable))
        {
            throw new AlreadyParkedException();
        }

        if (isFull())
        {
            throw new ParkingLotFullException();
        }
        parkingLot.add(parkable);

        if (isFull()){
            observers.intimateParkingLotIsFull();
        }
    }

    private boolean isFull(){
        return size == parkingLot.size();
    }

    public void unpark(Parkable parkable) throws NotParkedException{
        if (!parkingLot.contains(parkable)){
            throw new NotParkedException();
        }

        parkingLot.remove(parkable);
    }

    public void assignObserver(ParkingLotObserver observer) {
        this.observers.add(observer);
    }
}
