import exceptions.AllLotsAreFullException;
import exceptions.AlreadyParkedException;
import exceptions.NotParkedException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AttendantTest {
    private ParkingLot parkingLotTwo;
    private ParkingLot parkingLotThree;
    private Attendant firstAvailableParkingLotAttendant;

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
        firstAvailableParkingLotAttendant = new Attendant(new FirstAvailableParkingLotSelector());
    }

    @Nested
    class Direct {
        @Test
        void toParkACarInTheOnlyManagedLot() throws Exception {
            firstAvailableParkingLotAttendant.isResponsibleFor(parkingLotTwo);

            try{
                firstAvailableParkingLotAttendant.direct(carOne);
            } catch (AlreadyParkedException | AllLotsAreFullException e)
            {
                fail("Should park only an unparked car, and only when free space is available.");
            }
        }

        @Test
        void toParkACarInTheFirstManagedLot() throws Exception {
            firstAvailableParkingLotAttendant.isResponsibleFor(parkingLotTwo);
            firstAvailableParkingLotAttendant.isResponsibleFor(parkingLotThree);

            firstAvailableParkingLotAttendant.direct(carOne);

            assertThrows(AlreadyParkedException.class, () ->
                    parkingLotTwo.park(carOne));
        }

        @Test
        void toParkACarInTheNextAvailableLot() throws Exception {
            firstAvailableParkingLotAttendant.isResponsibleFor(parkingLotTwo);
            firstAvailableParkingLotAttendant.isResponsibleFor(parkingLotThree);

            firstAvailableParkingLotAttendant.direct(carOne);
            firstAvailableParkingLotAttendant.direct(carTwo);
            firstAvailableParkingLotAttendant.direct(carThree);

            assertThrows(AlreadyParkedException.class, () ->
                    parkingLotThree.park(carThree));
        }

        @Test
        void notToParkCarsWhenAllLotsAreFull() throws Exception {
            firstAvailableParkingLotAttendant.isResponsibleFor(parkingLotTwo);
            firstAvailableParkingLotAttendant.isResponsibleFor(parkingLotThree);

            firstAvailableParkingLotAttendant.direct(carOne);
            firstAvailableParkingLotAttendant.direct(carTwo);
            firstAvailableParkingLotAttendant.direct(carThree);
            firstAvailableParkingLotAttendant.direct(carFour);
            firstAvailableParkingLotAttendant.direct(carFive);

            assertThrows(AllLotsAreFullException.class, () ->
                    firstAvailableParkingLotAttendant.direct(carSix));
        }

        @Test
        void notToParkAnAlreadyParkedCarsWhenLotsAreAvailable() throws Exception {
            firstAvailableParkingLotAttendant.isResponsibleFor(parkingLotTwo);
            firstAvailableParkingLotAttendant.isResponsibleFor(parkingLotThree);

            firstAvailableParkingLotAttendant.direct(carOne);
            firstAvailableParkingLotAttendant.direct(carTwo);

            assertThrows(AlreadyParkedException.class, () ->
                    firstAvailableParkingLotAttendant.direct(carTwo));
        }

        @Test
        void toAcceptTheSelectionOfTheParkingLotSelector() throws Exception {
            ParkingLotSelector parkingLotSelector = mock(ParkingLotSelector.class);
            Attendant attendant = new Attendant(parkingLotSelector);

            attendant.isResponsibleFor(parkingLotTwo);
            attendant.isResponsibleFor(parkingLotThree);
            ArrayList<ParkingLot> parkingLots = new ArrayList<ParkingLot>() {
                {
                    add(parkingLotTwo);
                    add(parkingLotThree);
                }
            };

            when(parkingLotSelector.selectLot(parkingLots)).thenReturn(parkingLotThree);
            attendant.direct(carOne);

            assertThrows(AlreadyParkedException.class, ()->
                    parkingLotThree.park(carOne));
        }
    }

    @Nested
    class UnPark{
        @Test
        void toUnparkAParkedCarFromTheOnlyManagedLot() throws Exception
        {
            firstAvailableParkingLotAttendant.isResponsibleFor(parkingLotTwo);
            firstAvailableParkingLotAttendant.direct(carOne);

            try{
                firstAvailableParkingLotAttendant.unpark(carOne);
            } catch (NotParkedException e)
            {
                fail("Cannot unpark a car, which is not even parked!");
            }
        }

        @Test
        void toParkAnUnparkedCar() throws Exception
        {
            firstAvailableParkingLotAttendant.isResponsibleFor(parkingLotTwo);

            firstAvailableParkingLotAttendant.direct(carOne);
            firstAvailableParkingLotAttendant.unpark(carOne);
            firstAvailableParkingLotAttendant.direct(carOne);

            assertThrows(AlreadyParkedException.class, () ->
                    parkingLotTwo.park(carOne));
        }

        @Test
        void toParkACarOnlyWhenSpaceIsAvailableAfterUnparking() throws Exception{
            firstAvailableParkingLotAttendant.isResponsibleFor(parkingLotTwo);
            firstAvailableParkingLotAttendant.isResponsibleFor(parkingLotThree);

            firstAvailableParkingLotAttendant.direct(carOne);
            firstAvailableParkingLotAttendant.direct(carTwo);
            firstAvailableParkingLotAttendant.direct(carThree);
            firstAvailableParkingLotAttendant.direct(carFour);
            firstAvailableParkingLotAttendant.direct(carFive);

            firstAvailableParkingLotAttendant.unpark(carTwo);
            firstAvailableParkingLotAttendant.direct(carSix);

            assertThrows(AlreadyParkedException.class, () ->
                    parkingLotTwo.park(carSix));
        }

        @Test
        void notToUnparkACarWhichIsNotParkedInAnyOfTheLots() throws Exception {
            firstAvailableParkingLotAttendant.isResponsibleFor(parkingLotTwo);

            assertThrows(NotParkedException.class, ()->
                    firstAvailableParkingLotAttendant.unpark(carOne));
        }

    }
}
