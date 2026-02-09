package myTreap;

import abstractTests.SemiSplayTest;
import oplossing.MyTreap;
import oplossing.SemiSplayTree;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTest extends SemiSplayTest<MyTreap<Integer>> {

    @Test
    public void threeNodesRestructureLine() {
        Random rg_priority = new Random(1282);
        int bound = 10;

        List<List<Integer>> tests = List.of(List.of(1, 2, 3), List.of(3, 2, 1));

        for (List<Integer> test : tests) {
            MyTreap<Integer> treap = new MyTreap<>(rg_priority, bound);
            for (int e : test) treap.add(e);
            treap.search(1);

            //    2
            //   / \
            //  1   3

            assertEquals(2, treap.root().getValue());
            assertEquals(1, treap.root().getLeft().getValue());
            assertEquals(3, treap.root().getRight().getValue());
        }
    }

    @Test
    public void threeNodesRestructureZigZag() {
        Random rg_priority = new Random(1290);
        int bound = 10;

        List<List<Integer>> tests = List.of(List.of(5, 6, 7), List.of(5, 6, 7));

        for (List<Integer> test : tests) {
            MyTreap<Integer> treap = new MyTreap<>(rg_priority, bound);
            for (int e : test) treap.add(e);
            treap.search(6);

            //    6
            //   / \
            //  5   7

            assertEquals(6, treap.root().getValue());
            assertEquals(5, treap.root().getLeft().getValue());
            assertEquals(7, treap.root().getRight().getValue());
        }
    }

    @Test
    public void smallTest() {
        MyTreap<Integer> treap = new MyTreap<>(new Random(20), 300);
        Random rg_elements = new Random(21);
        for (int i = 0; i < 20; i++) treap.add(rg_elements.nextInt(100));
        assertTrue(validBinaryHeap(treap.root()));
        treap.search(92);
        assertTrue(validBinaryHeap(treap.root()));
        assertEquals(27, treap.root().getValue());
        assertEquals(6, treap.root().getLeft().getValue());
        assertEquals(81, treap.root().getRight().getValue());
        assertEquals(92, treap.root().getRight().getRight().getValue());
        assertEquals(53, treap.root().getRight().getLeft().getValue());
        assertEquals(55, treap.root().getRight().getLeft().getRight().getValue());
    }

    @Override
    protected void action(SemiSplayTree<Integer> tree, Integer e) {
        tree.search(e);
    }

    @Override
    public MyTreap<Integer> createTree() {
        return new MyTreap<>();
    }
}
