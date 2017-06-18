import org.junit.Assert;
import org.junit.Test;

public class PercolationTest {
    @Test
    public void returns_is_open() {
        Percolation sut = new Percolation(2);
        sut.open(1, 1);
        sut.open(2, 2);

        Assert.assertTrue(sut.isOpen(1, 1));
        Assert.assertFalse(sut.isOpen(1, 2));
        Assert.assertFalse(sut.isOpen(2, 1));
        Assert.assertTrue(sut.isOpen(2, 2));
    }

    @Test
    public void returns_false_when_does_not_percolate_1x1() {
        Percolation sut = new Percolation(1);
        Assert.assertFalse(sut.percolates());
    }

    @Test
    public void returns_true_when_percolates_1x1() {
        Percolation sut = new Percolation(1);
        sut.open(1, 1);

        Assert.assertTrue(sut.percolates());
    }

    @Test
    public void does_not_percolate_2x2_case_0() {
        Percolation sut = new Percolation(2);

        Assert.assertFalse(sut.percolates());
    }

    @Test
    public void does_not_percolate_2x2_case_1() {
        Percolation sut = new Percolation(2);
        sut.open(1, 1);
        sut.open(2, 2);

        Assert.assertFalse(sut.percolates());
    }

    @Test
    public void returns_true_when_percolates_2x2_case_0() {
        Percolation sut = new Percolation(2);
        sut.open(1, 1);
        sut.open(1, 2);
        sut.open(2, 2);

        Assert.assertTrue(sut.percolates());
    }

    @Test
    public void returns_true_when_percolates_2x2_case_1() {
        Percolation sut = new Percolation(2);
        sut.open(1, 1);
        sut.open(2, 1);

        Assert.assertTrue(sut.percolates());
    }

    @Test
    public void returns_true_when_percolates_2x2_case_2() {
        Percolation sut = new Percolation(2);
        sut.open(1, 1);
        sut.open(1, 2);
        sut.open(2, 2);

        Assert.assertTrue(sut.percolates());
    }

    @Test
    public void returns_is_full_case_0() {
        Percolation sut = new Percolation(3);
        sut.open(1, 3);
        sut.open(2, 2);
        sut.open(2, 3);
        sut.open(3, 2);

        Assert.assertFalse(sut.isFull(1, 1));
        Assert.assertFalse(sut.isFull(1, 2));
        Assert.assertTrue(sut.isFull(1, 3));
        Assert.assertFalse(sut.isFull(2, 1));
        Assert.assertTrue(sut.isFull(2, 2));
        Assert.assertTrue(sut.isFull(2, 3));
        Assert.assertFalse(sut.isFull(3, 1));
        Assert.assertTrue(sut.isFull(3, 2));
        Assert.assertFalse(sut.isFull(3, 3));
    }

    @Test
    public void returns_is_full_case_1() {
        Percolation sut = new Percolation(1);

        Assert.assertFalse(sut.isFull(1, 1));
    }

    @Test
    public void returns_number_of_open_sites() {
        Percolation sut = new Percolation(3);
        sut.open(1, 3);
        sut.open(3, 2);

        Assert.assertEquals(2, sut.numberOfOpenSites());
    }

    @Test
    public void returns_is_full_when_backwash() {
        Percolation sut = new Percolation(3);
        sut.open(1, 3);
        sut.open(2, 3);
        sut.open(3, 3);
        sut.open(3, 1);

        Assert.assertFalse(sut.isFull(3, 1));
    }
}
