package oplossing.abstractTree;

import oplossing.node.abstractNode.AbstractPriorityNode;

import java.util.Random;

public abstract class AbstractFrequencyTreap<T extends Comparable<T>, N
                                                 extends AbstractPriorityNode<T, N>>
    extends AbstractTreap<T, N> {

    public AbstractFrequencyTreap(Random random, int bound) { super(random, bound); }
    public AbstractFrequencyTreap(int bound) { super(bound); }

    protected abstract void changePriority(N node);

    @Override
    public boolean search(T o) {
        N node = searchBinaryAndReturnNode(o);
        if (node == null) return false;

        changePriority(node);
        rotateUpWhileNotHeap(node);

        return true;
    }
}
