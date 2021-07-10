import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class ParkingLotObserversTest {
    private ParkingLot parkingLotOne;
    private ParkingLot parkingLotTwo;
    private static Parkable carOne;
    private static Parkable carTwo;
    private ParkingLotObserver parkingLotOwner;
    private ParkingLotObserver trafficCop;

    @BeforeAll
    static void beforeAll() {
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

    @Test
    void toIntimateCorrespondingObserversWhenParkingLotHasFreeSpace() throws Exception
    {
        parkingLotOne.assignObserver(parkingLotOwner);
        parkingLotOne.assignObserver(trafficCop);

        parkingLotOne.park(carOne);
        parkingLotOne.unpark(carOne);

        verify(parkingLotOwner, times(1)).intimateParkingLotIsFree(parkingLotOne);
        verify(trafficCop, times(1)).intimateParkingLotIsFree(parkingLotOne);

    }

    @Test
    void toIntimateCorrespondingObserversWhenParkingLotHasFreeSpaceAgain() throws Exception
    {
        parkingLotTwo.assignObserver(parkingLotOwner);
        parkingLotTwo.assignObserver(trafficCop);

        parkingLotTwo.park(carOne);
        parkingLotTwo.park(carTwo);
        parkingLotTwo.unpark(carOne);

        verify(parkingLotOwner, times(1)).intimateParkingLotIsFree(parkingLotTwo);
        verify(trafficCop, times(1)).intimateParkingLotIsFree(parkingLotTwo);

    }
}
