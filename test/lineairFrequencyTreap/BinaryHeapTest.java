package lineairFrequencyTreap;

import oplossing.LineairFrequencyTreap;
import abstractTests.TreapStructureTest;

public class BinaryHeapTest extends TreapStructureTest<LineairFrequencyTreap<Integer>> {
    @Override
    public LineairFrequencyTreap<Integer> createTree() {
        return new LineairFrequencyTreap<>();
    }
}
