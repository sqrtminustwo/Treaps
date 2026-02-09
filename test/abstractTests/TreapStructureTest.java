package abstractTests;

import opgave.PriorityNode;
import opgave.PrioritySearchTree;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class TreapStructureTest<T extends PrioritySearchTree<Integer>>
    extends TreeTest<T, PriorityNode<Integer>> {

    @Test
    public void prioritiesNonNegative() {
        T treap = createTree();
        HashSet<Integer> added = addCheckReturn(treap, 10000);

        for (int e : added) {
            PriorityNode<Integer> node = getNodeByValue(treap.root(), e);
            if (node != null) assertTrue(node.getPriority() >= 0);
        }

        extraCheck(treap);
    }

    @Override
    public void extraCheck(T tree) {
        assertTrue(validBinaryHeap(tree.root()));
    }
}
