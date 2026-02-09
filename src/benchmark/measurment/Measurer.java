package benchmark.measurment;

public interface Measurer<T> {
    long before();

    long after(long before, T extra);
}
