import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;

    public PercolationStats(int n, int trials) {
        if (n <= 0) throw new IllegalArgumentException();
        if (trials <= 0) throw new IllegalArgumentException();

        results = new double[trials];

        for (int trial = 0; trial < trials; trial++) {
            results[trial] = calculateWhenPercolates(n) / (n * n);
        }
    }

    private static double calculateWhenPercolates(int n) {
        Percolation percolation = new Percolation(n);

        while (true) {
            int row = StdRandom.uniform(1, n + 1);
            int column = StdRandom.uniform(1, n + 1);
            if (!percolation.isOpen(row, column)) {
                percolation.open(row, column);
                if (percolation.percolates()) {
                    return percolation.numberOfOpenSites();
                }
            }
        }
    }

    public double mean() {
        return StdStats.mean(this.results);
    }

    public double stddev() {
        return StdStats.stddev(this.results);
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(results.length);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(results.length);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);

        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.stddev());
        StdOut.println("95% confidence interval = " + (ps.confidenceLo() + ", " + ps.confidenceHi()));
    }
}