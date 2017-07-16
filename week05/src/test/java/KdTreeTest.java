import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;

public class KdTreeTest {
    @Test
    public void returns_size_when_duplicate_points() {
        List<Point2D> points = Arrays.asList(
                new Point2D(.7, .2),
                new Point2D(.7, .2));

        KdTree kdTree = new KdTree();
        points.forEach(kdTree::insert);

        assertThat(kdTree.size(), is(1));
    }

    @Test
    public void returns_contains() {
        List<Point2D> points = Arrays.asList(
                new Point2D(.7, .2),
                new Point2D(.5, .4),
                new Point2D(.2, .3),
                new Point2D(.4, .7),
                new Point2D(.9, .6));

        KdTree kdTree = new KdTree();
        points.forEach(kdTree::insert);

        assertThat(kdTree.contains(points.get(3)), is(true));
    }

    @Test
    public void returns_range() {
        List<Point2D> points = randomPoints();

        PointSET pointSET = new PointSET();
        points.forEach(pointSET::insert);

        KdTree kdTree = new KdTree();
        points.forEach(kdTree::insert);

        double reactMinX = StdRandom.uniform(.0, 1.0);
        double reactMinY = StdRandom.uniform(.0, 1.0);
        double reactMaxX = StdRandom.uniform(reactMinX, 1.0);
        double reactMaxY = StdRandom.uniform(reactMinY, 1.0);
        RectHV rect = new RectHV(reactMinX, reactMinY, reactMaxX, reactMaxY);

        Iterable<Point2D> actual = kdTree.range(rect);
        Iterable<Point2D> expected = pointSET.range(rect);

        List<Point2D> expectedList = StreamSupport.stream(expected.spliterator(), false)
                .collect(Collectors.toList());

        assertThat(actual, containsInAnyOrder(expectedList.toArray()));
    }

    private List<Point2D> randomPoints() {
        return IntStream
                .range(0, 5000)
                .mapToObj(i -> randomPoint())
                .collect(Collectors.toList());
    }

    private Point2D randomPoint() {
        return new Point2D(StdRandom.uniform(.0, 1.0), StdRandom.uniform(.0, 1.0));
    }

    @Test
    public void returns_nearest() {
        List<Point2D> points = randomPoints();

        PointSET pointSET = new PointSET();
        points.forEach(pointSET::insert);

        KdTree kdTree = new KdTree();
        points.forEach(kdTree::insert);

        Point2D randomPoint = randomPoint();

        Point2D actual = kdTree.nearest(randomPoint);
        Point2D expected = pointSET.nearest(randomPoint);
        assertThat(actual, is(expected));
    }
}
