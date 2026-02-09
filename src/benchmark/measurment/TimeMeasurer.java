package benchmark.measurment;

public class TimeMeasurer implements Measurer<Void> {

    @Override
    public long before() {
        return System.currentTimeMillis();
    }

    @Override
    public long after(long before, Void extra) {
        return System.currentTimeMillis() - before;
    }
}
