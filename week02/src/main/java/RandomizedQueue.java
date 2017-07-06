import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int startsAt;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (startsAt + size == items.length) {
            resize(2 * size);
        }

        items[startsAt + size] = item;
        size++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int randomIndex = StdRandom.uniform(startsAt, startsAt + size());

        Item randomItem = items[randomIndex];
        Item head = items[startsAt];
        items[randomIndex] = head;
        items[startsAt] = null;
        startsAt++;
        size--;

        if (size == 0) {
            startsAt = 0;
        } else if (items.length >= 2 && size == items.length / 4) {
            resize(items.length / 2);
        }

        assert randomItem != null;
        return randomItem;
    }

    private void resize(int capacity) {
        assert capacity >= size;

        Item[] itemsResized = (Item[]) new Object[capacity];
        System.arraycopy(items, startsAt, itemsResized, 0, size);

        items = itemsResized;
        startsAt = 0;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        int randomIndex = StdRandom.uniform(startsAt, startsAt + size());
        Item item = items[randomIndex];
        assert item != null;
        return item;
    }

    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private final int[] shuffledIndices;
        private int position = 0;

        QueueIterator() {
            shuffledIndices = new int[size];
            for (int i = 0; i < size; i++) {
                shuffledIndices[i] = i + startsAt;
            }

            StdRandom.shuffle(shuffledIndices);
        }

        public boolean hasNext() {
            return position < size();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = items[shuffledIndices[position]];
            position++;
            return item;
        }
    }

    public static void main(String[] args) {
    }
}
