package oplossing;

import oplossing.abstractTree.AbstractFrequencyTreap;
import oplossing.node.MyFrequencyNode;

import java.util.Random;

public class MyFrequencyTreap<T extends Comparable<T>>
    extends AbstractFrequencyTreap<T, MyFrequencyNode<T>> {

    public MyFrequencyTreap(Random random, int bound) { super(random, bound); }
    public MyFrequencyTreap(int bound) { super(bound); }
    public MyFrequencyTreap() { super(100000); }

    @Override
    protected void changePriority(MyFrequencyNode<T> node) {
        node.incrementVists();
        node.incrementPriority(10 * node.getVisits());
    }

    @Override
    protected MyFrequencyNode<T> createNode(T value) {
        return new MyFrequencyNode<>(value);
    }
}
