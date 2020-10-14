/* *****************************************************************************
 *  Topic:    Randomized queue.
 *            A randomized queue is similar to a stack or queue, except that the
 *            item removed is chosen uniformly at random among items in the data
 *            structure.
 *  @author:  Ying Chu
 **************************************************************************** */
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int numOfItems;

    // construct an empty randomized queue
    public RandomizedQueue(){
        queue = (Item[]) new Object[2];
        numOfItems = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return numOfItems == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return numOfItems;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
        if (numOfItems == queue.length) {
            resize(queue.length * 2);
        }
        queue[numOfItems++] = item;

    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("The queue is empty");
        }
        int random = StdRandom.uniform(numOfItems);
        Item item = queue[random];
        queue[random] = queue[numOfItems - 1];
        queue[--numOfItems] = null;

        if (numOfItems > 0 && numOfItems == queue.length/4) {
            resize(queue.length/2);
        }
        return item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < numOfItems; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }
    // return a random item (but do not remove it)
    public Item sample() {
        if (numOfItems == 0) {
            throw new NoSuchElementException("The queue is empty");
        }
        int i = StdRandom.uniform(numOfItems);
        return queue[i];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private int i;

        public ListIterator() {
            i = 0;
            int[] randomIndices = new int[numOfItems];
            for (int j = 0; j < numOfItems; j++) {
                randomIndices[j] = j;
            }
            StdRandom.shuffle(randomIndices);
        }

        public boolean hasNext() {
            return i < numOfItems;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = queue[i++];
            return item;
        }
    }


    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        //add
        for (int i = 0; i < 10; i++) {
            rq.enqueue(i);

        }
        StdOut.println("Queue: " + rq.toString());

        //sample
        StdOut.println(rq.sample());

        //remove
        rq.dequeue();
        rq.dequeue();

        //Result
        StdOut.print("Is the queue empty: " + rq.isEmpty() + "\n");
        StdOut.println("Size: " + rq.size());

        Iterator<Integer> iter = rq.iterator();
        if (iter.hasNext()) {
            StdOut.println(iter.next());
        }
    }
}
