/* *****************************************************************************
 *  @author:   Ying Chu
 **************************************************************************** */
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    // constructs the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {

        // horizontal
        if (that.y == this.y) {
            return 0.0;
        }
        // vertical
        if (that.x == this.x) {
            return Double.POSITIVE_INFINITY;
        }
        // same point
        if (this == that) {
            return Double.NEGATIVE_INFINITY;
        }

        return (double) (that.y - this.y) / (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {

        if (that.y == this.y && that.x == this.x) {
            return 0;
        }

        if ((this.y >= that.y && this.x < that.x) || (this.y <= that.y && this.x < that.x)) {
            return -1;
        }

        return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return (o1, o2) -> Double.compare(slopeTo(o1), slopeTo(o2));
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
