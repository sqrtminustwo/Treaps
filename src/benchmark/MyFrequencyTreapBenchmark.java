package benchmark;

import benchmark.executer.BenchmarkExecuter;
import benchmark.executer.functional.TreeCreator;
import benchmark.measurment.Measurer;
import oplossing.LineairFrequencyTreap;
import oplossing.MyFrequencyTreap;
import oplossing.abstractTree.AbstractTreap;
import oplossing.node.MyFrequencyNode;
import oplossing.node.MyPriorityNode;
import oplossing.node.abstractNode.AbstractPriorityNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;

public class MyFrequencyTreapBenchmark<N extends AbstractPriorityNode<Integer, N>, T
                                           extends AbstractTreap<Integer, N>>
    extends BenchmarkExecuter<T> {

    public MyFrequencyTreapBenchmark(TreeCreator<T> treeCreator, int seed) {
        super(treeCreator, seed);
    }
    public MyFrequencyTreapBenchmark(TreeCreator<T> treeCreator) { super(treeCreator); }

    @Override
    public long executeBenchmark(Measurer measurer, T tree, List<Integer> elements) {
        for (int e : elements) tree.add(e);

        Leaf e = randomLeaf(tree);
        for (int j = 0; j < 2000; j++) tree.search(e.value());
        return measurer.after(0, tree);
    }

    public static void main(String[] args) {
        List<Integer> sizes = new ArrayList<>();

        // [10 000 000, 20 000 000]
        for (int i = 10; i <= 20; i++) { sizes.add(i * 1000000); }

        int seed_tree = 1973;
        int seed_benchmark = 1941;
        int bound = 10000;
        MyFrequencyTreapBenchmark<MyFrequencyNode<Integer>, MyFrequencyTreap<Integer>> benchmark1 =
            new MyFrequencyTreapBenchmark<>(
                ()
                    -> new MyFrequencyTreap<>(new Random(seed_tree), bound),
                seed_benchmark
            );
        SortedMap<Integer, Long> res1 = benchmark1.testMultipleVisits(
            sizes,
            1,
            "myAndlineairDiffVisits",
            "myFrequencyTreapVisits",
            true
        );
        System.gc();
        MyFrequencyTreapBenchmark<MyPriorityNode<Integer>, LineairFrequencyTreap<Integer>>
            benchmark2 = new MyFrequencyTreapBenchmark<>(
                ()
                    -> new LineairFrequencyTreap<>(new Random(seed_tree), bound),
                seed_benchmark
            );
        SortedMap<Integer, Long> res2 = benchmark2.testMultipleVisits(
            sizes,
            1,
            "myAndlineairDiffVisits",
            "lineairFrequencyTreapVisits",
            true
        );
        benchmark1.writeDifference(
            res2,
            res1,
            "myAndlineairDiffVisits",
            "diffMyANDLineair",
            "size,visits"
        );
    }
}
