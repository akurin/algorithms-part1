import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        ensureDoesNotContainNull(points);
        ensureDoesNotContainRepeated(points);

        List<LineSegment> result = new ArrayList<LineSegment>(points.length);

        for (Point firstPoint : points) {
            Point[] otherPoints = excludeFirstPoint(points, firstPoint);

            Arrays.sort(otherPoints, new SlopeThenCoordComparator(firstPoint));

            int secondPointIndex = 0;
            while (secondPointIndex < otherPoints.length) {
                SegmentCandidate segmentCandidate = new SegmentCandidate(firstPoint, otherPoints, secondPointIndex);

                if (segmentCandidate.pointCount() >= 4 && segmentCandidate.headIsInOrderWithTail()) {
                    result.add(segmentCandidate.lineSegment());
                }

                secondPointIndex += segmentCandidate.pointCount() - 1;
            }

        }

        this.segments = result.toArray(new LineSegment[result.size()]);
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

    private Point[] excludeFirstPoint(Point[] points, Point firstPoint) {
        Point[] otherPoints = new Point[points.length - 1];
        int otherPointsIndex = 0;
        for (Point otherPoint : points) {
            if (firstPoint != otherPoint) {
                otherPoints[otherPointsIndex] = otherPoint;
                otherPointsIndex++;
            }
        }
        return otherPoints;
    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, segments.length);
    }

    private class SlopeThenCoordComparator implements Comparator<Point> {
        final Comparator<Point> slopeComparator;

        public SlopeThenCoordComparator(Point point) {
            this.slopeComparator = point.slopeOrder();
        }

        public int compare(Point o1, Point o2) {
            int result = slopeComparator.compare(o1, o2);
            if (result == 0) {
                result = o1.compareTo(o2);
            }

            return result;
        }
    }

    private class SegmentCandidate {
        private final int pointCount;
        private final LineSegment lineSegment;
        private final boolean headIsInOrderWithTail;

        public SegmentCandidate(Point firstPoint, Point[] otherPoints, int secondPointIndex) {
            double slope = firstPoint.slopeTo(otherPoints[secondPointIndex]);

            int lastPointIndex = secondPointIndex;
            while (lastPointIndex + 1 < otherPoints.length &&
                    slope == firstPoint.slopeTo(otherPoints[lastPointIndex + 1])) {
                lastPointIndex++;
            }

            pointCount = lastPointIndex - secondPointIndex + 2;
            lineSegment = new LineSegment(firstPoint, otherPoints[lastPointIndex]);
            headIsInOrderWithTail = firstPoint.compareTo(otherPoints[secondPointIndex]) < 0;
        }

        public int pointCount() {
            return pointCount;
        }

        public LineSegment lineSegment() {
            return lineSegment;
        }

        public boolean headIsInOrderWithTail() {
            return headIsInOrderWithTail;
        }
    }
}
