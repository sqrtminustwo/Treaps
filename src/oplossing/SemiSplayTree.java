package oplossing;

import oplossing.abstractTree.AbstractTree;
import oplossing.node.MyNode;

import opgave.Node;

public class SemiSplayTree<T extends Comparable<T>> extends AbstractTree<T, MyNode<T>> {

    /*
     * Restruct bij elke stap van semisplay
     */

    @Override
    protected MyNode<T> restruct(MyNode<T> parent, MyNode<T> n1, MyNode<T> n2, MyNode<T> n3) {
        boolean firstTurn = n1.getMutableRight() == n2;
        boolean secondTurn = n2.getMutableRight() == n3;

        MyNode<T> b;

        if (firstTurn == secondTurn) {
            rotateUp(n2, n1, n1.getParent());
            b = n2;
        } else {
            rotateUp(n3, n2, n1);
            rotateUp(n3, n1, n1.getParent());
            b = n3;
        }

        return b;
    }

    @Override
    protected MyNode<T> createNode(T value) {
        return new MyNode<>(value);
    }
    @Override
    public boolean add(T o) {
        boolean added = addBinary(o);
        semiSplay(lastVisited);
        return added;
    }

    @Override
    public boolean remove(T e) {
        boolean removed = removeBinary(e);
        semiSplay(lastVisited);
        return removed;
    }

    @Override
    public boolean search(T o) {
        boolean found = searchBinary(o);
        semiSplay(lastVisited);
        return found;
    }

    @Override
    public Node<T> root() {
        return root;
    }
}
