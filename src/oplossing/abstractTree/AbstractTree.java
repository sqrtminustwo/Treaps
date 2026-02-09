package oplossing.abstractTree;

import opgave.SearchTree;
import oplossing.node.abstractNode.AbstractNode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public abstract class AbstractTree<T extends Comparable<T>, N extends AbstractNode<T, N>>
    implements SearchTree<T> {

    protected N root;
    protected N lastVisited;
    protected int size;

    public AbstractTree() {
        root = null;
        lastVisited = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    /*
     * Hulp functies
     */

    private boolean isLeft(N parent, N child) {
        return parent != null && child.getValue().compareTo(parent.getValue()) < 0;
    }
    protected void setChild(N parent, N node, boolean left) {
        if (parent == null) root = node;
        else if (left) parent.setLeft(node);
        else parent.setRight(node);
        if (node != null) node.setParent(parent);
    }
    protected void setChild(N parent, N node) { setChild(parent, node, isLeft(parent, node)); }
    protected void replaceChild(N parentBoth, N oldChild, N newChild) {
        setChild(parentBoth, newChild, isLeft(parentBoth, oldChild));
    }

    /*
     * Add functies
     */

    protected abstract N createNode(T value);
    public boolean addBinary(T o) {
        if (searchBinary(o)) return false;

        // Maak een nieuw object alleen wanneer dat nodig is
        N node = createNode(o);
        setChild(lastVisited, node);
        lastVisited = node;

        size++;

        return true;
    }
    @Override
    public boolean add(T o) {
        return addBinary(o);
    }

    /*
     * Remove functies
     */

    protected boolean removeBinary(T e) {
        N node = searchBinaryAndReturnNode(e);
        if (node == null) return false;

        if (node.hasBoth()) {
            // replaceWithLeftLargest
            N parent = node;
            N extreme = node.getMutableLeft();
            boolean left = !extreme.hasRight();

            while (extreme.hasRight()) {
                parent = extreme;
                extreme = extreme.getMutableRight();
            }

            node.setValue(extreme.getValue());
            // Die is grootste dus kan enkel linkse kind hebben (null is ok)
            setChild(parent, extreme.getMutableLeft(), left);
            // als de sleutel in een top met twee kinderen zit en
            // dus een andere sleutel in de top geplaatst wordt, telt het pad naar deze
            // andere sleutel mee
            lastVisited = parent;
        } else {
            lastVisited = node.getParent();
            replaceChild(node.getParent(), node, node.getOnlyChild());
        }

        size--;

        return true;
    }

    @Override public abstract boolean remove(T e);

    /*
     * Search functies
     */

    protected N searchBinaryAndReturnNode(T o) {
        N currentNode = root;
        lastVisited = null; // edge geval bij zoeken in lege boom

        while (currentNode != null) {
            int compared = o.compareTo(currentNode.getValue());

            lastVisited = currentNode;
            if (compared == 0) break;
            else
                currentNode = compared < 0 ? currentNode.getMutableLeft()
                                           : currentNode.getMutableRight();
        }

        return currentNode;
    }

    protected boolean searchBinary(T o) { return searchBinaryAndReturnNode(o) != null; }

    /*
     * Values functies
     */

    private void runLeft(N curr, Stack<N> stack) {
        while (curr != null) {
            stack.add(curr);
            curr = curr.getMutableLeft();
        }
    }
    @Override
    public List<T> values() {
        Stack<N> stack = new Stack<>();
        ArrayList<T> values = new ArrayList<>(size);
        runLeft(root, stack);

        while (!stack.isEmpty()) {
            N curr = stack.pop();
            values.add(curr.getValue());
            runLeft(curr.getMutableRight(), stack);
        }

        return values;
    }

    /*
     * Rotatie functies (SemiSplayTree en Treaps)
     */

    protected void rotateRight(N parentBoth, N parent, N child) {
        replaceChild(parentBoth, parent, child);

        setChild(parent, child.getMutableRight(), true);
        setChild(child, parent, false);
    }
    protected void rotateLeft(N parentBoth, N parent, N child) {
        replaceChild(parentBoth, parent, child);

        setChild(parent, child.getMutableLeft(), false);
        setChild(child, parent, true);
    }

    public void rotateUp(N node, N parent, N parentBoth) {
        if (isLeft(parent, node)) rotateRight(parentBoth, parent, node);
        else rotateLeft(parentBoth, parent, node);
    }

    /*
     * Semi-Splay functies (SemiSplayTree en MyTreap)
     */

    // Voor @Override
    protected N restruct(N parent, N n1, N n2, N n3) { return null; }

    protected void semiSplay(N node) {
        if (node == null) return;

        N n3 = node, n2 = node.getParent(), n1, parent;

        // n2 != null && n2 heeft parent -> 3 nodes -> semi-splay
        while (n2 != null && n2.hasParent()) {
            n1 = n2.getParent();
            parent = n1.getParent();

            n3 = restruct(parent, n1, n2, n3);
            n2 = parent;
        }
    }

    // https://dreampuf.github.io/GraphvizOnline/

    public void toGraphviz(String fileName) {
        Stack<N> stack = new Stack<>();
        String basePath = "output/";
        File file = new File(basePath + fileName);
        try {
            file.createNewFile();
        } catch (IOException ex) {
            System.out.println("Error creating: " + file.getName());
            ex.printStackTrace();
            return;
        }

        StringBuilder line = new StringBuilder();
        int idCounter = 0;

        if (root != null) stack.push(root);

        try (BufferedWriter writer = new BufferedWriter(new PrintWriter(file))) {
            writer.write(
                    "digraph BST {\n"
                            + "\tnode [shape=circle, fontname=\"Helvetica\"];\n"
            );

            while (!stack.isEmpty()) {
                writer.newLine();
                N parent = stack.pop();

                N right = parent.getMutableRight();
                N left = parent.getMutableLeft();

                idCounter = getIdCounter(line, stack, idCounter, parent, left);
                writer.write(line.toString());
                idCounter = getIdCounter(line, stack, idCounter, parent, right);
                writer.write(line.toString());
            }

            writer.write("}");
            writer.newLine();

        } catch (Exception ex) {
            System.out.println("Error writing to: " + file.getName());
            ex.printStackTrace();
        }

        stack.clear();
    }

    private int getIdCounter(StringBuilder line, Stack<N> stack, int idCounter, N parent, N node) {
        line.setLength(0);
        if (node != null) {
            line.append("\t").append(parent).append(" -> ").append(node).append(";\n");
            line.append("\t").append(node).append(" -> ").append(node.getParent()).append(";\n");
            stack.push(node);
        } else {
            idCounter++;
            line.append("\t").append(parent).append(" -> ").append("null").append(idCounter).append(
                    ";\n"
            );
        }
        return idCounter;
    }
}
