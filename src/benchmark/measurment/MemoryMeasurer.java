package benchmark.measurment;

public class MemoryMeasurer implements Measurer<Void> {

    private final Runtime rt = Runtime.getRuntime();

    @Override
    public long before() {
        System.gc();
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {}

        return rt.totalMemory() - rt.freeMemory();
    }

    @Override
    public long after(long before, Void extra) {
        long after = rt.totalMemory() - rt.freeMemory();

        return (after - before) / 1024;
    }
}
