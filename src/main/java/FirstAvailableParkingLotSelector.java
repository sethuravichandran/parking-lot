
import java.util.List;

public class FirstAvailableParkingLotSelector implements ParkingLotSelector{

    @Override
    public ParkingLot selectLot(List<ParkingLot> parkingLots) {
        return parkingLots.get(0);
    }
}
