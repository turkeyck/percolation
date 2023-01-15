package hw2;
import java.lang.Math;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
public class PercolationStats {
    private final int T, N;
    private int[] counts;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf){
        if (N < 0 || T < 0) throw new IllegalArgumentException();
        this.T = T;
        this.N = N;
        int temp = 0;
        counts = new int[T];
        for (int i=0; i<T;i++) {
            Percolation pcl =  pf.make(N);
            while (!pcl.percolates()) {
                int p = StdRandom.uniform(N);
                int q = StdRandom.uniform(N);
                pcl.open(p, q);
            }
            counts[i] = pcl.numberOfOpenSites();
        }

    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(counts);
    }


    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(counts);
    }


    // low endpoint of 95% confidence interval
    public double confidenceLow(){
        return mean() - 1.96 * stddev()/(Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh(){
        return mean() + 1.96 * stddev()/(Math.sqrt(T));
    }

    public static void main(String args[]) {
        PercolationFactory pf = new PercolationFactory();
        int N = 100;
        int T = 50;
        PercolationStats stats = new PercolationStats(N,T, pf);
        double mean = stats.mean();
        double stddev = stats.stddev();
        double lb = stats.confidenceLow();
        double hb = stats.confidenceHigh();
        double plb = lb/(N*N);
        double phb = hb/(N*N);
        System.out.println( "N=" + N +" " + "T=" + T);
        System.out.println("mean=" + mean + " " + "stddev=" + stddev);
        System.out.println("95% confidence level: [" + lb + ", " + hb + "]");
        System.out.println("probability for percolation is " + "[" + plb + ", " + phb + "]");
    }

}
