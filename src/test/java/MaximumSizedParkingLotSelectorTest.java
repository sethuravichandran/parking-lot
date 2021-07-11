import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MaximumSizedParkingLotSelectorTest {

    @Test
    void toSelectTheLotWithTheMaximumSize(){
        ParkingLot parkingLotOne = new ParkingLot(1);
        ParkingLot parkingLotTwo = new ParkingLot(2);
        ParkingLot parkingLotThree = new ParkingLot(3);
        ArrayList<ParkingLot> parkingLots = new ArrayList<ParkingLot>() {
            {
                add(parkingLotOne);
                add(parkingLotTwo);
                add(parkingLotThree);
            }
        };

        ParkingLot selectedLot = new MaximumSizedParkingLotSelector().selectLot(parkingLots);

        assertThat(selectedLot, is(equalTo(parkingLotThree)));
    }

}

