package treap;

import java.util.List;
import java.util.Random;

import oplossing.Treap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RotateTest {

    @Test
    public void rotateRightTwo() {
        Treap<Integer> tree = new Treap<>(new Random(123456), Integer.MAX_VALUE);

        tree.add(2);
        tree.add(1);

        assertEquals(1, tree.root().getValue());
        assertEquals(2, tree.root().getRight().getValue());
        assertNull(tree.root().getLeft());
    }

    @Test
    public void rotateLeftTwo() {
        Treap<Integer> tree = new Treap<>(new Random(123456), Integer.MAX_VALUE);

        tree.add(1);
        tree.add(2);

        assertEquals(2, tree.root().getValue());
        assertEquals(1, tree.root().getLeft().getValue());
        assertNull(tree.root().getRight());
    }

    @Test
    public void rotateRightChildrenNoParent() {
        Treap<Integer> tree = new Treap<>(new Random(123456), Integer.MAX_VALUE);

        tree.add(1);
        assertEquals(1, tree.root().getValue());

        tree.add(2);
        assertEquals(2, tree.root().getValue());
        assertEquals(1, tree.root().getLeft().getValue());
        assertNull(tree.root().getRight());

        tree.add(4);
        assertEquals(4, tree.root().getValue());
        assertEquals(2, tree.root().getLeft().getValue());
        assertEquals(1, tree.root().getLeft().getLeft().getValue());

        tree.add(3);
        assertEquals(4, tree.root().getValue());
        assertEquals(2, tree.root().getLeft().getValue());
        assertEquals(1, tree.root().getLeft().getLeft().getValue());
        assertEquals(3, tree.root().getLeft().getRight().getValue());

        tree.add(6);
        assertEquals(4, tree.root().getValue());
        assertEquals(2, tree.root().getLeft().getValue());
        assertEquals(1, tree.root().getLeft().getLeft().getValue());
        assertEquals(3, tree.root().getLeft().getRight().getValue());
        assertEquals(6, tree.root().getRight().getValue());

        tree.add(5);
        assertEquals(5, tree.root().getRight().getLeft().getValue());

        tree.remove(4);
        assertEquals(2, tree.root().getValue());
        assertEquals(1, tree.root().getLeft().getValue());
        assertEquals(3, tree.root().getRight().getValue());
        assertEquals(6, tree.root().getRight().getRight().getValue());
        assertEquals(5, tree.root().getRight().getRight().getLeft().getValue());
    }

    @Test
    public void rotateLeftChildrenNoParent() {
        Treap<Integer> tree = new Treap<>(new Random(9876543), Integer.MAX_VALUE);
        for (int e : List.of(1, 4, 7, 0, 8, 9)) tree.add(e);

        assertEquals(9, tree.root().getValue());
        assertEquals(4, tree.root().getLeft().getValue());
        assertEquals(1, tree.root().getLeft().getLeft().getValue());
        assertEquals(0, tree.root().getLeft().getLeft().getLeft().getValue());
        assertEquals(8, tree.root().getLeft().getRight().getValue());
        assertEquals(7, tree.root().getLeft().getRight().getLeft().getValue());
        assertNull(tree.root().getRight());
    }
}
