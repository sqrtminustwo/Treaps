package benchmark.executer.functional;

import opgave.SearchTree;

@FunctionalInterface
public interface TreeCreator<T extends SearchTree<Integer>> {

    T createTree();
}
