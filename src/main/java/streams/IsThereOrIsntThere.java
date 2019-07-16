package streams;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class IsThereOrIsntThere {
    public static void main(String[] args) {
        Map<String, String> names = new HashMap<>();
        names.put("Fred", "Jones");
        String name = "Freddy";

        String last = names.get(name);
        if (last != null) {
            String message = "Dear Professor " + last.toUpperCase();
            System.out.println(message);
        }

        Optional.of(names)
                .map(m -> m.get(name))
//                .filter(n -> n != null)
                .map(n -> "Dear Professor " + n)
                .ifPresent(System.out::println);
    }
}
