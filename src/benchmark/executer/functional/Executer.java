package benchmark.executer.functional;

import benchmark.measurment.Measurer;
import opgave.SearchTree;

import java.util.List;

public interface Executer<T extends SearchTree<Integer>> {

    long executeBenchmark(Measurer measurer, T tree, List<Integer> elements);
}
