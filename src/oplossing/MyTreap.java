package oplossing;

import oplossing.abstractTree.AbstractTreap;
import oplossing.node.MyPriorityNode;

import java.util.Random;

public class MyTreap<T extends Comparable<T>> extends AbstractTreap<T, MyPriorityNode<T>> {

    public MyTreap(Random random, int bound) { super(random, bound); }
    public MyTreap() { super(); }

    @Override
    protected MyPriorityNode<T> restruct(
        MyPriorityNode<T> ignore, MyPriorityNode<T> n1, MyPriorityNode<T> n2, MyPriorityNode<T> n3
    ) {
        boolean firstTurn = n1.getMutableRight() == n2;
        boolean secondTurn = n2.getMutableRight() == n3;

        MyPriorityNode<T> toRotateUp = firstTurn == secondTurn ? n2 : n3;

        toRotateUp.setPriority(n1.getPriority() + 1);
        rotateUpWhileNotHeap(toRotateUp);

        return toRotateUp;
    }

    @Override
    public boolean search(T o) {
        boolean found = searchBinary(o);
        semiSplay(lastVisited);
        return found;
    }

    @Override
    protected MyPriorityNode<T> createNode(T value) {
        return new MyPriorityNode<>(value);
    }
}
