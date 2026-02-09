package benchmark.executer.functional;

import benchmark.executer.BenchmarkThread;
import opgave.SearchTree;

@FunctionalInterface
public interface TestFunctionExecuter<T extends SearchTree<Integer>> {
    BenchmarkThread<T> execute(int size, int times);
}
