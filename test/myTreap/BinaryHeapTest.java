package myTreap;

import oplossing.MyTreap;
import abstractTests.TreapStructureTest;

public class BinaryHeapTest extends TreapStructureTest<MyTreap<Integer>> {
    @Override
    public MyTreap<Integer> createTree() {
        return new MyTreap<>();
    }
}
