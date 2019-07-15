package trip;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class Schedule {
    public static void main(String[] args) {
        Set<String> zones = ZoneId.getAvailableZoneIds();
        for (String s : zones) System.out.println(s);
        System.out.println("----------------");
        List<Trip> trips = Arrays.asList(
                new Trip("DEN", "SEA",
                        ZonedDateTime.of(2019, 6, 12, 10, 5, 0, 0, ZoneId.of("US/Mountain")),
                        ZonedDateTime.of(2019, 6, 12, 11, 45, 0, 0, ZoneId.of("US/Pacific"))
                ),
                new Trip("DEN", "SFO",
                        ZonedDateTime.of(2019, 6, 12, 14, 5, 0, 0, ZoneId.of("US/Mountain")),
                        ZonedDateTime.of(2019, 6, 12, 15, 15, 0, 0, ZoneId.of("US/Pacific"))
                ),
                new Trip("DEN", "LGA",
                        ZonedDateTime.of(2019, 6, 12, 9, 5, 0, 0, ZoneId.of("US/Mountain")),
                        ZonedDateTime.of(2019, 6, 12, 15, 0, 0, 0, ZoneId.of("US/Eastern"))
                )
        );

        Comparator<Trip> comp = (t1, t2) -> t1.getDuration().compareTo(t2.getDuration());

        System.out.println("comp is " + comp.getClass().getName());
        if (comp instanceof Object) {
            System.out.println("Yes it is!");
        }
        trips.sort(comp);
        for (Trip t : trips) {
            System.out.println("> " + t);
        }
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println("  now is " + now);
        ZonedDateTime then = now.plusDays(7);
        System.out.println("then  is " + then);

        System.out.println("-------------------------");
        Predicate<Trip> longTrip = Trip.longerThan(Duration.ofHours(3));
        for (Trip t : trips) {
            if (longTrip.test(t)) {
                System.out.println("> " + t + " duration: " + t.getDuration());
            }
        }

    }
}
