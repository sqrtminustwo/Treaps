package benchmark;

import oplossing.*;
import benchmark.executer.BenchmarkExecuter;
import benchmark.executer.functional.TreeCreator;
import benchmark.measurment.Measurer;
import oplossing.abstractTree.AbstractTree;
import oplossing.node.MyFrequencyNode;
import oplossing.node.MyNode;
import oplossing.node.MyPriorityNode;
import oplossing.node.abstractNode.AbstractNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AverageBenchmark<N extends AbstractNode<Integer, N>, T
                                  extends AbstractTree<Integer, N>> extends BenchmarkExecuter<T> {

    private final Random rg = new Random();

    public AverageBenchmark(TreeCreator<T> treeCreator) { super(treeCreator); }

    @Override
    public long executeBenchmark(Measurer measurer, T tree, List<Integer> elements) {

        List<List<Integer>> partitions = getPartitions(elements, 10);

        long before = measurer.before();
        for (List<Integer> partition : partitions) {
            int size = partition.size();
            int sub_partition = partition.size() / 4;

            for (int e : partition) tree.add(e);
            for (int i = 0; i < sub_partition; i++) tree.search(partition.get(rg.nextInt(size)));
            for (int i = 0; i < sub_partition; i++) tree.remove(partition.get(rg.nextInt(size)));
        }
        long after = measurer.after(before, null);

        return after;
    }

    public record BoundIncrementers(String name, List<BoundIncrementer> incrementers) {}
    public record BoundIncrementer(String name, int incrementer, int bound) {}

    public static void main(String[] args) {
        List<Integer> sizes = new ArrayList<>();
        // 100000, 200000, ..., 1000000
        for (int i = 10; i <= 100; i += 10) sizes.add(i * 10000);

        //        List<Integer> bounds = List.of(10000, 100000, 1000000, 10000000,
        //        Integer.MAX_VALUE); for (int bound: bounds) {
        //            AverageBenchmark<MyFrequencyNode<Integer>, MyFrequencyTreap<Integer>>
        //            benchmark = new AverageBenchmark<>(
        //                    () -> new MyFrequencyTreap<>(bound)
        //            );
        //            benchmark.testMultipleTime(sizes, 3, "myFrequencyTreapBoundTime", "avgBound="
        //            + bound, false);
        //        }

        // Neem voor elke bound een aantal incrementers, plaats in grafieke, neem beste van elke
        // grafiek en vergelijk dan
        //        List<BoundIncrementer> bound1= List.of(
        //                new BoundIncrementer("bound=10000inc=100", 100, 10000),
        //                new BoundIncrementer("bound=10000inc=1000",1000,10000)
        //        );
        //        List<BoundIncrementer> bound2 = List.of(
        //                new BoundIncrementer("bound=100000inc=100", 100, 100000),
        //                new BoundIncrementer("bound=100000inc=1000",1000,100000),
        //                new BoundIncrementer("bound=100000inc=10000",10000,100000)
        //        );
        //        List<BoundIncrementer> bound3 = List.of(
        //                new BoundIncrementer("bound=1000000inc=100", 100, 1000000),
        //                new BoundIncrementer("bound=1000000inc=1000",1000,1000000),
        //                new BoundIncrementer("bound=1000000inc=10000",10000,1000000),
        //                new BoundIncrementer("bound=1000000inc=100000",100000,1000000)
        //        );
        //        List<BoundIncrementer> bound4 = List.of(
        //                new BoundIncrementer("bound=" + Integer.MAX_VALUE + "inc=100", 100,
        //                Integer.MAX_VALUE), new BoundIncrementer("bound=" + Integer.MAX_VALUE +
        //                "inc=1000",1000, Integer.MAX_VALUE), new BoundIncrementer("bound=" +
        //                Integer.MAX_VALUE + "inc=10000",10000, Integer.MAX_VALUE), new
        //                BoundIncrementer("bound=" + Integer.MAX_VALUE + "inc=100000",100000,
        //                Integer.MAX_VALUE), new BoundIncrementer("bound=" + Integer.MAX_VALUE +
        //                "inc=1000000",1000000, Integer.MAX_VALUE), new BoundIncrementer("bound=" +
        //                Integer.MAX_VALUE + "inc=10000000",10000000, Integer.MAX_VALUE)
        //        );
        //        List<BoundIncrementers> all = List.of(
        //                new BoundIncrementers("10000", bound1),
        //                new BoundIncrementers("100000", bound2),
        //                new BoundIncrementers("1000000", bound3),
        //                new BoundIncrementers(Integer.MAX_VALUE + "", bound4)
        //        );
        //
        //        MyWriter writer = new MyWriter();
        //        String baseName = "lineairFrequencyTreapBound";
        //        writer.createDirInBase(baseName);
        //        for (BoundIncrementers incrementers: all) {
        //
        //            String basePath = baseName + "/" + incrementers.name;
        //            writer.createDirInBase(basePath);
        //
        //            for (BoundIncrementer incrementer: incrementers.incrementers()) {
        //                AverageBenchmark<MyPriorityNode<Integer>, LineairFrequencyTreap<Integer>>
        //                benchmark = new AverageBenchmark<>(
        //                        () -> new LineairFrequencyTreap<>(incrementer.incrementer,
        //                        incrementer.bound)
        //                );
        //                benchmark.testMultipleTime(sizes, 3, basePath, incrementer.name, false);
        //            }
        //        }

        AverageBenchmark<MyPriorityNode<Integer>, Treap<Integer>> benchmark1 =
            new AverageBenchmark<>(Treap::new);
        benchmark1.testMultipleTime(sizes, 3, "averageTreapTime", "avg", false);

        System.gc();

        AverageBenchmark<MyPriorityNode<Integer>, MyTreap<Integer>> benchmark2 =
            new AverageBenchmark<>(MyTreap::new);
        benchmark2.testMultipleTime(sizes, 3, "averageMyTreapTime", "avg", false);

        System.gc();

        AverageBenchmark<MyNode<Integer>, SemiSplayTree<Integer>> benchmark4 =
            new AverageBenchmark<>(SemiSplayTree::new);
        benchmark4.testMultipleTime(sizes, 3, "averageSemiSplayTime", "avg", false);

        System.gc();

        AverageBenchmark<MyPriorityNode<Integer>, LineairFrequencyTreap<Integer>> benchmark5 =
            new AverageBenchmark<>(LineairFrequencyTreap::new);
        benchmark5.testMultipleTime(sizes, 3, "averageLineairFrequencyTime", "avg", false);

        System.gc();

        AverageBenchmark<MyFrequencyNode<Integer>, MyFrequencyTreap<Integer>> benchmark6 =
            new AverageBenchmark<>(MyFrequencyTreap::new);
        benchmark6.testMultipleTime(sizes, 3, "averageMyFrequencyTime", "avg", false);
    }
}
