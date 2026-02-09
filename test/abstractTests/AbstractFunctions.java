package abstractTests;

import opgave.Node;
import opgave.PriorityNode;
import opgave.SearchTree;
import opgave.samplers.Sampler;

import java.util.*;

public abstract class AbstractFunctions<E extends Comparable<E>, N extends Node<E>, T
                                            extends SearchTree<E>> {

    protected final Random rg = new Random();

    protected abstract T createTree();

    protected boolean validBinaryTree(Node<E> t) {
        if (t == null) return true;

        if (t.getLeft() != null && t.getLeft().getValue().compareTo(t.getValue()) >= 0)
            return false;
        if (t.getRight() != null && t.getRight().getValue().compareTo(t.getValue()) <= 0)
            return false;

        return validBinaryTree(t.getLeft()) && validBinaryTree(t.getRight());
    }

    private boolean validHeap(PriorityNode<E> t) {
        if (t == null) return true;

        if (t.getLeft() != null && t.getLeft().getPriority() > t.getPriority()) return false;
        if (t.getRight() != null && t.getRight().getPriority() > t.getPriority()) return false;

        return validHeap(t.getLeft()) && validHeap(t.getRight());
    }

    protected boolean validBinaryHeap(PriorityNode<E> t) {
        return validBinaryTree(t) && validHeap(t);
    }

    protected N getNodeByValue(Node<Integer> root, Integer value) {
        Node<Integer> current = root;
        while (current != null) {
            int compare = value.compareTo(current.getValue());
            if (compare == 0) break;
            else current = compare < 0 ? current.getLeft() : current.getRight();
        }

        return (N)current;
    }

    protected List<N> getNodesByValue(Node<Integer> root, List<Integer> value) {
        return value.stream().map(v -> getNodeByValue(root, v)).toList();
    }

    protected List<N> getNodes(N root, List<E> exclude) {
        List<N> nodes = new ArrayList<>();
        Stack<N> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            N current = stack.pop();
            if (!exclude.contains(current.getValue())) nodes.add(current);
            if (current.getLeft() != null) stack.add((N)current.getLeft());
            if (current.getRight() != null) stack.add((N)current.getRight());
        }

        return nodes;
    }
    protected List<N> getNodes(N root) { return getNodes(root, List.of()); }

    public HashSet<Integer> addAndGet(SearchTree<Integer> treap, int size) {
        Sampler sampler = new Sampler(new Random(), size);
        HashSet<Integer> elements = new HashSet<>(sampler.sample(size));

        for (int e : elements) treap.add(e);

        return elements;
    }

    public List<Integer> chooseRandom(HashSet<Integer> elements) {
        return elements.stream().filter(n -> rg.nextInt() % 3 == 0).toList();
    }

    public int getMax(SearchTree<Integer> tree) {
        return tree.values().stream().max(Integer::compareTo).get();
    }
}
