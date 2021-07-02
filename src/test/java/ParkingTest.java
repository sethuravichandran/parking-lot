import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class ParkingTest {
    @Test
    void toParkACarInAParkingLot()
    {
        Parking parking = new Parking(1);
        Parkable car = new Car();

        try {
            parking.park(car);
        } catch (AlreadyParkedException e) {
           fail("The car is already parked.");
        } catch (ParkingLotFullException f){
            fail("The parking lot is already full.");
        }
    }
}
