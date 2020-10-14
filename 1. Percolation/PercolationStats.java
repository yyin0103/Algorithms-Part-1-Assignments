
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final int trials;
    private final double[] test;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("length must be positive");
        }

        this.trials = trials;
        test = new double[trials];
        double openCount = 0;

        // Create trial instances of new percolation object od size n
        for (int i=0; i < trials; i++) {
            Percolation percolation = new Percolation(n);

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;

                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    openCount++;
                }
            }
            test[i] = openCount / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(test);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(test);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trails = Integer.parseInt(args[1]);
        PercolationStats percolation = new PercolationStats(n,trails);
        System.out.println("mean:  " + percolation.mean());
        System.out.println("stddev:  " + percolation.stddev());
        System.out.println("confidence Low:  "
                                   + percolation.confidenceLo());
        System.out.println("confidence High:  "
                                   + percolation.confidenceHi());
    }

}
