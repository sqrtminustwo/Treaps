package oplossing.node;

import oplossing.node.abstractNode.AbstractPriorityNode;

public class MyFrequencyNode<T extends Comparable<T>>
    extends AbstractPriorityNode<T, MyFrequencyNode<T>> {

    private long visits = 0;

    public MyFrequencyNode(T value) { super(value); }

    public void incrementVists() { visits++; }
    public long getVisits() { return visits; }
}
