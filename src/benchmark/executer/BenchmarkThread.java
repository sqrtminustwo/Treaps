package benchmark.executer;

import benchmark.executer.functional.Executer;
import benchmark.executer.functional.TreeCreator;
import benchmark.measurment.Measurer;
import opgave.SearchTree;
import opgave.samplers.Sampler;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

public class BenchmarkThread<T extends SearchTree<Integer>>
    implements Callable<BenchmarkExecuter.ResultAndSize> {

    private final Executer<T> executer;
    private final TreeCreator<T> treeCreator;
    private final Measurer measurer;
    private final int times;
    private final int size;
    private final int seed;

    public BenchmarkThread(
        Executer<T> executer, TreeCreator<T> treeCreator, Measurer measurer, int times, int size,
        int seed
    ) {
        this.executer = executer;
        this.treeCreator = treeCreator;
        this.measurer = measurer;
        this.times = times;
        this.size = size;
        this.seed = seed;
    }

    @Override
    public BenchmarkExecuter.ResultAndSize call() throws Exception {
        Sampler sampler = new Sampler(seed > 0 ? new Random(seed) : new Random(), size);

        long total = 0;

        for (int t = 0; t < times; t++) {
            List<Integer> elements = sampler.sample(size);

            T tree = treeCreator.createTree();

            long measurment = executer.executeBenchmark(measurer, tree, elements);

            System.gc();

            total += measurment;
        }

        long avg = total / times;

        System.gc();

        return new BenchmarkExecuter.ResultAndSize(avg, size);
    }
}
