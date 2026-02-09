package abstractTests;

import opgave.Node;
import opgave.SearchTree;
import oplossing.SemiSplayTree;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class SemiSplayTest<T extends SearchTree<Integer>>
    extends AbstractFunctions<Integer, Node<Integer>, T> {

    protected List<Node<Integer>> getWayTo(Node<Integer> root, Integer value) {
        List<Node<Integer>> way = new ArrayList<>();
        Node<Integer> currentNode = root;

        while (currentNode != null) {
            way.add(currentNode);
            int compared = value.compareTo(currentNode.getValue());

            if (compared == 0) break;
            else currentNode = compared < 0 ? currentNode.getLeft() : currentNode.getRight();
        }

        return way;
    }

    public void nodeCloserToTop(int n) {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();
        HashSet<Integer> elements = addAndGet(tree, n);

        for (int e : elements) {
            int length_before = getWayTo(tree.root(), e).size();
            if (length_before >= 3) {
                action(tree, e);
                assertTrue(length_before > getWayTo(tree.root(), e).size());
            }
        }
    }

    protected abstract void action(SemiSplayTree<Integer> tree, Integer e);

    @Test
    public void closerToTopSmall() {
        nodeCloserToTop(200);
    }

    @Test
    public void closerToTopLarge() {
        nodeCloserToTop(2000);
    }
}
