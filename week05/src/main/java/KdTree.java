import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KdTree {
    private Node root;
    private int size;

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (root == null) {
            root = new Node(p, Orientation.Vertical, new RectHV(0, 0, 1, 1));
            size++;
        } else {
            if (root.insert(p)) {
                size++;
            }
        }
    }

    public boolean contains(Point2D p) {
        if (root == null) {
            return false;
        }

        return root.contains(p);
    }

    public void draw() {
    }

    public Iterable<Point2D> range(RectHV rect) {
        List<Point2D> collectedPoints = new ArrayList<>();

        if (root != null) {
            root.collectPointsInRect(rect, collectedPoints);
        }

        return collectedPoints;
    }

    public Point2D nearest(Point2D p) {
        if (root == null) {
            return null;
        }

        return root.nearest(p, new Node.NearestResult(null, Double.MAX_VALUE)).point();
    }

    public static void main(String[] args) {
    }

    private enum Orientation {
        Horizontal,
        Vertical
    }

    private static class Node {
        private Point2D p;
        private Orientation orientation;
        private final Comparator<Point2D> pointComparator;
        private Node lb;
        private Node rt;
        private RectHV rect;

        public Node(Point2D p, Orientation orientation, RectHV rect) {
            this.p = p;
            this.orientation = orientation;
            this.rect = rect;
            this.pointComparator = comparatorByOrientation(orientation);
        }

        private static Comparator<Point2D> comparatorByOrientation(Orientation orientation) {
            switch (orientation) {
                case Horizontal:
                    return new YComparator();
                case Vertical:
                    return new XComparator();
                default:
                    throw new IllegalArgumentException();
            }
        }

        public boolean insert(Point2D p) {
            int compare = pointComparator.compare(this.p, p);
            if (compare == 0 && this.p.equals(p))
                return false;

            if (compare > 0) {
                if (lb == null) {
                    lb = new Node(p, reverseOrientation(), leftOrBottomRect());
                    return true;
                } else {
                    return lb.insert(p);
                }
            } else if (rt == null) {
                rt = new Node(p, reverseOrientation(), rightOrTopRect());
                return true;
            } else {
                return rt.insert(p);
            }
        }

        private RectHV leftOrBottomRect() {
            switch (orientation) {
                case Horizontal:
                    return bottomRect();
                case Vertical:
                    return leftRect();
                default:
                    throw new IllegalArgumentException();
            }
        }

        private RectHV rightOrTopRect() {
            switch (orientation) {
                case Horizontal:
                    return topRect();
                case Vertical:
                    return rightRect();
                default:
                    throw new IllegalArgumentException();
            }
        }

        private RectHV leftRect() {
            return new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax());
        }

        private RectHV topRect() {
            return new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
        }

        private RectHV rightRect() {
            return new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax());
        }

        private RectHV bottomRect() {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
        }

        private Orientation reverseOrientation() {
            switch (orientation) {
                case Horizontal:
                    return Orientation.Vertical;
                case Vertical:
                    return Orientation.Horizontal;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public void collectPointsInRect(RectHV rect, List<Point2D> collectedPoints) {
            if (rect.contains(p)) {
                collectedPoints.add(p);
            }

            if (this.rect.intersects(rect)) {
                if (lb != null) {
                    lb.collectPointsInRect(rect, collectedPoints);
                }

                if (rt != null) {
                    rt.collectPointsInRect(rect, collectedPoints);
                }
            }
        }

        public NearestResult nearest(Point2D p, NearestResult nearestResult) {
            if (rect.distanceTo(p) > nearestResult.distance()) {
                return nearestResult;
            }

            NearestResult result = nearestResult;

            double distanceToThisPoint = p.distanceTo(this.p);
            if (distanceToThisPoint < result.distance()) {
                result = new NearestResult(this.p, distanceToThisPoint);
            }

            Node primarySubtreeToSearch;
            Node secondarySubtreeToSearch;

            if (lb != null && pointComparator.compare(this.p, p) > 0) {
                primarySubtreeToSearch = lb;
                secondarySubtreeToSearch = rt;
            } else {
                primarySubtreeToSearch = rt;
                secondarySubtreeToSearch = lb;
            }

            if (primarySubtreeToSearch != null) {
                result = primarySubtreeToSearch.nearest(p, result);
            }

            if (secondarySubtreeToSearch != null) {
                result = secondarySubtreeToSearch.nearest(p, result);
            }

            return result;
        }

        public boolean contains(Point2D p) {
            int compare = pointComparator.compare(this.p, p);
            if (compare == 0 && this.p.equals(p)) {
                return true;
            }

            if (lb != null && compare > 0) {
                return lb.contains(p);
            } else if (rt != null) {
                return rt.contains(p);
            }

            return false;
        }

        private static class XComparator implements Comparator<Point2D> {
            @Override
            public int compare(Point2D thisPoint, Point2D thatPoint) {
                return Double.compare(thisPoint.x(), thatPoint.x());
            }
        }

        private static class YComparator implements Comparator<Point2D> {
            @Override
            public int compare(Point2D thisPoint, Point2D thatPoint) {
                return Double.compare(thisPoint.y(), thatPoint.y());
            }
        }

        private static class NearestResult {
            private Point2D point;
            private double distance;

            public NearestResult(Point2D point, double distance) {
                this.point = point;
                this.distance = distance;
            }

            public Point2D point() {
                return point;
            }

            public double distance() {
                return distance;
            }
        }
    }
}
