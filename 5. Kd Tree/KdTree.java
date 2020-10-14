/* *****************************************************************************
 *  Topic:     KdThree (2d Implementation)
 *             A 2d-tree is a generalization of a BST to two-dimensional keys.
 *             The idea is to build a BST with points in the nodes, using the x-
 *             and y-coordinates of the points as keys in strictly alternating
 *             sequence.
 *  @author:   Ying Chu
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;

public class KdTree {

    private final RectHV bound = new RectHV(0.0, 0.0, 1.0, 1.0);
    private KdNode root;
    private int size;

    private class KdNode {
        private KdNode left;
        private KdNode right;
        private final boolean vertical;
        private final Point2D p;

        public KdNode(Point2D p, KdNode left, KdNode right, boolean vertical) {
            this.p = p;
            this.left = left;
            this.right = right;
            this.vertical = vertical;
        }

        public RectHV leftRect(RectHV rect) {
            RectHV left;
            if (this.vertical) {
                left = new RectHV(rect.xmin(), rect.ymin(), this.p.x(), rect.ymax());
            } else {
                left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), this.p.y());
            }
            return left;
        }

        public RectHV rightRect(RectHV rect) {
            RectHV right;
            if (this.vertical) {
                right = new RectHV(this.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            } else {
                right = new RectHV(rect.xmin(), this.p.y(), rect.xmax(), rect.ymax());
            }
            return right;
        }
    }

    // construct an empty tree of points
    public KdTree() {
        size = 0;
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return (size == 0);
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p);
    }

    // does the subtree of the root contains p(x,y)?
    private boolean contains(KdNode node, Point2D p) {

        if (node == null) return false;

        if (node.p.equals(p)) return true;

        if (node.vertical && p.x() < node.p.x() || !node.vertical && p.y() < node.p.y()) {
            return contains(node.left, p);
        } else {
            return contains(node.right, p);
        }

    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        if (isEmpty()) {
            size++;
            root = new KdNode(p, null, null, true);
        } else {
            insert(p, root);
        }
    }

    private void insert(Point2D p, KdNode node) {
        int cmp = compareTo(p, node);
        // 如果比较结果是小于，那么就是要往左边走，右边同理
        if (cmp < 0) {
            // 走到头了就新建，否则继续走
            if (node.left == null) {
                size++;
                node.left = new KdNode(p, null, null, !node.vertical);
            } else {
                insert(p, node.left);
            }
        } else if (cmp > 0) {
            if (node.right == null) {
                size++;
                node.right = new KdNode(p, null, null, !node.vertical);
            } else {
                insert(p, node.right);
            }
        }
        // 重叠的点，size 不加 1
    }

    private int compareTo(Point2D p, KdNode n) {
        if (n.vertical) {
            if (Double.compare(p.x(), n.p.x()) == 0) {
                return Double.compare(p.y(), n.p.y());
            } else {
                return Double.compare(p.x(), n.p.x());
            }
        } else {
            if (Double.compare(p.y(), n.p.y()) == 0) {
                return Double.compare(p.x(), n.p.x());
            } else {
                return Double.compare(p.y(), n.p.y());
            }
        }
    }

    // draw all points to standard draw
    public void draw() {
        if (isEmpty()) return;
        draw(root);
    }

    private void draw(final KdNode node) {
        if (node == null) return;

        // draw point
        StdDraw.setPenColor(StdDraw.BLACK);
        node.p.draw();

        // draw line
        if (node.vertical) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), bound.ymin(), node.p.x(), bound.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(bound.xmin(), node.p.y(), bound.xmax(), node.p.y());
        }

        // draw subtree
        draw(node.left);
        draw(node.right);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Null Rectangle");
        }

        if (isEmpty()) {
            return null;
        }
        ArrayList<Point2D> pointsInRect = new ArrayList<>();

        range(root, bound, rect, pointsInRect);
        return pointsInRect;
    }

    private void range(KdNode node, RectHV nrect, RectHV rect, ArrayList<Point2D> list) {

        if (node != null && rect.intersects(nrect)) {
            if (rect.contains(node.p)) {
                list.add(node.p);
            }

        RectHV left = node.leftRect(nrect);
        RectHV right = node.rightRect(nrect);
        range(node.left, left, rect, list);
        range(node.right, right, rect, list);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Null point");
        }

        if (isEmpty()) {
            return null;
        } else {
            return nearest(p, root, bound, null );
        }
    }

    private Point2D nearest(Point2D p, KdNode node, RectHV nrect, Point2D nearest) {

        if (node != null) {
            if (nearest == null) {
                nearest = node.p;
            }

            if (nearest.distanceSquaredTo(p) >= nrect.distanceSquaredTo(p)) {
                if (nearest.distanceSquaredTo(p) > node.p.distanceSquaredTo(p)) {
                    nearest = node.p;
                }

                RectHV left = node.leftRect(nrect);
                RectHV right = node.rightRect(nrect);
                if (node.left != null && node.leftRect(nrect).contains(p)) {
                    nearest = nearest(p, node.left, left, nearest);
                    nearest = nearest(p, node.right, right, nearest);
                } else {
                    nearest = nearest(p, node.right, right, nearest);
                    nearest = nearest(p, node.left, left, nearest);
                }
            }
        }
        return nearest;
    }
}
