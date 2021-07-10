import exceptions.AlreadyParkedException;
import exceptions.NotParkedException;
import exceptions.ParkingLotFullException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

public class ParkingLotTest {
    private ParkingLot parkingLotOne;
    private ParkingLot parkingLotTwo;
    private static Parkable carOne;
    private static Parkable carTwo;
    private ParkingLotObserver parkingLotOwner;
    private ParkingLotObserver trafficCop;

    @BeforeAll
    static void beforeAll()
    {
        carOne = mock(Parkable.class);
        carTwo = mock(Parkable.class);

    }

    @BeforeEach
    void beforeEach()
    {
        parkingLotOne = new ParkingLot(1);
        parkingLotTwo = new ParkingLot(2);
        parkingLotOwner = mock(ParkingLotObserver.class);
        trafficCop = mock(ParkingLotObserver.class);
    }

    @Nested
    class Park {
        @Test
        void toParkACarInAParkingLot() {
            try {
                parkingLotOne.park(carOne);
            } catch (AlreadyParkedException e) {
                fail("The car is already parked.");
            } catch (ParkingLotFullException f) {
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
        void toThrowExceptionWhenTheLotIsFull() throws Exception {
            parkingLotOne.park(carOne);

            assertThrows(ParkingLotFullException.class, () ->
                    parkingLotOne.park(carTwo));
        }
    }

    @Nested
    class Unpark {
        @Test
        void toUnparkAParkedCar() throws Exception {
            parkingLotOne.park(carOne);
            parkingLotOne.unpark(carOne);

            assertThrows(NotParkedException.class, () ->
                    parkingLotOne.unpark(carOne));
        }
    }

    @Nested
    class Notify {
        @Test
        void toIntimateOwnerWhenTheParkingLotIsFull() throws Exception {
            parkingLotOne.assignObserver(parkingLotOwner);
            parkingLotOne.park(carOne);

            verify(parkingLotOwner, times(1)).intimateParkingLotIsFull(parkingLotOne);
        }

        @Test
        void notToIntimateOwnerWhenTheParkingLotIsNotFullAfterParking() throws Exception {
            parkingLotTwo.assignObserver(parkingLotOwner);
            parkingLotTwo.park(carOne);

            verify(parkingLotOwner, never()).intimateParkingLotIsFull(parkingLotTwo);
        }

        @Test
        void toIntimateTrafficCopWhenParkingLotIsFull() throws Exception {
            parkingLotOne.assignObserver(trafficCop);
            parkingLotOne.park(carOne);

            verify(trafficCop, times(1)).intimateParkingLotIsFull(parkingLotOne);
        }
    }

}
