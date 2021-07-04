import exceptions.AlreadyParkedException;
import exceptions.NotParkedException;
import exceptions.ParkingLotFullException;

import java.util.HashSet;
import java.util.Set;

public class Parking {
    private final int size;
    private final Set<Parkable> parkedCar = new HashSet<>();
    private ParkingLotOwner owner;

    public Parking(int size)
    {
        this.size = size;
    }

    public void park(Parkable parkable) throws AlreadyParkedException, ParkingLotFullException {
        if (parkedCar.contains(parkable))
        {
            throw new AlreadyParkedException();
        }

        if (isFull())
        {
            throw new ParkingLotFullException();
        }
        parkedCar.add(parkable);

        if (owner!=null && isFull()){
            owner.intimateFull();
        }
    }

    private boolean isFull(){
        return size == parkedCar.size();
    }

    public void unpark(Parkable parkable) throws NotParkedException{
        if (!parkedCar.contains(parkable)){
            throw new NotParkedException();
        }

        parkedCar.remove(parkable);
    }

    public void assignOwner(ParkingLotOwner owner) {
        this.owner = owner;
    }
}
