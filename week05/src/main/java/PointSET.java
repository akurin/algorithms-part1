import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private Set<Point2D> points = new TreeSet<>();

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        points.add(p);
    }

    public boolean contains(Point2D p) {
        return points.contains(p);
    }

    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> result = new ArrayList<>();

        points.forEach(p -> {
            if (rect.contains(p)) {
                result.add(p);
            }
        });

        return result;
    }

    public Point2D nearest(Point2D p) {
        Point2D nearestPoint = null;
        double nearestDistance = 0;

        for (Point2D point : points) {
            if (nearestPoint == null || point.distanceTo(p) < nearestDistance) {
                nearestPoint = point;
                nearestDistance = point.distanceTo(p);
            }
        }

        return nearestPoint;
    }

    public static void main(String[] args) {
    }
}
