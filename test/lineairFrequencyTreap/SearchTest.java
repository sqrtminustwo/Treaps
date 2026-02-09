package lineairFrequencyTreap;

import abstractTests.FrequencyTreapSearchTest;
import oplossing.LineairFrequencyTreap;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class SearchTest extends FrequencyTreapSearchTest<LineairFrequencyTreap<Integer>> {

    @Test
    public void smallTest() {
        LineairFrequencyTreap<Integer> treap = new LineairFrequencyTreap<>(new Random(20), 300);
        Random rg_elements = new Random(21);
        for (int i = 0; i < 10; i++) treap.add(rg_elements.nextInt(100));
        treap.search(78);
        assertEquals(153, getNodeByValue(treap.root(), 78).getPriority());
        treap.search(78);
        assertEquals(253, treap.root().getRight().getRight().getPriority());

        treap.search(44);
        assertEquals(105, getNodeByValue(treap.root(), 44).getPriority());
        treap.search(44);
        assertEquals(205, treap.root().getRight().getLeft().getPriority());
    }

    public void testIncrementWithConstant(int size) {
        LineairFrequencyTreap<Integer> treap = createTree();
        HashSet<Integer> elements = addAndGet(treap, size);
        assertTrue(validBinaryHeap(treap.root()));
        ReturnOfSearchGetPriorities res = searchGetPriorities(treap, elements);
        assertTrue(validBinaryHeap(treap.root()));
        long constant = res.new_priorities().get(res.searched().getFirst()) -
                        res.old_priorities().get(res.searched().getFirst());

        for (Integer value : res.old_priorities().keySet()) {
            assertEquals(
                constant,
                res.new_priorities().get(value) - res.old_priorities().get(value)
            );
        }
    }

    @Test
    public void testIncrementWithConstantSmall() {
        testIncrementWithConstant(200);
    }

    @Test
    public void testIncrementWithConstantLarge() {
        testIncrementWithConstant(2000);
    }

    @Override
    protected LineairFrequencyTreap<Integer> createTree() {
        return new LineairFrequencyTreap<>();
    }
}
