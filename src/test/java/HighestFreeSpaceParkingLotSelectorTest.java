import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HighestFreeSpaceParkingLotSelectorTest {
    @Test
    void toSelectParkingLotWithTheHighestNumberOfFreeSpaces() {
        ParkingLot parkingLotOne = mock(ParkingLot.class);
        ParkingLot parkingLotTwo = mock(ParkingLot.class);
        ParkingLot parkingLotThree = mock(ParkingLot.class);
        when(parkingLotOne.freeSpace()).thenReturn(1);
        when(parkingLotTwo.freeSpace()).thenReturn(2);
        when(parkingLotThree.freeSpace()).thenReturn(3);
        ArrayList<ParkingLot> parkingLots = new ArrayList<ParkingLot>() {
            {
                add(parkingLotOne);
                add(parkingLotTwo);
                add(parkingLotThree);
            }
        };

        ParkingLot selectedLot = new HighestFreeSpaceParkingLotSelector().selectLot(parkingLots);

        assertThat(selectedLot, is(equalTo(parkingLotThree)));
    }

}
