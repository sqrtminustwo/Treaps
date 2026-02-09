package abstractTests;

import opgave.PriorityNode;
import opgave.PrioritySearchTree;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class FrequencyTreapSearchTest<T extends PrioritySearchTree<Integer>>
    extends AbstractFunctions<Integer, PriorityNode<Integer>, T> {

    protected HashMap<Integer, Long> getPriorities(List<PriorityNode<Integer>> nodes) {
        HashMap<Integer, Long> priorities = new HashMap<>();
        for (PriorityNode<Integer> node : nodes)
            priorities.put(node.getValue(), node.getPriority());
        return priorities;
    }

    public record ReturnOfSearchGetPriorities(
        List<Integer> searched, HashMap<Integer, Long> old_priorities,
        HashMap<Integer, Long> new_priorities
    ) {}
    public ReturnOfSearchGetPriorities
    searchGetPriorities(PrioritySearchTree<Integer> treap, List<Integer> to_search) {
        List<PriorityNode<Integer>> nodes = getNodesByValue(treap.root(), to_search);
        HashMap<Integer, Long> old_priorities = getPriorities(nodes);

        for (int e : to_search) treap.search(e);
        assertTrue(validBinaryHeap(treap.root()));

        HashMap<Integer, Long> new_priorities = getPriorities(nodes);

        assertFalse(old_priorities.isEmpty());
        assertEquals(old_priorities.size(), new_priorities.size());

        return new ReturnOfSearchGetPriorities(to_search, old_priorities, new_priorities);
    }
    public ReturnOfSearchGetPriorities
    searchGetPriorities(PrioritySearchTree<Integer> treap, HashSet<Integer> elements) {
        return searchGetPriorities(treap, chooseRandom(elements));
    }

    protected void checkUnchanged(HashMap<Integer, Long> a, HashMap<Integer, Long> b) {
        assertEquals(a.size(), b.size());
        for (Integer value : a.keySet()) assertEquals(a.get(value), b.get(value));
    }

    @Test
    public void searchSingle() {
        T treap = createTree();
        treap.add(1);
        assertEquals(1, treap.root().getValue());
        long old_priority = treap.root().getPriority();
        treap.search(1);
        assertTrue(old_priority < treap.root().getPriority());
    }

    public void searchNonExistend(int size) {
        T treap = createTree();
        addAndGet(treap, size);
        List<PriorityNode<Integer>> nodes = getNodes(treap.root());
        HashMap<Integer, Long> old_priorities = getPriorities(nodes);

        int max = getMax(treap) + 1;
        for (int i = 0; i < size; i++) {
            assertFalse(treap.search(max));
            max++;
        }

        assertTrue(validBinaryHeap(treap.root()));

        HashMap<Integer, Long> new_priorities = getPriorities(nodes);

        checkUnchanged(old_priorities, new_priorities);
    }

    @Test
    public void searchNonExistendSmall() {
        searchNonExistend(100);
    }
    @Test
    public void searchNonExistendLarge() {
        searchNonExistend(1000);
    }

    public void searchExistend(int size) {
        T treap = createTree();
        HashSet<Integer> elements = addAndGet(treap, size);

        List<Integer> to_search_values = chooseRandom(elements);
        List<PriorityNode<Integer>> to_search_nodes =
            to_search_values.stream().map(e -> getNodeByValue(treap.root(), e)).toList();
        List<PriorityNode<Integer>> nodes = getNodes(treap.root(), to_search_values);

        HashMap<Integer, Long> old_priorities_searched = getPriorities(to_search_nodes);
        HashMap<Integer, Long> old_priorities_unsearched = getPriorities(nodes);

        for (int e : to_search_values) treap.search(e);
        assertTrue(validBinaryHeap(treap.root()));

        HashMap<Integer, Long> new_priorities_searched = getPriorities(to_search_nodes);
        HashMap<Integer, Long> new_priorities_unsearched = getPriorities(nodes);

        checkUnchanged(old_priorities_unsearched, new_priorities_unsearched);

        for (PriorityNode<Integer> node : to_search_nodes) {
            assertTrue(
                old_priorities_searched.get(node.getValue()) <
                new_priorities_searched.get(node.getValue())
            );
        }
    }

    @Test
    public void searchExistendSmall() {
        searchExistend(200);
    }

    @Test
    public void searchExistendLarge() {
        searchExistend(2000);
    }

    public void bubbleUpTest(int size) {
        T treap = createTree();
        List<Integer> elements = addAndGet(treap, size).stream().toList();

        long max_priority = treap.root().getPriority();
        PriorityNode<Integer> node =
            getNodeByValue(treap.root(), elements.get(rg.nextInt(elements.size())));

        while (node.getPriority() <= max_priority) {
            treap.search(node.getValue());
            assertTrue(validBinaryHeap(treap.root()));
        }

        assertEquals(node.getValue(), treap.root().getValue());
    }

    @Test
    public void bubbleUpTestSmall() {
        bubbleUpTest(1000);
    }
    @Test
    public void bubbleUpTestLarge() {
        bubbleUpTest(10000);
    }
}
