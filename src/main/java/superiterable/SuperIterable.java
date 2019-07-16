package superiterable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class SuperIterable<E> implements Iterable<E> {
    private Iterable<E> self;

    public SuperIterable(Iterable<E> target) {
        self = target;
    }

    public SuperIterable<E> filter(Predicate<? super E> pred) {
        List<E> rv = new ArrayList<>();
        self.forEach(e -> {if (pred.test(e)) rv.add(e);});
        return new SuperIterable<>(rv);
    }

    public <F> SuperIterable<F> map(Function<? super E, ? extends F> op) {
        List<F> rv = new ArrayList<>();
        self.forEach(e -> rv.add(op.apply(e)));
        return new SuperIterable<>(rv);
    }

    public <F> SuperIterable<F> flatMap(Function<E, SuperIterable<F>> op) {
        List<F> rv = new ArrayList<>();
        self.forEach(e -> op.apply(e).forEach(f -> rv.add(f)));
        return new SuperIterable<>(rv);
    }

    public Iterator<E> iterator() {
        return self.iterator();
    }
}
