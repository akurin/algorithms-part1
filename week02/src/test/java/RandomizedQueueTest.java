import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class RandomizedQueueTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void rejects_when_enqueue_null() {
        exception.expect(IllegalArgumentException.class);

        RandomizedQueue sut = new RandomizedQueue();
        sut.enqueue(null);
    }

    @Test
    public void rejects_when_dequeue_from_empty_dequeue() {
        exception.expect(NoSuchElementException.class);

        RandomizedQueue sut = new RandomizedQueue();
        sut.dequeue();
    }

    @Test
    public void rejects_when_sample_from_empty_dequeue() {
        exception.expect(NoSuchElementException.class);

        RandomizedQueue sut = new RandomizedQueue();
        sut.sample();
    }

    @Test
    public void rejects_when_iterate_over_empty_dequeue() {
        exception.expect(NoSuchElementException.class);

        RandomizedQueue sut = new RandomizedQueue();
        Iterator iterator = sut.iterator();
        iterator.next();
    }

    @Test
    public void enqueues_and_dequeues_item() {
        RandomizedQueue<Integer> sut = new RandomizedQueue();
        sut.enqueue(0);
        int result = sut.dequeue();
        Assert.assertThat(result, is(0));
    }

    @Test
    public void enqueues_and_dequeues_items_case_0() {
        RandomizedQueue<Integer> sut = new RandomizedQueue();
        sut.enqueue(0);
        sut.enqueue(1);
        sut.enqueue(2);

        List<Integer> result = new ArrayList();
        result.add(sut.dequeue());
        result.add(sut.dequeue());
        result.add(sut.dequeue());

        Assert.assertThat(result, containsInAnyOrder(0, 1, 2));
    }

    @Test
    public void enqueues_and_dequeues_items_case_1() {
        RandomizedQueue<Integer> sut = new RandomizedQueue();
        sut.enqueue(0);
        sut.dequeue();
        sut.enqueue(1);
        sut.dequeue();
        sut.enqueue(2);

        Assert.assertThat(sut, contains(2));
    }

    @Test
    public void iterates_over_items() {
        RandomizedQueue<Integer> sut = new RandomizedQueue();
        sut.enqueue(0);
        sut.enqueue(1);
        sut.enqueue(2);

        Assert.assertThat(sut, containsInAnyOrder(0, 1, 2));
    }

    @Test
    public void returns_sample() {
        RandomizedQueue<Integer> sut = new RandomizedQueue();
        sut.enqueue(0);
        sut.enqueue(1);
        sut.enqueue(2);

        for (int i = 0; i < 100; i++) {
            Assert.assertThat(sut.sample(), is(not(nullValue())));
        }
    }

    @Test
    public void test() {
        List<Integer> items = new ArrayList<Integer>();

        RandomizedQueue<Integer> sut = new RandomizedQueue<Integer>();

        for (int i = 0; i < 1000; i++) {
            Assert.assertThat(sut.isEmpty(), is(items.isEmpty()));
            Assert.assertThat(sut.size(), is(items.size()));

            if (StdRandom.uniform(3) > 0) {
                sut.enqueue(i);
                items.add(i);
            } else {
                if (!items.isEmpty()) {
                    items.remove(sut.dequeue());
                }
            }

            Assert.assertThat(sut, containsInAnyOrder(items.toArray()));
        }
    }
}