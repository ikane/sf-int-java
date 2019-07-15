package superiterable;

import trip.Trip;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class UseSuperIterable {
    public static SuperIterable<String> getAirportNames(Trip t) {
        List<String> names = new ArrayList<>();
        names.add(t.getOrigin());
        names.add(t.getDestination());
        return new SuperIterable<>(names);
    }
    public static void main(String[] args) {
        List<String> nl = Arrays.asList("Fred", "Jim", "Sheila");
        SuperIterable<String> names = new SuperIterable<>(nl);

        System.out.println("----------------------");
        names
                .filter(x -> x.length() > 3)
                .map(x -> "Dear " + x)
                .forEach(x -> System.out.println(x));

        System.out.println("----------------------");
        nl.stream()
                .filter(x -> x.length() > 3)
                .map(x -> "Dear " + x)
                .forEach(x -> System.out.println(x));

        List<Trip> tl = Arrays.asList(
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
        SuperIterable<Trip> sit = new SuperIterable<>(tl);

        System.out.println("----------------------");
        sit
                .flatMap(t -> UseSuperIterable.getAirportNames(t))
                .forEach(t -> System.out.println(t));

        System.out.println("----------------------");
        tl.stream()
                .flatMap(t -> Stream.of(t.getOrigin(), t.getDestination()))
                .forEach(t -> System.out.println(t));

    }
}