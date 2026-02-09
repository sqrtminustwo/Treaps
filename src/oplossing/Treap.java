package oplossing;

import oplossing.abstractTree.AbstractTreap;
import oplossing.node.MyPriorityNode;

import java.util.Random;

public class Treap<T extends Comparable<T>> extends AbstractTreap<T, MyPriorityNode<T>> {

    public Treap(Random random, int bound) { super(random, bound); }
    public Treap(int bound) { super(bound); }
    public Treap() { super(100000); }

    @Override
    public MyPriorityNode<T> createNode(T o) {
        return new MyPriorityNode<>(o);
    }
}
