package trip;

import java.time.ZonedDateTime;

public class LongTrip extends Trip {
    public LongTrip(String origin, String destination, ZonedDateTime depart, ZonedDateTime arrive) {
        super(origin, destination, depart, arrive);
    }

    @Override
    public String toString() {
        return "LongTrip: " + super.toString();
    }
}
