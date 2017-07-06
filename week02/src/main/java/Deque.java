import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private static class Node<NodeItem> {
        private Node<NodeItem> prev;
        private Node<NodeItem> next;
        private NodeItem item;

        private Node(Node<NodeItem> prev, Node<NodeItem> next, NodeItem item) {
            this.prev = prev;
            this.next = next;
            this.item = item;
        }

        Node(NodeItem item) {
            this.item = item;
        }

        Node<NodeItem> addHead(NodeItem item) {
            Node<NodeItem> head = new Node<NodeItem>(null, this, item);
            this.prev = head;
            return head;
        }

        Node<NodeItem> addTail(NodeItem item) {
            Node<NodeItem> tail = new Node<NodeItem>(this, null, item);
            this.next = tail;
            return tail;
        }

        Node<NodeItem> prev() {
            return prev;
        }

        Node<NodeItem> next() {
            return next;
        }

        void makeHead() {
            prev = null;
        }

        NodeItem item() {
            return item;
        }

        void becomeTail() {
            next = null;
        }
    }

    private Node<Item> first;
    private Node<Item> last;
    private int size;

    public Deque() {
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (isEmpty()) {
            first = last = new Node<Item>(item);
        } else {
            first = first.addHead(item);
        }

        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (isEmpty()) {
            first = last = new Node<Item>(item);
        } else {
            last = last.addTail(item);
        }

        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        if (first.next() != null) {
            first.next().makeHead();
        }

        Item item = first.item;
        first = first.next();
        if (first == null) {
            last = null;
        }

        size--;

        return item;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        if (last.prev() != null) {
            last.prev().becomeTail();
        }

        Item item = last.item;
        last = last.prev();
        if (last == null) {
            first = null;
        }

        size--;

        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeueIterator(first);
    }

    public static void main(String[] args) {
    }

    private class DequeueIterator implements Iterator<Item> {
        private Node<Item> current;

        DequeueIterator(Node<Item> current) {
            this.current = current;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item currentItem = current.item();
            current = current.next();
            return currentItem;
        }
    }
}