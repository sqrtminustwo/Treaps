package oplossing;

import oplossing.abstractTree.AbstractFrequencyTreap;
import oplossing.node.MyPriorityNode;

import java.util.Random;

public class LineairFrequencyTreap<T extends Comparable<T>>
    extends AbstractFrequencyTreap<T, MyPriorityNode<T>> {

    public LineairFrequencyTreap(Random random, int bound) { super(random, bound); }
    public LineairFrequencyTreap() { super(10000); }

    @Override
    protected void changePriority(MyPriorityNode<T> node) {
        node.incrementPriority(100);
    }

    @Override
    protected MyPriorityNode<T> createNode(T value) {
        return new MyPriorityNode<>(value);
    }
}
