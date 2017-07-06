import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

public class DequeTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void rejects_when_add_first_null() {
        exception.expect(IllegalArgumentException.class);

        Deque sut = new Deque();
        sut.addFirst(null);
    }

    @Test
    public void rejects_when_add_last_null() {
        exception.expect(IllegalArgumentException.class);

        Deque sut = new Deque();
        sut.addLast(null);
    }

    @Test
    public void rejects_when_remove_first_from_empty_dequeue() {
        exception.expect(NoSuchElementException.class);

        Deque sut = new Deque();
        sut.removeFirst();
    }

    @Test
    public void rejects_when_remove_last_from_empty_dequeue() {
        exception.expect(NoSuchElementException.class);

        Deque sut = new Deque();
        sut.removeLast();
    }

    @Test
    public void rejects_when_iterate_over_empty_dequeue() {
        exception.expect(NoSuchElementException.class);

        Deque sut = new Deque();
        Iterator iterator = sut.iterator();
        iterator.next();
    }

    @Test
    public void adds_and_removes_first_item() {
        Deque<Integer> sut = new Deque<Integer>();
        sut.addFirst(0);
        int result = sut.removeFirst();
        Assert.assertThat(result, is(0));
    }

    @Test
    public void adds_and_removes_last_item() {
        Deque<Integer> sut = new Deque<Integer>();
        sut.addLast(0);
        int result = sut.removeLast();
        Assert.assertThat(result, is(0));
    }

    @Test
    public void adds_and_removes_first_and_last() {
        Deque<Integer> sut = new Deque<Integer>();
        sut.addFirst(0);
        sut.removeLast();
        sut.addFirst(1);
        int result = sut.removeLast();
        Assert.assertThat(result, is(1));
    }

    @Test
    public void is_stack_from_head() {
        Deque<Integer> sut = new Deque<Integer>();
        sut.addFirst(0);
        sut.addFirst(1);
        sut.addFirst(2);

        Assert.assertThat(sut, contains(2, 1, 0));
    }

    @Test
    public void is_stack_from_tail() {
        Deque<Integer> sut = new Deque<Integer>();
        sut.addLast(0);
        sut.addLast(1);
        sut.addLast(2);

        Assert.assertThat(sut, contains(0, 1, 2));
    }

    @Test
    public void tells_if_is_empty_when_empty() {
        Deque<Integer> sut = new Deque<Integer>();
        Assert.assertThat(sut.isEmpty(), is(true));
    }

    @Test
    public void tells_if_is_empty_when_not_empty() {
        Deque<Integer> sut = new Deque<Integer>();
        sut.addLast(0);
        Assert.assertThat(sut.isEmpty(), is(false));
    }

    @Test
    public void tells_size_when_empty_case_0() {
        Deque<Integer> sut = new Deque<Integer>();
        Assert.assertThat(sut.size(), is(0));
    }

    @Test
    public void tells_size_when_empty_case_1() {
        Deque<Integer> sut = new Deque<Integer>();
        sut.addFirst(0);
        sut.removeFirst();
        Assert.assertThat(sut.size(), is(0));
    }

    @Test
    public void tells_size_when_not_empty() {
        Deque<Integer> sut = new Deque<Integer>();
        sut.addFirst(0);
        Assert.assertThat(sut.size(), is(1));
    }
}