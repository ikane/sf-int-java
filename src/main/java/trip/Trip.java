package trip;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Predicate;

//enum AirportId {
//    DEN("US/Mountain"), SFO("US/Pacific"), LGA("US/Eastern");
//
//    private ZoneId timezone;
//    private AirportId(String zone) {
//        timezone = ZoneId.of(zone);
//    }
//}

class AirportId {
    //    private static final List<String> ls = new ArrayList<>(Arrays.asList(/*LocalDate.now(), */"Fred"));
    private static final Map<String, AirportId> existing = new HashMap<>();
    private String id;

    private AirportId() {
    }

    public AirportId of(String id) {
        AirportId self = existing.get(id);
        if (self == null) {
            self = new AirportId();
            self.id = id;
            existing.put(id, self);
        }
        return self;
    }
}

public class Trip {
    private String origin;
    private String destination;
    private ZonedDateTime depart;
    private ZonedDateTime arrive;


    // oops should be factory
    public Trip(String origin, String destination, ZonedDateTime depart, ZonedDateTime arrive) {
        this.origin = origin;
        this.destination = destination;
        this.depart = depart;
        this.arrive = arrive;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public ZonedDateTime getDepart() {
        return depart;
    }

    public ZonedDateTime getArrive() {
        return arrive;
    }

    public Duration getDuration() {
        return Duration.between(depart, arrive);
    }

    public static Predicate<Trip> longerThan(final Duration d) {
        return (Trip t) -> t.getDuration().compareTo(d) > 0;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", depart=" + depart +
                ", arrive=" + arrive +
                '}';
    }
}
