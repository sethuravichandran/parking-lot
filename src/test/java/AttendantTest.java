import exceptions.AllLotsAreFullException;
import exceptions.AlreadyParkedException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

public class AttendantTest {
    private ParkingLot parkingLotTwo;
    private ParkingLot parkingLotThree;
    private static Parkable carOne;
    private static Parkable carTwo;
    private static Parkable carThree;
    private static Parkable carFour;
    private static Parkable carFive;
    private static Parkable carSix;

    @BeforeAll
    static void beforeAll()
    {
        carOne = mock(Parkable.class);
        carTwo = mock(Parkable.class);
        carThree = mock(Parkable.class);
        carFour = mock(Parkable.class);
        carFive = mock(Parkable.class);
        carSix = mock(Parkable.class);

    }

    @BeforeEach
    void beforeEach()
    {
        parkingLotTwo = new ParkingLot(2);
        parkingLotThree = new ParkingLot(3);
    }

    @Test
    void toParkACarInTheOnlyManagedLot() throws Exception{
        Attendant attendant = new Attendant();
        attendant.isResponsibleFor(parkingLotTwo);

        attendant.direct(carOne);
    }

    @Test
    void toParkACarInTheFirstManagedLot() throws Exception{
        Attendant attendant = new Attendant();
        attendant.isResponsibleFor(parkingLotTwo);
        attendant.isResponsibleFor(parkingLotThree);

        attendant.direct(carOne);

        assertThrows(AlreadyParkedException.class,()->
                parkingLotTwo.park(carOne));
    }

    @Test
    void toParkACarInTheNextAvailableLot() throws Exception{
        Attendant attendant = new Attendant();
        attendant.isResponsibleFor(parkingLotTwo);
        attendant.isResponsibleFor(parkingLotThree);

        attendant.direct(carOne);
        attendant.direct(carTwo);
        attendant.direct(carThree);

        assertThrows(AlreadyParkedException.class, ()->
                parkingLotThree.park(carThree));
    }

    @Test
    void notToParkCarsWhenAllLotsAreFull() throws Exception{
        Attendant attendant = new Attendant();
        attendant.isResponsibleFor(parkingLotTwo);
        attendant.isResponsibleFor(parkingLotThree);

        attendant.direct(carOne);
        attendant.direct(carTwo);
        attendant.direct(carThree);
        attendant.direct(carFour);
        attendant.direct(carFive);

        assertThrows(AllLotsAreFullException.class, ()->
                attendant.direct(carSix));
    }

    @Test
    void notToAnAlreadyParkedCarsWhenLotsAreAvailable() throws Exception{
        Attendant attendant = new Attendant();
        attendant.isResponsibleFor(parkingLotTwo);
        attendant.isResponsibleFor(parkingLotThree);

        attendant.direct(carOne);
        attendant.direct(carTwo);

        assertThrows(AlreadyParkedException.class, ()->
                attendant.direct(carTwo));
    }
}
