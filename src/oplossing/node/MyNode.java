package oplossing.node;

import oplossing.node.abstractNode.AbstractNode;

public class MyNode<T extends Comparable<T>> extends AbstractNode<T, MyNode<T>> {
    public MyNode(T value) { super(value); }
}
