package benchmark.executer.functional;

import opgave.SearchTree;

@FunctionalInterface
public interface TreeFunctionExecuter {
    void executeFunction(SearchTree<Integer> tree, Integer arg);
}
