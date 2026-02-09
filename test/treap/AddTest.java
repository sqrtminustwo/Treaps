package treap;

import oplossing.Treap;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class AddTest {

    @Test
    public void addOne() {
        Treap<Integer> treap = new Treap<>();
        assertEquals(0, treap.size());
        treap.add(0);
        assertTrue(treap.search(0));
        assertTrue(treap.root().getPriority() >= 0);
    }

    @Test
    public void addRotateRightMultiple() {
        Random rg_priority = new Random(95959595);
        int bound = 20;
        Treap<Integer> treap = new Treap<>(rg_priority, bound);

        for (int e : List.of(4, 7, 2, 8, 5)) treap.add(e);

        assertEquals(7, treap.root().getValue());
        assertEquals(4, treap.root().getLeft().getValue());
        assertEquals(2, treap.root().getLeft().getLeft().getValue());
        assertEquals(5, treap.root().getLeft().getRight().getValue());
        assertEquals(8, treap.root().getRight().getValue());

        treap.add(1);
        assertEquals(7, treap.root().getValue());
        assertEquals(1, treap.root().getLeft().getValue());
        assertEquals(4, treap.root().getLeft().getRight().getValue());
        assertEquals(2, treap.root().getLeft().getRight().getLeft().getValue());
        assertEquals(5, treap.root().getLeft().getRight().getRight().getValue());
    }

    @Test
    public void addRotateLeftMultiple() {
        Random rg_priority = new Random(18237456);
        Random rg_element = new Random(76767676);
        int bound = 20;
        Treap<Integer> treap = new Treap<>(rg_priority, bound);

        for (int i = 0; i < 6; i++) treap.add(rg_element.nextInt(bound));

        assertEquals(11, treap.root().getValue());
        assertEquals(5, treap.root().getLeft().getValue());
        assertEquals(7, treap.root().getLeft().getRight().getValue());

        assertEquals(15, treap.root().getRight().getValue());
        assertEquals(14, treap.root().getRight().getLeft().getValue());
        assertEquals(18, treap.root().getRight().getRight().getValue());

        treap.add(rg_element.nextInt(bound));
        assertEquals(11, treap.root().getValue());
        assertEquals(19, treap.root().getRight().getValue());
        assertEquals(15, treap.root().getRight().getLeft().getValue());
        assertEquals(14, treap.root().getRight().getLeft().getLeft().getValue());
        assertEquals(18, treap.root().getRight().getLeft().getRight().getValue());
    }
}
