package oplossing.node.abstractNode;

import opgave.PriorityNode;

public abstract class AbstractPriorityNode<T extends Comparable<T>, N
                                               extends AbstractPriorityNode<T, N>>
    extends AbstractNode<T, N> implements PriorityNode<T> {

    private long priority;

    public AbstractPriorityNode(T value, int priority) {
        super(value);
        this.priority = priority;
    }
    public AbstractPriorityNode(T value) { this(value, 0); }

    public void setPriority(long priority) { this.priority = priority; }
    public void incrementPriority(long inc) { priority += inc; }
    @Override
    public long getPriority() {
        return priority;
    }

    @Override
    public PriorityNode<T> getLeft() {
        return left;
    }

    @Override
    public PriorityNode<T> getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "\"" + value + "," + priority + "\"";
    }
}
