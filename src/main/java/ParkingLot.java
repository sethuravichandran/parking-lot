import exceptions.AlreadyParkedException;
import exceptions.NotParkedException;
import exceptions.ParkingLotFullException;

import java.util.HashSet;
import java.util.Set;

public class ParkingLot {
    private final int size;
    private final Set<Parkable> parkingLot = new HashSet<>();
    private ParkingLotObservers observers = new ParkingLotObservers();

    public ParkingLot(int size)
    {
        this.size = size;
    }

    public void park(Parkable car) throws AlreadyParkedException, ParkingLotFullException {
        if (parkingLot.contains(car))
        {
            throw new AlreadyParkedException();
        }

        if (isFull())
        {
            throw new ParkingLotFullException();
        }
        parkingLot.add(car);
        if (isFull()){
            observers.intimateParkingLotIsFull(this);
        }
    }



    public void unpark(Parkable car) throws NotParkedException{
        if (!parkingLot.contains(car)){
            throw new NotParkedException();
        }

        parkingLot.remove(car);

        if (isFullBeforeUnpark())
        {
            observers.intimateParkingLotIsFree(this);
        }
    }

    public void assignObserver(ParkingLotObserver observer) {

        this.observers.add(observer);
    }

    private boolean isFullBeforeUnpark(){

        return (size-1) == parkingLot.size();
    }

    boolean isFull(){

        return size == parkingLot.size();
    }

    public int size(){
        return size;
    }

}
