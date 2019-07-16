package streams;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class AddStuffUp {
    public static void main(String[] args) {
        IntStream.iterate(1, x -> x + 1)
                .limit(10)
                .reduce((x, y) -> x + y)
                .ifPresent(x -> System.out.println("Sum is : " + x));
//                .peek(x -> System.out.println("> " + x))
//                .limit(10)
//                .filter(x -> x % 3 == 0)
//                .forEach(System.out::println)
        ;

    }
}
