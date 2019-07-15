package higherordercollectionstuff;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

public class UseHigherOrder {
    public static <E> void showAll(Iterable<E> ls) {
        ls.forEach(s -> System.out.println(s));
        System.out.println("-------------------");
    }

    public static <E> List<E> filter(Iterable<E> is, Predicate<E> ps) {
        List<E> res = new ArrayList<>();
        is.forEach(s -> {if (ps.test(s)) res.add(s);});
        return res;
    }

    public static void main(String[] args) {
        List<String> ls = new ArrayList<>(Arrays.asList("Fred", "Jim", "Sheila"));
        filter(ls, x -> x.length() > 3).forEach(x -> System.out.println(x));

        ls.removeIf(x -> x.length() < 4);
        ls.forEach(x -> System.out.println(x));

        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        LocalDate yesterday = today.minusDays(1);

        Map<LocalDate, String> work = new HashMap<>();
        work.compute(yesterday, (k, v) -> "Leave early");
        work.computeIfAbsent(today, k -> "Finish projects");
        work.put(tomorrow, "Take day off");
        work.computeIfPresent(tomorrow, (k, v) -> v + "!");

        Map<LocalDate, String> home = new HashMap<>();
        home.put(today, "Tidy up");
        home.put(tomorrow, "Party!");

        Map<LocalDate, String> combined = new HashMap<>();

        work.forEach((k, v) -> combined.merge(k, v, (v1, v2) -> v1 + ", " + v2));
        home.forEach((k, v) -> combined.merge(k, v, (v1, v2) -> v1 + ", " + v2));

        combined.forEach((k, v) -> System.out.println("on " + k + " : " + v));
    }
}
