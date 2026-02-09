package semisplay;

import oplossing.SemiSplayTree;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

public class SearchTest extends SemiSplayAbstractTest {

    @Test
    public void searchOnEmpty() {
        SemiSplayTree<Integer> tree1 = new SemiSplayTree<>();
        assertFalse(tree1.search(0));
        assertFalse(tree1.search(-1));
        assertTrue(validBinaryTree(tree1.root()));

        SemiSplayTree<String> tree2 = new SemiSplayTree<>();
        assertFalse(tree2.search(" "));
        assertFalse(tree2.search("1337"));
    }

    @Test
    public void searchMultiple() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();

        for (int i = 0; i < 100; i++) tree.add(i);
        for (int i = 0; i < 100; i++) assertTrue(tree.search(i));
        for (int i = -100; i < 0; i++) assertFalse(tree.search(i));

        for (int i = 0; i < 100; i++) tree.remove(i);
        for (int i = -100; i < 100; i++) assertFalse(tree.search(i));
        assertTrue(validBinaryTree(tree.root()));
    }

    @Test
    public void splayExistendSearch() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();
        List<Integer> values = List.of(10, 20, 30, 40, 25, 23, 24, 21, 29, 41);
        for (int value : values) tree.add(value);

        tree.search(21);
        assertTrue(validBinaryTree(tree.root()));
        assertTrue(validBinaryTree(tree.root()));
        assertEquals(24, tree.root().getValue());
        assertEquals(21, tree.root().getLeft().getValue());
        assertEquals(20, tree.root().getLeft().getLeft().getValue());
        assertNull(tree.root().getLeft().getLeft().getRight());
        assertEquals(10, tree.root().getLeft().getLeft().getLeft().getValue());
        assertNull(tree.root().getLeft().getLeft().getLeft().getLeft());
        assertNull(tree.root().getLeft().getLeft().getLeft().getRight());
        assertEquals(23, tree.root().getLeft().getRight().getValue());
        assertNull(tree.root().getLeft().getRight().getLeft());
        assertNull(tree.root().getLeft().getRight().getRight());

        assertEquals(29, tree.root().getRight().getValue());
        assertEquals(25, tree.root().getRight().getLeft().getValue());
        assertNull(tree.root().getRight().getLeft().getLeft());
        assertNull(tree.root().getRight().getLeft().getRight());
        assertEquals(40, tree.root().getRight().getRight().getValue());
        assertEquals(30, tree.root().getRight().getRight().getLeft().getValue());
        assertNull(tree.root().getRight().getRight().getLeft().getLeft());
        assertNull(tree.root().getRight().getRight().getLeft().getRight());
        assertEquals(41, tree.root().getRight().getRight().getRight().getValue());
        assertNull(tree.root().getRight().getRight().getRight().getLeft());
        assertNull(tree.root().getRight().getRight().getRight().getRight());
    }

    @Test
    public void splayNonExistendSearch() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();
        List<Integer> values = List.of(10, 5, 20, 15, 11, 4, 30);
        for (int value : values) tree.add(value);

        assertFalse(tree.search(31));
        assertTrue(validBinaryTree(tree.root()));
        assertEquals(20, tree.root().getValue());
        assertEquals(11, tree.root().getLeft().getValue());
        assertEquals(15, tree.root().getLeft().getRight().getValue());
        assertEquals(5, tree.root().getLeft().getLeft().getValue());
        assertEquals(10, tree.root().getLeft().getLeft().getRight().getValue());
        assertEquals(4, tree.root().getLeft().getLeft().getLeft().getValue());
    }

    @Override
    protected void action(SemiSplayTree<Integer> tree, Integer e) {
        tree.search(e);
    }
}
