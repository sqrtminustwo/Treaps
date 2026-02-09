package semisplay;

import opgave.SearchTree;
import oplossing.SemiSplayTree;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RemoveTest {

    @Test
    public void removeSmallNoSplay() {
        SearchTree<Integer> tree = new SemiSplayTree<>();
        tree.add(5);
        tree.add(4);
        tree.add(6);
        tree.add(7);

        tree.remove(4);
        assertEquals(6, tree.root().getValue());
        assertEquals(5, tree.root().getLeft().getValue());
        assertNull(tree.root().getLeft().getLeft());
        assertNull(tree.root().getLeft().getRight());
        assertEquals(7, tree.root().getRight().getValue());

        tree.add(8);
        tree.add(9);
        tree.remove(6);
        assertEquals(8, tree.root().getValue());
        assertEquals(7, tree.root().getLeft().getValue());
        assertEquals(5, tree.root().getLeft().getLeft().getValue());
        assertEquals(9, tree.root().getRight().getValue());
    }

    @Test
    public void removeLeafSmall() {
        SearchTree<Integer> tree = new SemiSplayTree<>();
        tree.add(5);
        tree.add(4);
        tree.add(6);

        tree.remove(6);
        assertEquals(5, tree.root().getValue());
        assertEquals(4, tree.root().getLeft().getValue());

        assertNull(tree.root().getRight());

        tree.add(6);
        tree.remove(4);
        assertEquals(5, tree.root().getValue());
        assertNull(tree.root().getLeft());

        assertEquals(6, tree.root().getRight().getValue());
    }

    @Test
    public void removeReplacesWithGreatestLeft1() {
        SearchTree<Integer> tree = new SemiSplayTree<>();

        tree.add(7);
        tree.add(1);
        tree.add(9);
        tree.add(3);
        tree.add(8);

        // should replace with left child
        tree.remove(8);

        //    3
        //   / \
        //  1   7
        //       \
        //        9
        assertEquals(3, tree.root().getValue());
        assertEquals(1, tree.root().getLeft().getValue());
        assertEquals(
            7,
            tree.root().getRight().getValue(),
            "should replace with greatest left child when removing"
        );
        assertEquals(9, tree.root().getRight().getRight().getValue());
    }

    @Test
    public void removeRootAndSplayToGreatestLeft() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();

        int n = 30;
        Random rg = new Random(18379);
        for (int i = 0; i < n; i++) tree.add(rg.nextInt(n * 10));

        tree.remove(31);
        assertEquals(30, tree.root().getValue());
        assertEquals(23, tree.root().getLeft().getValue());
        assertEquals(18, tree.root().getLeft().getLeft().getValue());
        assertEquals(15, tree.root().getLeft().getLeft().getLeft().getValue());
        assertEquals(24, tree.root().getLeft().getRight().getValue());

        assertEquals(68, tree.root().getRight().getValue());
    }

    @Test
    public void removeWithNoReplacingBig() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();
        int n = 20;
        Random rg = new Random(238476);
        for (int i = 0; i < n; i++) tree.add(rg.nextInt(n * 10));

        tree.remove(6);
        assertNull(tree.root().getLeft().getLeft().getLeft());
    }

    @Test
    public void removeReplaceOneChildNoSplaying() {
        // Wanneer je een top verwijdert met maar één kind, moet je dit kind niet opnemen in het
        // semi-splaydpad.
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();
        int n = 20;
        Random rg = new Random(85465);
        for (int i = 0; i < n; i++) tree.add(rg.nextInt(n * 10));

        tree.remove(170);
        assertEquals(139, tree.root().getValue());
        assertEquals(167, tree.root().getRight().getValue());
        assertEquals(176, tree.root().getRight().getRight().getValue());
    }

    @Test
    public void removeReplaceGreatestLeft2() {
        // als de sleutel in een top met twee kinderen zit en
        // dus een andere sleutel in de top geplaatst wordt, telt het pad naar deze
        // andere sleutel mee
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();
        int n = 30;
        Random rg = new Random(129487);
        for (int i = 0; i < n; i++) tree.add(rg.nextInt(n * 10));

        tree.remove(90);
        assertEquals(81, tree.root().getValue());
        assertEquals(73, tree.root().getLeft().getValue());
        assertEquals(80, tree.root().getLeft().getRight().getValue());
        assertEquals(189, tree.root().getRight().getValue());
        assertEquals(170, tree.root().getRight().getLeft().getValue());
    }

    @Test
    public void removeWithReplacing() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();
        List<Integer> values = List.of(12, 10, 13, 0, 11, 1);
        for (int value : values) tree.add(value);

        tree.remove(0);
        assertEquals(11, tree.root().getValue());
        assertEquals(1, tree.root().getLeft().getValue());
        assertNull(tree.root().getLeft().getLeft());
        assertEquals(10, tree.root().getLeft().getRight().getValue());

        assertEquals(12, tree.root().getRight().getValue());
        assertEquals(13, tree.root().getRight().getRight().getValue());

        tree.remove(1);
        assertEquals(11, tree.root().getValue());
        assertEquals(10, tree.root().getLeft().getValue());
        assertNull(tree.root().getLeft().getLeft());
        assertNull(tree.root().getLeft().getRight());

        assertEquals(12, tree.root().getRight().getValue());
        assertEquals(13, tree.root().getRight().getRight().getValue());

        tree.remove(11);
        assertEquals(10, tree.root().getValue());
        assertNull(tree.root().getLeft());

        assertEquals(12, tree.root().getRight().getValue());
        assertEquals(13, tree.root().getRight().getRight().getValue());

        tree.remove(10);
        assertEquals(12, tree.root().getValue());
        assertNull(tree.root().getLeft());

        assertEquals(13, tree.root().getRight().getValue());

        tree.remove(12);
        assertEquals(13, tree.root().getValue());
        assertNull(tree.root().getLeft());

        assertNull(tree.root().getRight());

        tree.remove(13);
        assertNull(tree.root());
    }

    @Test
    public void removeWithNoReplacingButSplaying() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();
        List<Integer> values = List.of(1, 2, 3, 4, 5, 6);
        for (int value : values) tree.add(value);

        tree.remove(1);
        assertEquals(5, tree.root().getValue());
        assertEquals(3, tree.root().getLeft().getValue());
        assertEquals(2, tree.root().getLeft().getLeft().getValue());
        assertNull(tree.root().getLeft().getLeft().getLeft());
        assertNull(tree.root().getLeft().getLeft().getRight());
        assertEquals(4, tree.root().getLeft().getRight().getValue());
        assertNull(tree.root().getLeft().getRight().getLeft());
        assertNull(tree.root().getLeft().getRight().getRight());

        assertEquals(6, tree.root().getRight().getValue());
        assertNull(tree.root().getRight().getLeft());
        assertNull(tree.root().getRight().getRight());

        tree.remove(2);
        assertEquals(5, tree.root().getValue());
        assertEquals(3, tree.root().getLeft().getValue());
        assertNull(tree.root().getLeft().getLeft());
        assertEquals(4, tree.root().getLeft().getRight().getValue());
        assertNull(tree.root().getLeft().getRight().getLeft());
        assertNull(tree.root().getLeft().getRight().getRight());

        assertEquals(6, tree.root().getRight().getValue());
        assertNull(tree.root().getRight().getLeft());
        assertNull(tree.root().getRight().getRight());

        tree.remove(4);
        assertEquals(5, tree.root().getValue());
        assertEquals(3, tree.root().getLeft().getValue());
        assertNull(tree.root().getLeft().getLeft());
        assertNull(tree.root().getLeft().getRight());

        assertEquals(6, tree.root().getRight().getValue());
        assertNull(tree.root().getRight().getLeft());
        assertNull(tree.root().getRight().getRight());
    }

    @Test
    public void removeWithOneChildAndMultiple() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();
        List<Integer> values = List.of(50, 30, 70, 20, 40, 35, 80);
        for (int value : values) tree.add(value);

        tree.remove(30);
        assertEquals(40, tree.root().getValue());
        assertEquals(35, tree.root().getLeft().getValue());
        assertEquals(20, tree.root().getLeft().getLeft().getValue());
        assertNull(tree.root().getLeft().getLeft().getLeft());
        assertNull(tree.root().getLeft().getLeft().getRight());

        assertEquals(70, tree.root().getRight().getValue());
        assertEquals(80, tree.root().getRight().getRight().getValue());
        assertEquals(50, tree.root().getRight().getLeft().getValue());
        assertNull(tree.root().getRight().getLeft().getLeft());
        assertNull(tree.root().getRight().getLeft().getRight());
        assertNull(tree.root().getRight().getRight().getLeft());
        assertNull(tree.root().getRight().getRight().getRight());

        tree.remove(40);
        assertEquals(35, tree.root().getValue());
        assertEquals(20, tree.root().getLeft().getValue());
        assertEquals(70, tree.root().getRight().getValue());
        assertEquals(50, tree.root().getRight().getLeft().getValue());
        assertEquals(80, tree.root().getRight().getRight().getValue());
    }

    @Test
    public void removeNonExistend() {
        SemiSplayTree<Integer> tree = new SemiSplayTree<>();
        List<Integer> values = List.of(1, 2, 3, 4, 5);
        for (Integer value : values) tree.add(value);

        tree.remove(0);
        assertEquals(4, tree.root().getValue());
        assertEquals(2, tree.root().getLeft().getValue());
        assertEquals(1, tree.root().getLeft().getLeft().getValue());
        assertNull(tree.root().getLeft().getLeft().getLeft());
        assertNull(tree.root().getLeft().getLeft().getRight());
        assertEquals(3, tree.root().getLeft().getRight().getValue());
        assertNull(tree.root().getLeft().getRight().getLeft());
        assertNull(tree.root().getLeft().getRight().getRight());

        assertEquals(5, tree.root().getRight().getValue());
        assertNull(tree.root().getRight().getRight());
        assertNull(tree.root().getRight().getLeft());
    }

    @Test
    public void removeNonInteger() {
        SemiSplayTree<String> tree = new SemiSplayTree<>();
        List<String> values = List.of("appel", "aardbei", "banaan", "peer", "kiwi", "mandarijn");
        for (String value : values) tree.add(value);

        tree.remove("banaan");
        assertEquals("mandarijn", tree.root().getValue());
        assertEquals("kiwi", tree.root().getLeft().getValue());
        assertNull(tree.root().getLeft().getRight());
        assertEquals("appel", tree.root().getLeft().getLeft().getValue());
        assertEquals("aardbei", tree.root().getLeft().getLeft().getLeft().getValue());

        assertEquals("peer", tree.root().getRight().getValue());
        assertNull(tree.root().getRight().getLeft());
        assertNull(tree.root().getRight().getRight());

        tree.remove("aardbei");
        assertEquals("kiwi", tree.root().getValue());
        assertEquals("appel", tree.root().getLeft().getValue());
        assertNull(tree.root().getLeft().getLeft());
        assertNull(tree.root().getLeft().getRight());

        assertEquals("mandarijn", tree.root().getRight().getValue());
        assertEquals("peer", tree.root().getRight().getRight().getValue());
        assertNull(tree.root().getRight().getLeft());
    }
}
