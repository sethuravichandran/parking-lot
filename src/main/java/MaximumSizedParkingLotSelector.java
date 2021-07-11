import java.util.Comparator;
import java.util.List;

public class MaximumSizedParkingLotSelector implements ParkingLotSelector{
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public ParkingLot selectLot(List<ParkingLot> parkingLots) {
        return parkingLots.stream().max(Comparator.comparing(ParkingLot::size)).get();
    }
}
