import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF percolateCheckingUF;
    private final WeightedQuickUnionUF isFullCheckingUF;
    private int n;
    private boolean[] sites;
    private int numberOfOpenSites;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        this.n = n;
        sites = new boolean[n * n];

        percolateCheckingUF = new WeightedQuickUnionUF(n * n + 2);
        isFullCheckingUF = new WeightedQuickUnionUF(n * n + 1);

    }

    public void open(int row, int col) {
        validateRowCol(row, col);

        if (isOpen(row, col))
            return;

        sites[(row - 1) * n + (col - 1)] = true;

        if (row == 1) {
            percolateCheckingUF.union(0, siteIndex(row, col));
            isFullCheckingUF.union(0, siteIndex(row, col));
        }

        if (row == n) {
            percolateCheckingUF.union(n * n + 1, siteIndex(row, col));
        }

        if (row - 1 >= 1 && isOpen(row - 1, col)) {
            percolateCheckingUF.union(siteIndex(row - 1, col), siteIndex(row, col));
            isFullCheckingUF.union(siteIndex(row - 1, col), siteIndex(row, col));
        }

        if (row + 1 <= n && isOpen(row + 1, col)) {
            percolateCheckingUF.union(siteIndex(row + 1, col), siteIndex(row, col));
            isFullCheckingUF.union(siteIndex(row + 1, col), siteIndex(row, col));
        }

        if (col - 1 >= 1 && isOpen(row, col - 1)) {
            percolateCheckingUF.union(siteIndex(row, col - 1), siteIndex(row, col));
            isFullCheckingUF.union(siteIndex(row, col - 1), siteIndex(row, col));
        }

        if (col + 1 <= n && isOpen(row, col + 1)) {
            percolateCheckingUF.union(siteIndex(row, col + 1), siteIndex(row, col));
            isFullCheckingUF.union(siteIndex(row, col + 1), siteIndex(row, col));
        }

        numberOfOpenSites++;
    }

    private void validateRowCol(int row, int col) {
        if (row < 1 || row > n) throw new IndexOutOfBoundsException();
        if (col < 1 || col > n) throw new IndexOutOfBoundsException();
    }

    private int siteIndex(int row, int col) {
        return (row - 1) * n + (col - 1) + 1;
    }

    public boolean isOpen(int row, int col) {
        validateRowCol(row, col);

        return sites[(row - 1) * n + (col - 1)];
    }

    public boolean isFull(int row, int col) {
        validateRowCol(row, col);

        return isFullCheckingUF.connected(siteIndex(row, col), 0);
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return percolateCheckingUF.connected(0, n * n + 1);
    }

    public static void main(String[] args) {
    }
}