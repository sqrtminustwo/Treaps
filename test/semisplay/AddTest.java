package semisplay;

import opgave.Node;
import opgave.SearchTree;
import oplossing.SemiSplayTree;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class AddTest extends SemiSplayAbstractTest {

    @Test
    public void noSplayWhenNotDeep() {
        SearchTree<Integer> tree = new SemiSplayTree<>();

        tree.add(7);
        tree.add(1);
        tree.add(9);

        assertTrue(validBinaryTree(tree.root()));
        // search with depth < 2 should not splay
        assertTrue(tree.search(9));
        assertTrue(validBinaryTree(tree.root()));

        //    7
        //   / \
        //  1   9
        assertEquals(7, tree.root().getValue());
        assertEquals(1, tree.root().getLeft().getValue());
        assertEquals(9, tree.root().getRight().getValue());
    }

    @Test
    public void threeNodesRestructureRight() {
        SemiSplayTree<Integer> tree;
        List<List<Integer>> tests = List.of(List.of(5, 6, 7), List.of(5, 7, 6));

        for (List<Integer> test : tests) {
            tree = new SemiSplayTree<>();
            for (Integer e : test) tree.add(e);
            assertEquals(6, tree.root().getValue());
            assertEquals(5, tree.root().getLeft().getValue());
            assertEquals(7, tree.root().getRight().getValue());
            assertTrue(validBinaryTree(tree.root()));
        }
    }

    @Test
    public void threeNodesRestructureLeft() {
        SemiSplayTree<Integer> tree;
        List<List<Integer>> tests = List.of(List.of(5, 3, 4), List.of(5, 4, 3));

        for (List<Integer> test : tests) {
            tree = new SemiSplayTree<>();
            for (Integer e : test) tree.add(e);
            assertEquals(4, tree.root().getValue());
            assertEquals(3, tree.root().getLeft().getValue());
            assertEquals(5, tree.root().getRight().getValue());
            assertTrue(validBinaryTree(tree.root()));
        }
    }

    @Test
    public void fourNodesShouldSplay() {
        SearchTree<Integer> tree = new SemiSplayTree<>();

        tree.add(7);
        tree.add(1);
        tree.add(9);

        // first path of 3 nodes, should splay
        tree.add(3);

        assertTrue(validBinaryTree(tree.root()));
        //    3
        //   / \
        //  1   7
        //       \
        //        9
        assertEquals(3, tree.root().getValue());
        assertEquals(1, tree.root().getLeft().getValue());
        assertEquals(7, tree.root().getRight().getValue());
        assertEquals(9, tree.root().getRight().getRight().getValue());
    }

    @Test
    public void checkWithExample() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();

        for (int i : List.of(5, 7, 2, 6, 1, 3, 8, 4, 9, 0, 11, 15, 13, 12)) { tree.add(i); }

        assertTrue(validBinaryTree(tree.root()));
        assertEquals(6, tree.root().getValue());
        assertEquals(3, tree.root().getLeft().getValue());
        assertEquals(1, tree.root().getLeft().getLeft().getValue());
        assertEquals(4, tree.root().getLeft().getRight().getValue());
        assertEquals(0, tree.root().getLeft().getLeft().getLeft().getValue());
        assertEquals(2, tree.root().getLeft().getLeft().getRight().getValue());
        assertEquals(5, tree.root().getLeft().getRight().getRight().getValue());

        assertEquals(12, tree.root().getRight().getValue());
        assertEquals(11, tree.root().getRight().getLeft().getValue());
        assertEquals(13, tree.root().getRight().getRight().getValue());
        assertEquals(9, tree.root().getRight().getLeft().getLeft().getValue());
        assertEquals(15, tree.root().getRight().getRight().getRight().getValue());
        assertEquals(8, tree.root().getRight().getLeft().getLeft().getLeft().getValue());
        assertEquals(7, tree.root().getRight().getLeft().getLeft().getLeft().getLeft().getValue());
    }

    @Test
    public void addExistend() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();
        for (int i : List.of(10, 8, 12, 13, 7, 14)) { tree.add(i); }

        // Moet splayen
        tree.add(10);
        assertTrue(validBinaryTree(tree.root()));

        assertEquals(13, tree.root().getValue());
        assertEquals(10, tree.root().getLeft().getValue());
        assertEquals(12, tree.root().getLeft().getRight().getValue());
        assertNull(tree.root().getLeft().getRight().getLeft());
        assertNull(tree.root().getLeft().getRight().getRight());
        assertEquals(8, tree.root().getLeft().getLeft().getValue());
        assertNull(tree.root().getLeft().getLeft().getRight());
        assertEquals(7, tree.root().getLeft().getLeft().getLeft().getValue());
        assertNull(tree.root().getLeft().getLeft().getLeft().getLeft());
        assertNull(tree.root().getLeft().getLeft().getLeft().getRight());

        assertEquals(14, tree.root().getRight().getValue());
        assertNull(tree.root().getRight().getLeft());
        assertNull(tree.root().getRight().getRight());
    }

    public void addNNumbers(int n) {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();
        for (int i = 0; i <= n; i++) tree.add(i);

        tree.add(0);

        int startValue;
        if (n % 2 != 0) {
            startValue = n - 2;
            assertEquals(n - 2, tree.root().getValue());
            assertEquals(n - 1, tree.root().getRight().getValue());
            assertEquals(n, tree.root().getRight().getRight().getValue());
        } else {
            startValue = n - 1;
            assertEquals(n - 1, tree.root().getValue());
            assertEquals(n, tree.root().getRight().getValue());
        }

        Node<Integer> node = tree.root();
        for (int i = startValue; i > 0; i -= 2) {
            assertEquals(i, node.getValue());
            assertEquals(i + 1, node.getRight().getValue());
            node = node.getLeft();
        }
        assertEquals(0, node.getValue());
    }

    @Test
    public void addEvenAmountOfNumbers() {
        addNNumbers(1000000);
    }
    @Test
    public void addOddAmountOfNumbers() {
        addNNumbers(1000011);
    }

    @Override
    protected void action(SemiSplayTree<Integer> tree, Integer e) {
        tree.add(e);
    }
}
