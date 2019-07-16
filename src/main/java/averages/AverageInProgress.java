package averages;

import java.util.Optional;

public class AverageInProgress {
    private double sum = 0;
    private long count = 0;

    private AverageInProgress() {}
    public static AverageInProgress initial() { return new AverageInProgress(); }

    public void updateWithNewData(double d) {
        sum = sum + d;
        count = count + 1;
    }

    public void merge(AverageInProgress other) {
        this.sum += other.sum;
        this.count += other.count;
    }

    public Optional<Double> get() {
        if (count > 0) {
            return Optional.of(sum / count);
        } else {
            return Optional.empty();
        }
    }
}

// collect ( Supplier that creates AverageInProgress object
//           BiConsumer that takes AverageInProgress and a Double
//           BiConsumer that takes TWO AverageInProgress
