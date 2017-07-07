import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

public class BoardTest {
    @Test
    public void returns_hamming() {
        Board sut = new Board(new int[][]{
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}});

        assertThat(sut.hamming(), is(5));
    }

    @Test
    public void returns_manhattan() {
        Board sut = new Board(new int[][]{
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}});

        assertThat(sut.manhattan(), is(10));
    }

    @Test
    public void returns_if_equal_when_another_board_is_equal() {
        Board sut = new Board(new int[][]{
                {1, 3, 2},
                {4, 0, 8},
                {5, 6, 7}});

        Board anotherBoard = new Board(new int[][]{
                {1, 3, 2},
                {4, 0, 8},
                {5, 6, 7}});

        assertThat(sut.equals(anotherBoard), is(true));
    }

    @Test
    public void returns_if_equal_when_another_board_is_not_equal() {
        Board sut = new Board(new int[][]{
                {1, 3, 2},
                {4, 0, 8},
                {5, 6, 7}});

        Board anotherBoard = new Board(new int[][]{
                {3, 1, 2},
                {4, 0, 8},
                {5, 6, 7}});

        assertThat(sut.equals(anotherBoard), is(false));
    }

    @Test
    public void returns_neighbors() {
        Board sut = new Board(new int[][]{
                {8, 1, 3},
                {4, 2, 0},
                {7, 6, 5}});

        assertThat(sut.neighbors(), containsInAnyOrder(
                new Board(new int[][]{
                        {8, 1, 0},
                        {4, 2, 3},
                        {7, 6, 5}}),
                new Board(new int[][]{
                        {8, 1, 3},
                        {4, 0, 2},
                        {7, 6, 5}}),
                new Board(new int[][]{
                        {8, 1, 3},
                        {4, 2, 5},
                        {7, 6, 0}})));
    }
}
