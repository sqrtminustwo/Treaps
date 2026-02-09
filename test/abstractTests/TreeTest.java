package abstractTests;

import opgave.Node;
import opgave.SearchTree;

import opgave.samplers.Sampler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public abstract class TreeTest<T extends SearchTree<Integer>, N extends Node<Integer>>
    extends AbstractFunctions<Integer, N, T> {

    private final Sampler sampler = new Sampler(new Random(), 10000);

    public void extraCheck(T tree) { assertTrue(validBinaryTree(tree.root())); }

    public HashSet<Integer> addCheckReturn(T tree, int size) {
        HashSet<Integer> added = new HashSet<>(sampler.sample(size));

        for (int e : added) {
            assertFalse(tree.search(e));
            assertTrue(tree.add(e));
            assertTrue(tree.search(e));
            extraCheck(tree);
        }

        return added;
    }

    /*
     * ADD
     */

    @Test
    public void addOne() {
        T tree = createTree();

        assertFalse(tree.search(1));
        tree.add(1);
        assertTrue(tree.search(1));

        extraCheck(tree);
    }

    @Test
    public void addMultiple() {
        T tree = createTree();

        for (int i = 0; i < 5; i++) {
            assertTrue(tree.add(i));
            extraCheck(tree);
        }
        for (int i = 0; i < 5; i++) {
            assertTrue(tree.search(i), String.format("should contain %d", i));
            extraCheck(tree);
        }
    }

    @Test
    public void addExistend() {
        T tree = createTree();
        HashSet<Integer> added = addCheckReturn(tree, 200);

        for (int num : added) {
            assertTrue(tree.search(num));
            assertFalse(tree.add(num));
        }
    }

    @Test
    public void addLarge() {
        T tree = createTree();
        addCheckReturn(tree, 5000);
    }

    /*
     * REMOVE
     */

    @Test
    public void removeEmpty() {
        T tree = createTree();
        assertFalse(tree.remove(0));
        assertNull(tree.root());
        assertEquals(0, tree.size());

        extraCheck(tree);
    }

    public void removeRoot(int size) {
        T tree = createTree();

        assertNull(tree.root());
        assertFalse(tree.search(1));
        tree.add(1);
        assertTrue(tree.search(1));
        assertEquals(1, tree.size());
        tree.remove(1);
        assertFalse(tree.search(1));
        assertEquals(0, tree.size());
        assertNull(tree.root());

        size = addCheckReturn(tree, size).size();
        assertEquals(size, tree.size());
        int root = tree.root().getValue();

        while (tree.size() > 1) {
            tree.remove(root);
            assertNotEquals(root, tree.root().getValue());
            size--;
            assertEquals(size, tree.size());
            root = tree.root().getValue();
        }

        tree.remove(tree.root().getValue());

        assertNull(tree.root());
    }

    @Test
    public void removeRootSmall() {
        removeRoot(100);
    }

    @Test
    public void removeRootLarge() {
        removeRoot(1000);
    }

    @Test
    public void removeMultiple() {
        T tree = createTree();

        for (int i = 0; i < 5; i++) {
            assertTrue(tree.add(i), String.format("should change when adding %d", i));
        }
        for (int i = 0; i < 5; i++) {
            assertTrue(tree.search(i), String.format("should contain %d", i));
            assertTrue(tree.remove(i), String.format("should change when removing %d", i));
            assertFalse(tree.search(i), String.format("should not contain %d anymore", i));
            extraCheck(tree);
        }
        assertEquals(0, tree.size(), "should be empty");
    }

    @Test
    public void removeWithBothChildren() {
        int bound = 100;
        Random rg = new Random();

        // Maak een boom an zodat rechtse kind van wortel 2 kinderen heeft

        T tree = null;

        while (tree == null) {
            Random rg_elements = new Random(rg.nextInt());
            tree = createTree();
            for (int i = 0; i < 10; i++) tree.add(rg_elements.nextInt(bound));

            if (tree.root().getRight() == null || tree.root().getRight().getLeft() == null ||
                tree.root().getRight().getRight() == null)
                tree = null;
        }

        int size_before = tree.size();
        tree.remove(tree.root().getRight().getValue());
        assertEquals(size_before - 1, tree.size());

        extraCheck(tree);
    }

    @Test
    public void removeLarge() {
        T tree = createTree();
        HashSet<Integer> added = addCheckReturn(tree, 5000);
        ArrayList<Integer> added_l = new ArrayList<>(added);

        extraCheck(tree);

        while (!added.isEmpty()) {
            int e = added_l.get(rg.nextInt(added_l.size()));
            if (added.contains(e)) {
                assertTrue(tree.remove(e));
                assertFalse(tree.remove(e));
                added.remove(e);
            } else assertFalse(tree.remove(e));
            extraCheck(tree);
        }
    }

    @Test
    public void removeBool() {
        T tree = createTree();
        HashSet<Integer> added = addCheckReturn(tree, 2000);

        for (int e : added) {
            assertTrue(tree.remove(e));
            assertFalse(tree.remove(e));
        }

        extraCheck(tree);
    }

    /*
     * SEARCH
     */

    @Test
    public void searchEmpty() {
        T tree = createTree();
        assertFalse(tree.search(0));
        assertNull(tree.root());

        for (int i = 0; i < 10; i++) tree.add(i);
        for (int i = 0; i < 10; i++) tree.remove(i);

        for (int i = 0; i < 10; i++) assertFalse(tree.search(i));

        assertEquals(0, tree.size());

        extraCheck(tree);
    }

    @Test
    public void searchNonEmpty() {
        T tree = createTree();
        HashSet<Integer> added = addCheckReturn(tree, 500);
        for (int i : added) assertTrue(tree.search(i));
        extraCheck(tree);
    }

    @Test
    public void searchBool() {
        T tree = createTree();
        HashSet<Integer> added = addCheckReturn(tree, 5000);

        for (int e : added) {
            assertTrue(tree.search(e));
            tree.remove(e);
            assertFalse(tree.search(e));
        }

        extraCheck(tree);
    }

    @Test
    public void searchLarge() {
        T tree = createTree();
        int size = 5000;
        HashSet<Integer> added = addCheckReturn(tree, size);
        for (int e : added) {
            assertTrue(tree.search(e));
            extraCheck(tree);
        }

        int lower = getMax(tree) + 1;
        int upper = lower + size;
        for (int i = 0; i < size; i++) {
            int e = rg.nextInt(lower, upper);
            assertFalse(tree.search(e));
            extraCheck(tree);
        }
    }

    /*
     * SIZE
     */

    @Test
    public void sizeEmpty() {
        T tree = createTree();
        assertEquals(0, tree.size());

        for (int i = 0; i < 10; i++) tree.add(i);
        for (int i = 0; i < 10; i++) tree.remove(i);

        assertEquals(0, tree.size());

        extraCheck(tree);
    }

    @Test
    public void sizeNonEmpty() {
        T tree = createTree();
        int n = 1000;
        for (int i = 0; i < n; i++) {
            tree.add(i);
            assertEquals(tree.size(), i + 1);
        }
        extraCheck(tree);

        for (int i = n - 1; i >= 0; i--) {
            tree.remove(i);
            assertEquals(tree.size(), i);
        }
        extraCheck(tree);
    }

    /*
     * VALUES
     */

    @Test
    public void valuesEmpty() {
        T tree = createTree();
        assertTrue(tree.values().isEmpty());

        for (int i = 0; i < 10; i++) tree.add(i);
        for (int i = 0; i < 10; i++) tree.remove(i);

        assertTrue(tree.values().isEmpty());

        extraCheck(tree);
    }

    private void valuesTest(int size) {
        T tree = createTree();

        HashSet<Integer> elements = addCheckReturn(tree, size);

        List<Integer> values = tree.values();
        assertEquals(elements.size(), values.size());
        assertEquals(elements.stream().sorted().toList(), values);

        extraCheck(tree);
    }

    @Test
    public void valuesNonEmptySmall() {
        valuesTest(500);
    }

    @Test
    public void valuesNonEmptyLarge() {
        valuesTest(5000);
    }
}
