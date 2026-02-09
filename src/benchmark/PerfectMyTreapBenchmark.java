package benchmark;

import benchmark.executer.BenchmarkExecuter;
import benchmark.executer.functional.TreeCreator;
import benchmark.measurment.Measurer;
import oplossing.*;
import oplossing.abstractTree.AbstractTree;
import oplossing.node.MyNode;
import oplossing.node.MyPriorityNode;
import oplossing.node.abstractNode.AbstractNode;

import java.util.ArrayList;
import java.util.List;

public class PerfectMyTreapBenchmark<N extends AbstractNode<Integer, N>, T
                                         extends AbstractTree<Integer, N>>
    extends BenchmarkExecuter<T> {

    public PerfectMyTreapBenchmark(TreeCreator<T> treeCreator) { super(treeCreator); }

    @Override
    public long executeBenchmark(Measurer measurer, T tree, List<Integer> elements) {

        for (int e : elements) tree.add(e);

        int size = elements.size() / 100;

        List<Leaf> leafs = getLeafs(tree, size);

        int c = elements.size() / 10;

        long before = measurer.before();
        for (Leaf leaf : leafs) {
            // omdat er meerdere toppen van zelfde waarde mogelijk zijn kan het zijn dat het geen
            // blad is
            Integer value = leaf.value();
            long depth = leaf.depth();
            long to_search = depth + c;
            for (int i = 0; i < to_search; i++) tree.search(value);
        }
        long after = measurer.after(before, null);

        return after;
    }

    public static void main(String[] args) {
        List<Integer> sizes = new ArrayList<>();
        // 100000, 200000, ..., 1000000
        for (int i = 10; i <= 100; i += 10) sizes.add(i * 10000);

        PerfectMyTreapBenchmark<MyPriorityNode<Integer>, MyTreap<Integer>> benchmark1 =
            new PerfectMyTreapBenchmark<>(MyTreap::new);
        benchmark1.testMultipleTime(sizes, 3, "perfectMyTreapTime", "avg", false);

        System.gc();

        PerfectMyTreapBenchmark<MyPriorityNode<Integer>, Treap<Integer>> benchmark2 =
            new PerfectMyTreapBenchmark<>(Treap::new);
        benchmark2.testMultipleTime(sizes, 3, "perfectTreapTime", "avg", false);

        System.gc();

        PerfectMyTreapBenchmark<MyNode<Integer>, SemiSplayTree<Integer>> benchmark3 =
            new PerfectMyTreapBenchmark<>(SemiSplayTree::new);
        benchmark3.testMultipleTime(sizes, 3, "perfectSemiSplayTime", "avg", false);

        //        PerfectMyTreapBenchmark<MyPriorityNode<Integer>, LineairFrequencyTreap<Integer>>
        //        benchmark4 = new PerfectMyTreapBenchmark<>(LineairFrequencyTreap::new);
        //        benchmark4.testMultipleTime(sizes, 3, "perfectLineairFrequencyTime", "avg",
        //        false);
        //
        //        System.gc();
        //
        //        PerfectMyTreapBenchmark<MyFrequencyNode<Integer>, MyFrequencyTreap<Integer>>
        //        benchmark5 = new PerfectMyTreapBenchmark<>(MyFrequencyTreap::new);
        //        benchmark5.testMultipleTime(sizes, 3, "perfectMyFrequencyTime", "avg", false);
    }
}
