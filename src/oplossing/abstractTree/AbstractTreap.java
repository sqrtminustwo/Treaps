package oplossing.abstractTree;

import opgave.PriorityNode;
import opgave.PrioritySearchTree;
import oplossing.Treap;
import oplossing.node.abstractNode.AbstractPriorityNode;

import java.util.*;

public abstract class AbstractTreap<T extends Comparable<T>, N extends AbstractPriorityNode<T, N>>
    extends AbstractTree<T, N> implements PrioritySearchTree<T> {

    private final Random random;
    private final int bound;

    public AbstractTreap(Random random, int bound) {
        this.random = random;
        this.bound = bound;
    }
    public AbstractTreap(int bound) { this(new Random(), bound); }
    public AbstractTreap() { this(new Random(), 100000); }

    /*
     * Add functies
     */

    protected void rotateUpWhileNotHeap(N node) {
        N parent = node.getParent();

        while (parent != null && node.getPriority() > parent.getPriority()) {
            N parentBoth = parent.getParent();
            rotateUp(node, parent, parentBoth);
            parent = parentBoth;
        }
    }

    protected boolean addTreap(T value) {
        if (!addBinary(value)) return false;

        if (size == 1) {
            root.setPriority(100);
            return true;
        }

        // lastVisited is hier de toegevoegde node
        // met prioriteit 0
        lastVisited.setPriority(lastVisited.getParent().getPriority()+1);
        rotateUpWhileNotHeap(lastVisited);

        return true;
    }
    @Override
    public boolean add(T o) {
        return addTreap(o);
    }

    /*
     * Remove functies
     */

    @Override
    public boolean remove(T e) {
        N node = searchBinaryAndReturnNode(e);
        if (node == null) return false;

        while (node.hasBoth()) {
            if (node.getLeft().getPriority() >= node.getRight().getPriority())
                rotateRight(node.getParent(), node, node.getMutableLeft());
            else rotateLeft(node.getParent(), node, node.getMutableRight());
        }

        replaceChild(node.getParent(), node, node.getOnlyChild());

        size--;

        return true;
    }

    /*
     * Rest van SearchTree intereface functies
     */

    @Override
    public boolean search(T o) {
        return searchBinary(o);
    }

    @Override
    public PriorityNode<T> root() {
        return root;
    }

    public static void main(String[] args) {
        Treap<Integer> treap = new Treap<>();
        Random rg = new Random();
        for (int i=0; i<50; i++) treap.add(rg.nextInt(100));
        treap.toGraphviz("treap");
    }
}
