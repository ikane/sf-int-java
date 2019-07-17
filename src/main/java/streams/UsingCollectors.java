package streams;

import trip.Trip;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UsingCollectors {
    public static String tripDurationClassifier(Trip t) {
        Duration d = t.getDuration();
        if (d.compareTo(Duration.ofHours(1)) < 0) return "less than one hour";
        if (d.compareTo(Duration.ofHours(2)) < 0) return "one to less than two hours";
        if (d.compareTo(Duration.ofHours(3)) < 0) return "two to less than three hours";
        return "three or more hours";
    }

    public static void main(String[] args) {

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
                ),
                new Trip("HER", "THE",
                        ZonedDateTime.of(2019, 6, 12, 9, 5, 0, 0, ZoneId.of("US/Mountain")),
                        ZonedDateTime.of(2019, 6, 12, 15, 0, 0, 0, ZoneId.of("US/Eastern"))
                ),
                new Trip("AAA", "BBB",
                        ZonedDateTime.of(2019, 6, 12, 9, 5, 0, 0, ZoneId.of("US/Mountain")),
                        ZonedDateTime.of(2019, 6, 12, 15, 20, 0, 0, ZoneId.of("US/Eastern"))
                ),
                new Trip("DDD", "CCC",
                        ZonedDateTime.of(2019, 6, 12, 9, 5, 0, 0, ZoneId.of("US/Mountain")),
                        ZonedDateTime.of(2019, 6, 12, 16, 0, 0, 0, ZoneId.of("US/Eastern"))
                ),
                new Trip("EEE", "FFF",
                        ZonedDateTime.of(2019, 6, 12, 9, 5, 0, 0, ZoneId.of("US/Mountain")),
                        ZonedDateTime.of(2019, 6, 12, 13, 0, 0, 0, ZoneId.of("US/Eastern"))
                )
        );

        tl.stream()
                .collect(Collectors.groupingBy(
                        UsingCollectors::tripDurationClassifier,
                        Collectors.counting()))
                .entrySet().stream()
//                .map(e -> "These trips: " + e.getValue() + " are " + e.getKey())
                .map(e -> "There are " + e.getValue() + " trips of duration " + e.getKey())
                .forEach(System.out::println);

        tl.stream()
                .collect(Collectors.groupingBy(
                        UsingCollectors::tripDurationClassifier,
                        Collectors.mapping(t -> "From: " + t.getOrigin() + " to: " + t.getDestination(),
                                Collectors.joining(", "))))
                .entrySet().stream()
//                .map(e -> "These trips: " + e.getValue() + " are " + e.getKey())
                .map(e -> "Trips of " + e.getKey() + " are " + e.getValue())
                .forEach(System.out::println);

    }
}
