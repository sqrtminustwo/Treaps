package myFrequencyTreap;

import oplossing.MyFrequencyTreap;
import abstractTests.TreapStructureTest;

public class BinaryHeapTest extends TreapStructureTest<MyFrequencyTreap<Integer>> {
    @Override
    public MyFrequencyTreap<Integer> createTree() {
        return new MyFrequencyTreap<>();
    }
}
