/* *****************************************************************************
 *  Topic:    Faster, Sorting-Based Solution
 *  @auther:  Ying Chu
 *  Method:
    1. Think of p as the origin.
    2. For each other point q, determine the slope it makes with p.
    3. Sort the points according to the slopes they makes with p.
    4. Check if any 3 (or more) adjacent points in the sorted order have equal
       slopes with respect to p. If so, these points, together with p, are collinear.
 **************************************************************************** */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {
    private final ArrayList<LineSegment> lineSegmentList = new ArrayList<LineSegment>();

    public FastCollinearPoints(Point[] points) {
        // three Corner cases
        // 1. if the argument to the constructor is null
        if (points == null) {
            throw new IllegalArgumentException("no point in the array");
        }
        // 2. if any point in the array is null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("null point occurs");
            }
        }
        Point[] pCopy = points.clone();
        Arrays.sort(pCopy);
        // 3. containing a repeated point
        for (int i = 0; i < (pCopy.length - 1); i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("points duplicated");
            }
        }

        for (int i = 0; i < pCopy.length; i++) {
            Point p = pCopy[i]; // p = points
            // copy the array without reference point p
            Point[] pSortBySlope = pCopy.clone();
            // sort the points by their slope with p
            Arrays.sort(pSortBySlope, p.slopeOrder());

            // check the slope between each point and p
            int n = 1;
            while (n < pSortBySlope.length) {
                LinkedList<Point> pSameSlope = new LinkedList<>();
                // determine reference slope; use p and the point after p
                final double refSlope = p.slopeTo(pSortBySlope[n]);

                do {
                    pSameSlope.add(pSortBySlope[n++]);
                } while (n < pSortBySlope.length && p.slopeTo(pSortBySlope[n]) == refSlope);

                if (pSameSlope.size() >= 3 && p.compareTo(pSameSlope.peek()) < 0) {
                    Point max = pSameSlope.removeLast();
                    lineSegmentList.add(new LineSegment(p, max));
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegmentList.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegmentList.toArray(new LineSegment[0]);
    }

}
