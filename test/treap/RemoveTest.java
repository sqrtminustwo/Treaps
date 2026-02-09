package treap;

import oplossing.Treap;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RemoveTest {

    @Test
    public void removeRootWithBothChildrenSmall() {
        Random rg = new Random(-1268379515);
        Treap<Integer> tree = new Treap<>(new Random(1337), Integer.MAX_VALUE);

        for (int i = 0; i < 3; i++) tree.add(rg.nextInt(10));

        assertEquals(6, tree.root().getValue());
        assertEquals(5, tree.root().getLeft().getValue());
        assertEquals(9, tree.root().getRight().getValue());
        assertTrue(tree.root().getLeft().getPriority() > tree.root().getRight().getPriority());

        tree.remove(6);
        assertEquals(5, tree.root().getValue());
        assertEquals(9, tree.root().getRight().getValue());
        assertNull(tree.root().getLeft());
    }

    @Test
    public void removeWithNoChildrenBig() {
        Treap<Integer> tree = new Treap<>(new Random(23456), Integer.MAX_VALUE);
        Random rg = new Random(65432);
        int n = 20;
        int bound = 10 * n;
        for (int i = 0; i < n; i++) tree.add(rg.nextInt(bound));

        tree.remove(196);
        assertFalse(tree.search(196));
        assertNull(tree.root().getRight().getRight());

        tree.remove(156);
        assertFalse(tree.search(156));
        assertNull(tree.root().getLeft().getRight().getRight());
    }

    @Test
    public void removeWithOneChildBig() {
        Treap<Integer> tree = new Treap<>(new Random(34567), Integer.MAX_VALUE);
        Random rg = new Random(76543);
        int n = 20;
        int bound = 10 * n;
        for (int i = 0; i < n; i++) tree.add(rg.nextInt(bound));

        tree.remove(190);
        assertFalse(tree.search(190));
        assertEquals(145, tree.root().getRight().getRight().getValue());
        assertEquals(133, tree.root().getRight().getRight().getLeft().getValue());
        assertEquals(152, tree.root().getRight().getRight().getRight().getValue());

        tree.remove(37);
        assertFalse(tree.search(37));
        assertEquals(52, tree.root().getLeft().getRight().getValue());
        assertEquals(50, tree.root().getLeft().getRight().getLeft().getValue());
    }

    @Test
    public void removeWithBothChildren() {
        Treap<Integer> tree = new Treap<>(new Random(1234), Integer.MAX_VALUE);
        Random rg = new Random(4321);
        int n = 10;
        int bound = 10 * n;
        for (int i = 0; i < n; i++) tree.add(rg.nextInt(bound));

        tree.remove(18);
        assertEquals(36, tree.root().getRight().getLeft().getLeft().getLeft().getValue());
        assertEquals(23, tree.root().getRight().getLeft().getLeft().getLeft().getLeft().getValue());
        assertEquals(
            13,
            tree.root().getRight().getLeft().getLeft().getLeft().getLeft().getLeft().getValue()
        );
    }
}
