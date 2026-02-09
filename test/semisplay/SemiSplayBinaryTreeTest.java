package semisplay;

import abstractTests.TreeTest;
import opgave.Node;
import opgave.SearchTree;
import oplossing.SemiSplayTree;

public class SemiSplayBinaryTreeTest extends TreeTest<SearchTree<Integer>, Node<Integer>> {

    @Override
    public SearchTree<Integer> createTree() {
        return new SemiSplayTree<>();
    }
}
