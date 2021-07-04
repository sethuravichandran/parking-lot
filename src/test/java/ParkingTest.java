import exceptions.AlreadyParkedException;
import exceptions.NotParkedException;
import exceptions.ParkingLotFullException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class ParkingTest {
    private Parking parkingLotOne;
    private Parking parkingLotTwo;
    private static Parkable carOne;
    private static Parkable carTwo;
    private static ParkingLotOwner parkingLotOwner;

    @BeforeAll
    static void beforeAll()
    {
        carOne = mock(Parkable.class);
        carTwo = mock(Parkable.class);

    }

    @BeforeEach
    void beforeEach()
    {
        parkingLotOne = new Parking(1);
        parkingLotTwo = new Parking(2);
        parkingLotOwner = mock(ParkingLotOwner.class);
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
        parkingLotTwo.park(carOne);

        assertThrows(AlreadyParkedException.class, () ->
                parkingLotTwo.park(carOne));
    }

    @Test
    void toThrowExceptionWhenTheLotIsFull() throws Exception{
        parkingLotOne.park(carOne);

        assertThrows(ParkingLotFullException.class, () ->
                parkingLotOne.park(carTwo));
    }

    @Test
    void toUnparkAParkedCar() throws Exception{
        parkingLotOne.park(carOne);
        parkingLotOne.unpark(carOne);

        assertThrows(NotParkedException.class, () ->
                parkingLotOne.unpark(carOne));
    }

    @Test
    void toIntimateOwnerWhenTheParkingLotIsFull() throws Exception
    {
        parkingLotOne.assignOwner(parkingLotOwner);
        parkingLotOne.park(carOne);

        verify(parkingLotOwner, times(1)).intimateFull();
    }

    @Test
    void notToIntimateOwnerWhenTheParkingLotIsNotFullAfterParking() throws Exception
    {
        parkingLotTwo.assignOwner(parkingLotOwner);
        parkingLotTwo.park(carOne);

        verify(parkingLotOwner, never()).intimateFull();
    }
}
