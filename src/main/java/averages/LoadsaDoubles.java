package averages;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class LoadsaDoubles {
    public static void main(String[] args) {
        long start = System.nanoTime();
        long count = 6_000_000_000L;

        ThreadLocalRandom.current().doubles(count, -Math.PI, +Math.PI)
//        Stream.generate(() -> ThreadLocalRandom.current().nextDouble(-Math.PI, +Math.PI))
//                .limit(count)
//                .parallel()
//                .map(Math::sin)
                .collect(AverageInProgress::initial,
//                        (a, d) -> a.updateWithNewData(d),
                        AverageInProgress::updateWithNewData,
//                        (af, a) -> af.merge(a));
                        AverageInProgress::merge)
                .get()
                .map(a -> "The average is: " + a)
                .ifPresent(System.out::println);
        ;


        long time = System.nanoTime() - start;
        System.out.printf("Time for %d was %8.5f seconds\n", count, (time / 1_000_000_000.0));
    }
}
