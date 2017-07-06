import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        ensureDoesNotContainNull(points);
        ensureDoesNotContainRepeated(points);

        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);

        List<LineSegment> segments = new ArrayList<LineSegment>();

        for (int i0 = 0; i0 < pointsCopy.length; i0++) {
            Point p0 = pointsCopy[i0];

            for (int i1 = i0 + 1; i1 < pointsCopy.length; i1++) {
                Point p1 = pointsCopy[i1];

                double slope = p0.slopeTo(p1);

                for (int i2 = i1 + 1; i2 < pointsCopy.length; i2++) {
                    Point p2 = pointsCopy[i2];

                    if (p0.slopeTo(p2) != slope) continue;

                    for (int i3 = i2 + 1; i3 < pointsCopy.length; i3++) {
                        Point p3 = pointsCopy[i3];

                        if (p0.slopeTo(p3) != slope) continue;

                        if (p0.compareTo(p1) < 0 &&
                                p1.compareTo(p2) < 0 &&
                                p2.compareTo(p3) < 0) {
                            segments.add(new LineSegment(p0, p3));
                        }
                    }
                }
            }
        }

        this.segments = segments.toArray(new LineSegment[segments.size()]);
    }

    private void ensureDoesNotContainNull(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
        }
    }

    private void ensureDoesNotContainRepeated(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
        }
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }
}
