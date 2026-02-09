package oplossing.node.abstractNode;

import opgave.Node;

public abstract class AbstractNode<T extends Comparable<T>, N extends Node<T>> implements Node<T> {

    protected T value;
    private N parent;
    protected N left;
    protected N right;

    public AbstractNode(T value) { this.value = value; }

    public boolean hasLeft() { return left != null; }
    public boolean hasRight() { return right != null; }
    public boolean hasBoth() { return hasLeft() && hasRight(); }
    public boolean hasParent() { return parent != null; }

    public void setParent(N parent) { this.parent = parent; }
    public void setLeft(N left) { this.left = left; }
    public void setRight(N right) { this.right = right; }
    public void setValue(T value) { this.value = value; }

    public N getParent() { return parent; }
    public N getMutableLeft() { return left; }
    public N getMutableRight() { return right; }
    public N getOnlyChild() { return hasLeft() ? left : right; }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public Node<T> getLeft() {
        return left;
    }

    @Override
    public Node<T> getRight() {
        return right;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }
}
