/* *****************************************************************************
 *  Topic:    Bruce Force
 *            Examine 4 points at a time and checks whether they all lie on the
 *            same line segment, returning all such line segments. To check whether
 *            the 4 points p, q, r, and s are collinear, check whether the three
 *            slopes between p and q, between p and r, and between p and s are
 *            all equal.
 *  @author:  Ying Chu
 **************************************************************************** */
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private final ArrayList<LineSegment> lineSegmentList = new ArrayList<LineSegment>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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
        Point[] pcopy = points.clone();
        Arrays.sort(pcopy);
        // 3. containing a repeated point
        for (int i = 0; i < (pcopy.length - 1); i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("points duplicated");
            }
        }

        // find line segment, with point p, q, r, s
        for (int p = 0; p < pcopy.length - 3; p++) {
            for (int q = p + 1; q < pcopy.length - 2; q++) {
                // slope between point p and q
                double slopePQ = pcopy[p].slopeTo(pcopy[q]);
                for (int r = q + 1; r < pcopy.length - 1; r++) {
                    // slope between point p and r
                    double slopePR = pcopy[p].slopeTo(pcopy[r]);
                    if (slopePQ != slopePR) continue;
                    for (int s = r + 1; s < pcopy.length; s++) {
                        // slope between point p and s
                        double slopePS = pcopy[p].slopeTo(pcopy[s]);
                        if (slopePQ != slopePS) continue;
                        lineSegmentList.add(new LineSegment(pcopy[p], pcopy[s]));
                    }
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
