package myFrequencyTreap;

import abstractTests.FrequencyTreapSearchTest;
import oplossing.MyFrequencyTreap;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTest extends FrequencyTreapSearchTest<MyFrequencyTreap<Integer>> {

    @Test
    public void smallTest() {
        MyFrequencyTreap<Integer> treap = new MyFrequencyTreap<>(new Random(20), 300);
        Random rg_elements = new Random(21);
        for (int i = 0; i < 10; i++) treap.add(rg_elements.nextInt(100));
        treap.search(78);
        assertEquals(63, getNodeByValue(treap.root(), 78).getPriority());
        treap.search(78);
        assertEquals(83, treap.root().getRight().getRight().getLeft().getPriority());

        for (int i = 0; i < 6; i++) treap.search(44);
        assertEquals(215, treap.root().getRight().getLeft().getPriority());
    }

    public void testExponantialIncrement(int size) {
        MyFrequencyTreap<Integer> treap = createTree();
        HashSet<Integer> elements = addAndGet(treap, size);
        List<Integer> to_seach = chooseRandom(elements);

        ReturnOfSearchGetPriorities res1 = searchGetPriorities(treap, to_seach);
        long constant1 = res1.new_priorities().get(res1.searched().getFirst()) -
                         res1.old_priorities().get(res1.searched().getFirst());

        for (Integer value : res1.old_priorities().keySet()) {
            assertEquals(
                constant1,
                res1.new_priorities().get(value) - res1.old_priorities().get(value)
            );
        }

        ReturnOfSearchGetPriorities res2 = searchGetPriorities(treap, to_seach);
        long constant2 = res2.new_priorities().get(res2.searched().getFirst()) -
                         res2.old_priorities().get(res2.searched().getFirst());
        assertTrue(constant1 < constant2);

        for (Integer value : res1.old_priorities().keySet()) {
            assertEquals(
                constant2,
                res2.new_priorities().get(value) - res2.old_priorities().get(value)
            );
        }
    }

    @Test
    public void testExponantialIncrementSmall() {
        testExponantialIncrement(200);
    }

    @Test
    public void testExponantialIncrementLarge() {
        testExponantialIncrement(2000);
    }

    @Override
    protected MyFrequencyTreap<Integer> createTree() {
        return new MyFrequencyTreap<>();
    }
}
