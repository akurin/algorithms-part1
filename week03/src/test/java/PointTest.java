import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PointTest {
    @Test
    public void returns_slope() {
        Point a = new Point(0, 10);
        Point b = new Point(40, 20);
        Assert.assertThat(a.slopeTo(b), is(.25D));
    }

    @Test
    public void returns_slope_of_horizontal_line_segment_that_is_positive_zero() {
        Point a = new Point(10, 10);
        Point b = new Point(-20, 10);
        Assert.assertThat(a.slopeTo(b), is(0D));
    }

    @Test
    public void returns_slope_of_vertical_line_segment_that_is_positive_infinity() {
        Point a = new Point(10, 10);
        Point b = new Point(10, 20);
        Assert.assertThat(a.slopeTo(b), is(Double.POSITIVE_INFINITY));
    }

    @Test
    public void returns_slope_of_empty_line_segment_that_is_negative_infinity() {
        Point a = new Point(10, 10);
        Point b = new Point(10, 10);
        Assert.assertThat(a.slopeTo(b), is(Double.NEGATIVE_INFINITY));
    }

    @Test
    public void compares_equal_points() {
        Point a = new Point(10, 10);
        Point b = new Point(10, 10);
        Assert.assertThat(a.compareTo(b), is(0));
    }

    @Test
    public void compares_points_by_y() {
        Point a = new Point(10, 11);
        Point b = new Point(10, 10);
        Assert.assertThat(a.compareTo(b) > 0, is(true));
    }

    @Test
    public void compares_points_by_y_then_by_x() {
        Point a = new Point(11, 10);
        Point b = new Point(10, 10);
        Assert.assertThat(a.compareTo(b) > 0, is(true));
    }

    @Test
    public void compares_slopes_when_equal() {
        Point a = new Point(0, 0);
        Point b = new Point(10, 10);
        Point c = new Point(20, 20);
        Comparator<Point> slopeComparator = a.slopeOrder();
        assertThat(slopeComparator.compare(b, c), is(0));
    }

    @Test
    public void compares_slopes_when_greater() {
        Point a = new Point(0, 0);
        Point b = new Point(10, 10);
        Point c = new Point(10, 20);
        Comparator<Point> slopeComparator = a.slopeOrder();
        assertThat(slopeComparator.compare(b, c) < 0, is(true));
    }

    @Test
    public void copares_slopes_when_less() {
        Point a = new Point(0, 0);
        Point b = new Point(10, 20);
        Point c = new Point(10, 10);
        Comparator<Point> slopeComparator = a.slopeOrder();
        assertThat(slopeComparator.compare(b, c) > 0, is(true));
    }
}
