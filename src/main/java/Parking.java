import exceptions.AlreadyParkedException;
import exceptions.ParkingLotFullException;

import java.util.HashSet;
import java.util.Set;

public class Parking {
    Set<Parkable> parkedCar = new HashSet<>();

    public Parking(int size)
    {

    }

    public void park(Parkable parkable) throws AlreadyParkedException, ParkingLotFullException {
        if (parkedCar.contains(parkable))
        {
            throw new AlreadyParkedException();
        }
        parkedCar.add(parkable);
    }
}
