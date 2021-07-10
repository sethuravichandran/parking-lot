import exceptions.AllLotsAreFullException;
import exceptions.AlreadyParkedException;
import exceptions.NotParkedException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;

public class AttendantTest {
    private ParkingLot parkingLotTwo;
    private ParkingLot parkingLotThree;
    private Attendant attendant;

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
        attendant = new Attendant();
    }

    @Nested
    class Direct {
        @Test
        void toParkACarInTheOnlyManagedLot() throws Exception {
            attendant.isResponsibleFor(parkingLotTwo);

            try{
                attendant.direct(carOne);
            } catch (AlreadyParkedException | AllLotsAreFullException e)
            {
                fail("Should park only an unparked car, and only when free space is available.");
            }
        }

        @Test
        void toParkACarInTheFirstManagedLot() throws Exception {
            attendant.isResponsibleFor(parkingLotTwo);
            attendant.isResponsibleFor(parkingLotThree);

            attendant.direct(carOne);

            assertThrows(AlreadyParkedException.class, () ->
                    parkingLotTwo.park(carOne));
        }

        @Test
        void toParkACarInTheNextAvailableLot() throws Exception {
            attendant.isResponsibleFor(parkingLotTwo);
            attendant.isResponsibleFor(parkingLotThree);

            attendant.direct(carOne);
            attendant.direct(carTwo);
            attendant.direct(carThree);

            assertThrows(AlreadyParkedException.class, () ->
                    parkingLotThree.park(carThree));
        }

        @Test
        void notToParkCarsWhenAllLotsAreFull() throws Exception {
            attendant.isResponsibleFor(parkingLotTwo);
            attendant.isResponsibleFor(parkingLotThree);

            attendant.direct(carOne);
            attendant.direct(carTwo);
            attendant.direct(carThree);
            attendant.direct(carFour);
            attendant.direct(carFive);

            assertThrows(AllLotsAreFullException.class, () ->
                    attendant.direct(carSix));
        }

        @Test
        void notToAnAlreadyParkedCarsWhenLotsAreAvailable() throws Exception {
            attendant.isResponsibleFor(parkingLotTwo);
            attendant.isResponsibleFor(parkingLotThree);

            attendant.direct(carOne);
            attendant.direct(carTwo);

            assertThrows(AlreadyParkedException.class, () ->
                    attendant.direct(carTwo));
        }
    }

    @Nested
    class UnPark{
        @Test
        void toUnparkAParkedCarFromTheOnlyManagedLot() throws Exception
        {
            attendant.isResponsibleFor(parkingLotTwo);
            attendant.direct(carOne);

            try{
                attendant.unpark(carOne);
            } catch (NotParkedException e)
            {
                fail("Cannot unpark a car, which is not even parked!");
            }
        }

        @Test
        void toParkAnUnparkedCar() throws Exception
        {
            attendant.isResponsibleFor(parkingLotTwo);

            attendant.direct(carOne);
            attendant.unpark(carOne);
            attendant.direct(carOne);

            assertThrows(AlreadyParkedException.class, () ->
                    parkingLotTwo.park(carOne));
        }

        @Test
        void toParkACarOnlyWhenSpaceIsAvailableAfterUnparking() throws Exception{
            attendant.isResponsibleFor(parkingLotTwo);
            attendant.isResponsibleFor(parkingLotThree);

            attendant.direct(carOne);
            attendant.direct(carTwo);
            attendant.direct(carThree);
            attendant.direct(carFour);
            attendant.direct(carFive);

            attendant.unpark(carTwo);
            attendant.direct(carSix);

            assertThrows(AlreadyParkedException.class, () ->
                    parkingLotTwo.park(carSix));
        }

        @Test
        void notToUnparkACarWhichIsNotParkedInAnyOfTheLots() throws Exception {
            attendant.isResponsibleFor(parkingLotTwo);

            assertThrows(NotParkedException.class, ()->
                    attendant.unpark(carOne));
        }

    }
}
