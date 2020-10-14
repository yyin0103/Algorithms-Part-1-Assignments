/* *****************************************************************************
 *  Topic:     Dequeue.
 *             A double-ended queue or deque (pronounced “deck”) is a generalization
 *             of a stack and a queue that supports adding and removing items from
 *             either the front or the back of the data structure.
 *  @author:   Ying Chu
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int capacity = 0;

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque() {
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (capacity == 0);
    }

    // return the number of items on the deque
    public int size() {
        return capacity;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        if (capacity == 0) {
            last = first;
        } else {
            first.next = oldfirst;
            oldfirst.previous = first;
        }
        capacity++;
    }



    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (capacity == 0) {
            first = last;
        } else {
            oldLast.next = last;
            last.previous = oldLast;
        }
        capacity++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The deque is empty");
        }
        Item item = first.item;
        if (capacity == 1) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.previous = null;
        }
        capacity--;
        return item;

    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException("The deque is empty");
        }
        Item item = last.item;
        if (capacity == 1) {
            last = null;
            first = null;
        } else {
            last = last.previous;
            last.next = null;
        }
        capacity--;
        return item;
    }
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
            return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                Item item = current.item;
                current = current.next;
                return item;
            }
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();

        deque.addFirst("Apple");
        deque.addFirst("Banana");
        deque.addLast("Pear");
        deque.removeFirst();
        deque.removeLast();
        deque.addLast("Cherry");
        StdOut.println("Is the deque empty: " + deque.isEmpty());
        StdOut.println("Size of the deque: " + deque.size());
        for (String i : deque) {
            StdOut.println(i);
        }
    }
}
