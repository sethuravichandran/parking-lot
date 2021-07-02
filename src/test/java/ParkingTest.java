import org.junit.jupiter.api.Test;

public class ParkingTest {
    @Test
    void toParkACarInAParkingLot()
    {
        Parking parking = new Parking(1);
        Parkable car = new Car();

        parking.park(car);
    }
}
