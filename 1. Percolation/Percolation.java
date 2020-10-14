/* *****************************************************************************
 *  Topic:        Percolation.
 *                Given a composite systems comprised of randomly distributed
 *                insulating and metallic materials: what fraction of the materials
 *                need to be metallic so that the composite system is an electrical
 *                conductor? Given a porous landscape with water on the surface (or
 *                oil below), under what conditions will the water be able to drain
 *                through to the bottom (or the oil to gush through to the surface)?
 *                Scientists have defined an abstract process known as percolation
 *                to model such situations.
 *  @author:      Ying Chu
 **************************************************************************** */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n; // grid length
    private final WeightedQuickUnionUF grid, altGrid;  // n * n grid + virtual top & bottom
    private boolean[] open;  // true:open, false:blocked
    private int count = 0; // number of open sites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("length must be positive");
        }
        this.n = n;
        grid = new WeightedQuickUnionUF(n * n + 2);
        altGrid = new WeightedQuickUnionUF(n * n + 1);
        open = new boolean[n * n +2];

        //The sites are block at first hand
        for (int i = 1; i < n * n; i++) {
            open[i] = false;
        }

        open[0] = true; //virtual top
        open[n * n + 1] = true; //virtual bottom
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        //name row,col 0 to N ** 2
        int index = (row - 1) * n + col;
        if (!open[index]) {
            open[index] = true;
            count++;
        }

        //connect head and tails
        if (row == 1) {
            grid.union(0, index);
            altGrid.union(0, index);
        }
        if (row == n) {
            grid.union(n * n + 1, index);
        }

        // union judge for 4 directions
        // left
        if (row != 1 && isOpen(row - 1, col)) {
            grid.union(index, index - n);
            altGrid.union(index, index - n);
        }
        // right
        if (row != n && isOpen(row + 1, col)) {
            grid.union(index, index + n);
            altGrid.union(index, index + n);
        }
        // below
        if (col != 1 && isOpen(row, col - 1)) {
            grid.union(index, index - 1);
            altGrid.union(index, index - 1);
        }
        // above
        if (col != n && isOpen(row, col + 1)) {
            grid.union(index, index + 1);
            altGrid.union(index, index + 1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        return open[(row - 1) * n + col];
    }

    // is the site (row, col) full? Connected to the top to check.
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("Index out of bounds");
        }
        int index = (row - 1) * n + col;
        return grid.connected(0, index) && altGrid.connected(0, index);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid.connected(0, n * n + 1);
    }

    // test client
    public static void main(String[] args) {

        int n = StdIn.readInt();
        Percolation percolation = new Percolation(n);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            percolation.open(row, col);
        }
        StdOut.println("percolation is " + percolation.percolates());
    }
}
