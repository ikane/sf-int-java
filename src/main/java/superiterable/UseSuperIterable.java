package superiterable;

import trip.LongTrip;
import trip.Trip;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
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
//                .flatMap(t -> UseSuperIterable.getAirportNames(t))
                .flatMap(UseSuperIterable::getAirportNames)
//                .forEach(t -> System.out.println(t));
                .forEach(System.out::println);

        System.out.println("----------------------");
        tl.stream()
                .flatMap(t -> Stream.of(t.getOrigin(), t.getDestination()))
                .forEach(t -> System.out.println(t));


        System.out.println("----------------------");
        tl.stream()
                .forEach(t -> System.out.println(t));

        System.out.println("All: ----------------------");
        tl.stream()
                .forEach(t -> System.out.println(t));

        System.out.println("Long: ----------------------");
        tl.stream()
                .filter(t -> t.getDuration().compareTo(Duration.ofHours(3)) > 0)
                .forEach(t -> System.out.println(t));

        System.out.println("----------------------");
        tl.stream()
                .filter(t -> t.getDuration().compareTo(Duration.ofHours(3)) > 0)
                .map(t -> "From " + t.getOrigin() + " to " + t.getDestination() + " takes " + t.getDuration())
                .forEach(t -> System.out.println(t));

        System.out.println("Airports for long: ----------------------");
        tl.stream()
                .filter(t -> t.getDuration().compareTo(Duration.ofHours(3)) > 0)
                .flatMap(t -> Stream.of(t.getOrigin(), t.getDestination()))
                .sorted((s1, s2) -> s2.compareTo(s1))
                .distinct()
                .forEach(t -> System.out.println(t));

        System.out.println("Airports with durations: ----------------------");
        tl.stream()
                .flatMap(t -> Stream.of(t.getOrigin(), t.getDestination())
                        .map(a -> a + " has a trip of duration " + t.getDuration()))
                .forEach(t -> System.out.println(t));


        List<LongTrip> ltl = Arrays.asList(
                new LongTrip("DEN", "SEA",
                        ZonedDateTime.of(2019, 6, 12, 10, 5, 0, 0, ZoneId.of("US/Mountain")),
                        ZonedDateTime.of(2019, 6, 12, 11, 45, 0, 0, ZoneId.of("US/Pacific"))
                ),
                new LongTrip("DEN", "SFO",
                        ZonedDateTime.of(2019, 6, 12, 14, 5, 0, 0, ZoneId.of("US/Mountain")),
                        ZonedDateTime.of(2019, 6, 12, 15, 15, 0, 0, ZoneId.of("US/Pacific"))
                ),
                new LongTrip("DEN", "LGA",
                        ZonedDateTime.of(2019, 6, 12, 9, 5, 0, 0, ZoneId.of("US/Mountain")),
                        ZonedDateTime.of(2019, 6, 12, 15, 0, 0, 0, ZoneId.of("US/Eastern"))
                )
        );

        System.out.println("-----------------------------");
        Predicate<Trip> tripFilter = t -> t.getDuration().compareTo(Duration.ofHours(3)) > 0;
        ltl.stream()
                .filter(tripFilter)
                .forEach(s -> System.out.println(s));

        System.out.println("-----------------------------");
        new SuperIterable<LongTrip>(ltl)
                .filter(tripFilter)
                .forEach(s -> System.out.println(s));



        System.out.println("Durations: ----------------------");
        tl.stream()
//                .map(t -> t.getDuration())
                .map(Trip::getDuration)
                .forEach(t -> System.out.println(t));


    }
}