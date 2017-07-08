import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

public class SolverTest {
    @Test
    public void returns_solution() {
        Solver sut = new Solver(new Board(new int[][]{
                {0, 1, 3},
                {4, 2, 5},
                {7, 8, 6}}));

        assertThat(sut.solution(), contains(
                new Board(new int[][]{
                        {0, 1, 3},
                        {4, 2, 5},
                        {7, 8, 6}}),
                new Board(new int[][]{
                        {1, 0, 3},
                        {4, 2, 5},
                        {7, 8, 6}}),
                new Board(new int[][]{
                        {1, 2, 3},
                        {4, 0, 5},
                        {7, 8, 6}}),
                new Board(new int[][]{
                        {1, 2, 3},
                        {4, 5, 0},
                        {7, 8, 6}}),
                new Board(new int[][]{
                        {1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 0}})));
    }

    @Test
    public void returns_moves() {
        Solver sut = new Solver(new Board(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 8}}));

        assertThat(sut.moves(), is(1));
    }

    @Test
    public void returns_if_is_solvable_0() {
        Solver sut = new Solver(new Board(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 8}}));

        assertThat(sut.isSolvable(), is(true));
    }

    @Test
    public void returns_if_is_solvable_1() {
        Solver sut = new Solver(new Board(new int[][]{
                {8, 6, 7},
                {4, 3, 1},
                {2, 5, 0}}));

        assertThat(sut.isSolvable(), is(true));
    }

    @Test
    public void returns_if_is_not_solvable_0() {
        Solver sut = new Solver(new Board(new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {8, 7, 0}}));

        assertThat(sut.isSolvable(), is(false));
    }

    @Test
    public void returns_if_is_not_solvable_1() {
        Solver sut = new Solver(new Board(new int[][]{
                {3, 2, 4, 8},
                {1, 6, 0, 12},
                {5, 10, 7, 11},
                {9, 13, 14, 15}}));

        assertThat(sut.isSolvable(), is(false));
    }
}
