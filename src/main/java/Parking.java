import exceptions.AlreadyParkedException;
import exceptions.ParkingLotFullException;

import java.util.HashSet;
import java.util.Set;

public class Parking {
    private final int size;
    private final Set<Parkable> parkedCar = new HashSet<>();

    public Parking(int size)
    {
        this.size = size;
    }

    public void park(Parkable parkable) throws AlreadyParkedException, ParkingLotFullException {
        if (parkedCar.contains(parkable))
        {
            throw new AlreadyParkedException();
        }

        if (size == parkedCar.size())
        {
            throw new ParkingLotFullException();
        }

        parkedCar.add(parkable);
    }
}
