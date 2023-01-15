package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int numberOfOpenSites;
    private int N;
    private int[] dx = {0,0,1,-1}, dy = {1,-1,0,0} ;
    private WeightedQuickUnionUF unionGridF; //for isFull, only contain one extra space, representing "Top"
    private WeightedQuickUnionUF unionGridP; //for isPercolate, contains 2 extra spaces, representing "Top" and "Bottom"

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N){
        if (N <= 0) throw new IllegalArgumentException("Argument to Percolation should be positive integer.");
        this.grid = new int[N][N];
        this.unionGridF = new WeightedQuickUnionUF(N * N + 1);
        this.unionGridP = new WeightedQuickUnionUF(N * N + 2);
        numberOfOpenSites = 0;
        this.N = N;
    }

    public void CheckIndexRange(int row, int col) {
//        System.out.println(row);
//        System.out.println(col);
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new IndexOutOfBoundsException();
        }
//        if (col < 0 || col > N) {
//            throw new IndexOutOfBoundsException();
//        }
    }



    private int rcTo1D(int row, int col){
        return row * N + col;
    }

    //connect grid[row][col] with its neighboring girds
    private void connectNeibGrid(int row, int col) {
        if (grid[row][col] != 1) return;
        for (int i = 0; i < 4; i++) {
            if ((row + dx[i] >= 0 && row + dx[i] < N) && ((col + dy[i] >= 0 && col + dy[i] < N))) {
                if (grid[row + dx[i]][col + dy[i]] == 1) {
                    unionGridF.union(rcTo1D(row, col), rcTo1D(row + dx[i], col + dy[i]));
                    unionGridP.union(rcTo1D(row, col), rcTo1D(row + dx[i], col + dy[i]));
                }
            }
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col){
        CheckIndexRange(row, col);
        int gridNo = rcTo1D(row, col);
        if (!isOpen(row, col)) {
            grid[row][col] = 1;

            if (row == 0) {
                unionGridF.union(N * N, gridNo);
                unionGridP.union(N * N, gridNo);
            }
            if (row == N - 1) {
                unionGridP.union(N * N + 1, gridNo);
            }

            connectNeibGrid(row, col);
            numberOfOpenSites += 1;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        CheckIndexRange(row, col);
        return grid[row][col] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        CheckIndexRange(row, col);
        return unionGridF.connected(rcTo1D(row, col), N*N);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionGridP.connected(N*N+1, N*N);
    }

//    // use for unit testing (not required, but keep this here for the autograder)
    public static void main(String[] args){}

}
