import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;

public class FirstAvailableParkingLotSelectorTest {

    @Test
    void toSelectTheFirstAvailableLotFromMultipleLots(){
        ParkingLot parkingLotOne = mock(ParkingLot.class);
        ParkingLot parkingLotTwo = mock(ParkingLot.class);
        ParkingLot parkingLotThree = mock(ParkingLot.class);

        ArrayList<ParkingLot> parkingLots = new ArrayList<ParkingLot>()
        {
            {
                add(parkingLotOne);
                add(parkingLotTwo);
                add(parkingLotThree);
            }
        };

        ParkingLot selectedLot = new FirstAvailableParkingLotSelector().selectLot(parkingLots);

        assertThat(selectedLot, is (equalTo(parkingLotOne)));
    }
}
