package averages;

import java.util.Optional;

public class Average2 {
    public final double sum;
    public final long count;

    public Average2(double sum, long count) {
        this.sum = sum;
        this.count = count;
    }

    public Average2 include(double d) {
        return new Average2(d + sum, count + 1);
    }

    public Average2 merge(Average2 other) {
        return new Average2(sum + other.sum, count + other.count);
    }

    public Optional<Double> get() {
        if (count > 0) {
            return Optional.of(sum/count);
        } else {
            return Optional.empty();
        }
    }
}
