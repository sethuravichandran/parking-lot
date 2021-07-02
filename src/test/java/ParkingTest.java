import exceptions.AlreadyParkedException;
import exceptions.ParkingLotFullException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

public class ParkingTest {
    private Parking parkingLotOne;
    private static Parkable carOne;

    @BeforeAll
    static void beforeAll()
    {
        carOne = mock(Parkable.class);
    }

    @BeforeEach
    void beforeEach()
    {
        parkingLotOne = new Parking(1);
    }

    @Test
    void toParkACarInAParkingLot()
    {
        try {
            parkingLotOne.park(carOne);
        } catch (AlreadyParkedException e) {
           fail("The car is already parked.");
        } catch (ParkingLotFullException f){
            fail("The parking lot is already full.");
        }
    }

    @Test
    void toThrowExceptionWhenTheCarISAlreadyParked() throws Exception {
        parkingLotOne.park(carOne);

        assertThrows(AlreadyParkedException.class, () ->
                parkingLotOne.park(carOne));
    }
}
