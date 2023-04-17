package bearmaps.proj2ab;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<Node> pQ;
    private HashMap<T, Integer> inputs;

    private class Node {
        private T heapItem;
        private double prioritized;

        Node(T theItem, double thePrioritized) {
            heapItem = theItem;
            prioritized = thePrioritized;
        }

    }

    public ArrayHeapMinPQ() {
        pQ = new ArrayList<>();
        inputs = new HashMap<>();
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return i * 2 + 1;
    }

    private int rightChild(int i) {
        return i * 2 + 2;
    }

    private void swap(int x, int z) {
        Node original = pQ.get(x);
        pQ.set(x, pQ.get(z));
        pQ.set(z, original);
        inputs.put(pQ.get(x).heapItem, x);
        inputs.put(pQ.get(z).heapItem, z);
    }

    private void swim(int k) {
        while (k > 0) {
            int theParent = parent(k);
            if (!(pQ.get(k).prioritized < pQ.get(theParent).prioritized)) {
                return;
            }
            swap(k, theParent);
            k = theParent;
        }
        /*if (parent(k) > k) {
            swap(k, parent(k));
            swim(parent(k));
        }*/
        /*while (k > 0)
        int parentNum = parent(k);
        swap(k, );
        k = parentNum;*/
    }

    private boolean trueorFalse(int x, int y) {
        return pQ.get(x).prioritized < pQ.get(y).prioritized;
    }
    private void sink(int k) {
        while (leftChild(k) < size()) {
            int i = leftChild(k);
            if (rightChild(k) < size() && trueorFalse(rightChild(k), i)) {
                i = rightChild(k);
            }
            if (pQ.get(k).prioritized < pQ.get(i).prioritized) {
                return;
            }
            swap(k, i);
            k = i;
        }
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present.
     * You may assume that item is never null. */
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException();
        }
        pQ.add(new Node(item, priority));
        inputs.put(item, size() - 1);
        swim(size() - 1);

    }

    /* Returns true if the PQ contains the given item. */
    public boolean contains(T item) {
        return inputs.containsKey(item);
    }

    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T getSmallest() {
        if (pQ.isEmpty()) {
            throw new NoSuchElementException();
        }
        return pQ.get(0).heapItem;
    }

    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    public T removeSmallest() {
        if (pQ.isEmpty()) {
            throw new NoSuchElementException();
        }
        int last = size() - 1;
        T topNode = getSmallest();
        swap(0, last);
        pQ.remove(last);
        sink(0);
        inputs.remove(topNode);
        return topNode;
    }

    /* Returns the number of items in the PQ. */
    public int size() {
        return pQ.size();
    }

    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException();
        }
        int theItem = inputs.get(item);
        double prevPrioritized = pQ.get(theItem).prioritized;
        pQ.get(theItem).prioritized = priority;
        if (prevPrioritized < priority) {
            sink(theItem);
        }
        if (prevPrioritized >= priority) {
            swim(theItem);
        }
    }
}
