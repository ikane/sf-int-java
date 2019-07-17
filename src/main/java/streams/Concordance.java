package streams;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;
import static java.util.function.Function.*;


public class Concordance {

    public static <T> Comparator<T> reversed(Comparator<T> comp) {
        return (a, b) -> comp.compare(b, a);
    }

    public static <T> Predicate<T> negate(Predicate<T> pred) {
        return t -> !pred.test(t);
    }

    public static <T> Predicate<T> and(Predicate<T> p1, Predicate<T> p2) {
        return t -> p1.test(t) && p2.test(t);
    }

    public static <T, U extends Comparable<U>> Comparator<T> get(Function<T, U> extractor) {
        return (a, b) -> extractor.apply(a).compareTo(extractor.apply(b));
    }

    public static void main(String[] args) throws Throwable {
        final Pattern WORD_BOUNDARY = Pattern.compile("\\W+");
        final Comparator<Map.Entry<String, Long>> forward = Map.Entry.comparingByValue();
        final Comparator<Map.Entry<String, Long>> reversed = forward.reversed();

        try (Stream<String> input = Files.lines(Paths.get("PrideAndPrejudice.txt"));) {
            input
                    .map(String::toLowerCase)
                    .flatMap(WORD_BOUNDARY::splitAsStream)
                    .filter(s -> s.length() > 0)
                    .collect(groupingBy(identity(), counting()))
                    .entrySet().stream()
//                    .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
//                    .sorted(Concordance.reversed(Concordance.get(e -> e.getValue())))
                    .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                    .limit(200)
                    .map(e -> String.format("%20s : %5d", e.getKey(), e.getValue()))
                    .forEach(System.out::println);
        }
    }
}
