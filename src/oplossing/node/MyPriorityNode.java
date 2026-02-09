package oplossing.node;

import oplossing.node.abstractNode.AbstractPriorityNode;

public class MyPriorityNode<T extends Comparable<T>>
    extends AbstractPriorityNode<T, MyPriorityNode<T>> {
    public MyPriorityNode(T value) { super(value, 0); }
}
