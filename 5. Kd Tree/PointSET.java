/* *****************************************************************************
 *  Topic:    PointSET
 *            Used to represents a set of points in the unit square.
 *  @author:  Ying Chu
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;
import java.util.ArrayList;


public class PointSET {
    private final TreeSet<Point2D> point2DSet;

    // construct an empty set of points
    public PointSET() {
        this.point2DSet = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return this.point2DSet.isEmpty();
    }

    // number of points in the set
    public int size() {
        return this.point2DSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Null point");
        }
        if (!contains(p)) {
            point2DSet.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return this.point2DSet.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : point2DSet) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Null Rectangle");
        }

        ArrayList<Point2D> pInRect = new ArrayList<Point2D>();
        for (Point2D p : point2DSet) {
            if (rect.contains(p)) {
                pInRect.add(p);
            }
        }
        return pInRect;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Null point");
        }

        Point2D nearestNeigh = null;
        double minDist = Double.POSITIVE_INFINITY;

        for (Point2D point : point2DSet) {
            double dist = p.distanceSquaredTo(point);
            if (dist < minDist) {
                minDist = dist;
                nearestNeigh = point;
            }
        }
        return nearestNeigh;
    }
}
