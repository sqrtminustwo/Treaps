package treap;

import abstractTests.TreapStructureTest;
import oplossing.Treap;

public class TreapBinaryHeapTest extends TreapStructureTest<Treap<Integer>> {

    @Override
    public Treap<Integer> createTree() {
        return new Treap<>();
    }
}
