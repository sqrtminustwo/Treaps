package benchmark.executer;

import benchmark.executer.functional.Executer;
import benchmark.executer.functional.TestFunctionExecuter;
import benchmark.executer.functional.TreeCreator;
import benchmark.measurment.VisitsMeasurer;
import opgave.Node;
import opgave.SearchTree;
import benchmark.measurment.Measurer;
import benchmark.measurment.MemoryMeasurer;
import benchmark.measurment.TimeMeasurer;
import oplossing.abstractTree.AbstractTree;
import oplossing.node.abstractNode.AbstractNode;

import java.util.*;
import java.util.concurrent.*;

public abstract class BenchmarkExecuter<T extends SearchTree<Integer>>
    extends MyWriter implements Executer<T> {

    public Random rg;
    protected int seed;
    private final TreeCreator<T> treeCreator;

    public record ResultAndSize(long result, int size) {}

    public BenchmarkExecuter(TreeCreator<T> treeCreator) {
        this.treeCreator = treeCreator;
        rg = new Random();
        this.seed = -1;
    }
    public BenchmarkExecuter(TreeCreator<T> treeCreator, int seed) {
        this(treeCreator);
        rg = new Random(seed);
        this.seed = seed;
    }

    /*
     * Uitvoeren van benchmarks
     */

    public abstract long executeBenchmark(Measurer measurer, T tree, List<Integer> elements);

    public BenchmarkThread<T> testSingleTime(int times, int size) {
        Measurer measurer = new TimeMeasurer();

        return new BenchmarkThread<>(this, treeCreator, measurer, times, size, seed);
    }

    public BenchmarkThread<T> testSingleMemory(int times, int size) {
        Measurer measurer = new MemoryMeasurer();
        return new BenchmarkThread<>(this, treeCreator, measurer, times, size, seed);
    }

    public <N extends AbstractNode<Integer, N>, MT extends AbstractTree<Integer, N>>
        BenchmarkThread<T> testSingleVisits(int times, int size) {
        Measurer<MT> measurer = new VisitsMeasurer<>();
        return new BenchmarkThread<>(this, treeCreator, measurer, times, size, seed);
    }

    public SortedMap<Integer, Long> testMultiple(
        List<Integer> sizes, int times, String dir, String fileNameAvg, String header,
        TestFunctionExecuter<T> testFunctionExecuter
    ) {
        ExecutorService executor = Executors.newFixedThreadPool(sizes.size());
        //        ExecutorService executor =
        //                Executors.newFixedThreadPool(1);
        CompletionService<ResultAndSize> completionService =
            new ExecutorCompletionService<>(executor);

        for (int size : sizes) {
            completionService.submit(testFunctionExecuter.execute(times, size));
        }

        int received = 0;

        SortedMap<Integer, Long> result = new TreeMap<>();

        while (received < sizes.size()) {
            try {
                Future<ResultAndSize> resultFuture = completionService.take();

                ResultAndSize res = resultFuture.get();

                result.put(res.size(), res.result());

                received++;
                System.out.printf("%d/%d fnished%n", received, sizes.size());

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            } catch (ExecutionException e) { throw new RuntimeException(e.getCause()); }
        }

        executor.shutdown();

        writeResultToFile(result, dir, fileNameAvg, header);

        return result;
    }

    public SortedMap<Integer, Long> testMultipleTime(
        List<Integer> sizes, int times, String dir, String fileNameAvg, boolean fixed_seed
    ) {
        return testMultiple(
            sizes,
            times,
            dir,
            fileNameAvg,
            "size" + delimiter + "avgTime(ms)",
            this::testSingleTime
        );
    }

    public SortedMap<Integer, Long> testMultipleMemory(
        List<Integer> sizes, int times, String dir, String fileNameAvg, boolean fixed_seed
    ) {
        return testMultiple(
            sizes,
            times,
            dir,
            fileNameAvg,
            "size" + delimiter + "avgMemory(kB)",
            this::testSingleMemory
        );
    }

    public SortedMap<Integer, Long> testMultipleVisits(
        List<Integer> sizes, int times, String dir, String fileNameAvg, boolean fixed_seed
    ) {
        // Aantal visits is altijd dezelfde, dus avg is nutteloos
        return testMultiple(
            sizes,
            times,
            dir,
            fileNameAvg,
            "size" + delimiter + "visits",
            this::testSingleVisits
        );
    }

    /*
     * Helper functies
     */

    protected List<List<Integer>> getPartitions(List<Integer> elements, int number_of_partitions) {
        List<List<Integer>> partitions = new ArrayList<>();
        int size = elements.size() / number_of_partitions;
        for (int i = 0; i < number_of_partitions; i++) {
            partitions.add(elements.subList(i * size, (i + 1) * size));
        }

        return partitions;
    }

    protected record Leaf(Integer value, long depth, boolean isLeft) {}

    private boolean hasChildren(Node<Integer> node) {
        return node.getLeft() != null || node.getRight() != null;
    }
    private boolean hasBoth(Node<Integer> node) {
        return node.getLeft() != null && node.getRight() != null;
    }
    private Node<Integer> getNext(Node<Integer> node) {
        if (hasBoth(node)) {
            int left = rg.nextInt(2);
            if (left == 0) return node.getLeft();
            return node.getRight();
        }

        if (node.getRight() == null) return node.getLeft();
        return node.getRight();
    }

    protected Leaf randomLeaf(T treap) {
        long depth = 0;
        Node<Integer> current = treap.root();

        while (hasChildren(current)) {
            depth++;
            current = getNext(current);
        }

        return new Leaf(current.getValue(), depth, current.getValue() <= treap.root().getValue());
    }

    protected List<Leaf> getLeafs(T treap, int size) {
        List<Leaf> leafs = new ArrayList<>();

        for (int i = 0; i < size; i++) leafs.add(randomLeaf(treap));

        return leafs;
    }

    public static void executeListOfRunnables(List<Runnable> runnables) {
        ExecutorService executor = Executors.newFixedThreadPool(runnables.size());
        runnables.forEach(executor::submit);
        executor.shutdown();
    }
}
