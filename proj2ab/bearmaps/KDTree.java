package bearmaps;
import java.util.List;
//@Josh Hug video support

public class KDTree implements PointSet {
    private static final boolean HORIZONTAL = false;
    private static final boolean VERTICAL = true;
    private Node root;

    private class Node {
        private Point p;
        private boolean orientation;
        private Node left; //down child
        private Node right; //up child

        Node(Point givenp, boolean po) {
            p = givenp;
            orientation = po;
        }
    }
    public KDTree(List<Point> points) {
        for (Point p : points) {
            root = add(p, root, HORIZONTAL);
        }
    }
    private Node add(Point p, Node n, boolean orientation) {
        if (n == null) {
            return new Node(p, orientation);
        }
        if (p.equals(n.p)) {
            return n;
        }
        int cmp = comparePoints(p, n.p, orientation);
        if (cmp < 0) {
            n.left = add(p, n.left, !orientation);
        } else if (cmp >= 0) {
            n.right = add(p, n.right, !orientation);
        }
        return n;
    }
    private int comparePoints(Point a, Point b, boolean orientation) {
        if (orientation == HORIZONTAL) {
            return Double.compare(a.getX(), b.getX());
        } else {
            return Double.compare(a.getY(), b.getY());
        }
    }
    @Override
    public Point nearest(double x, double y) {
        Point parameterPoint = new Point(x, y);
        Node closest = nearest(root, parameterPoint, root);
        return closest.p;
    }
    private Node nearest(Node n, Point goal, Node best) {
        Node goodSide;
        Node badSide;
        if (n == null) {
            return best;
        }
        if (Point.distance(n.p, goal) < Point.distance(best.p, goal)) {
            best = n;
        }
        int cmp = comparePoints(goal, n.p, n.orientation);
        if (cmp < 0) {
            goodSide = n.left;
            badSide = n.right;
        } else {
            goodSide = n.right;
            badSide = n.left;
        }
        best = nearest(goodSide, goal, best);
        if (badSideBest(n, goal, best)) {
            best = nearest(badSide, goal, best);
        }
        return best;
    }
    private boolean badSideBest(Node n, Point goal, Node best) {
        Point bsPoint;
        if (n.orientation == HORIZONTAL) {
            bsPoint = new Point(n.p.getX(), goal.getY());
        } else {
            bsPoint = new Point(goal.getX(), n.p.getY());
        }
        return Point.distance(bsPoint, goal) < Point.distance(best.p, goal);
    }
}
