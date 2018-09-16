import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

        private double[] nums;
        private int n;
        private int trails;

        public PercolationStats(int n, int trails){

            if (n <= 0 || trails <= 0) throw new IllegalArgumentException("error");
            
            this.n = n;
            this.trails = trails;
            this.nums = new double[trails];

            for(int i=0;i<trails;i++){
                //System.out.println(i);
                Percolation machine = new Percolation(n);
                while(true){
                    int row = StdRandom.uniform(n)+1;
                    int col = StdRandom.uniform(n)+1;
                    machine.open(col,row);
                    if(machine.percolates()){
                        this.nums[i] = ((double)machine.numberOfOpenSites())/(n*n);
                        break;
                    }
                }
            }
        }
        public double mean(){
            return StdStats.mean(this.nums);
        }
        public double stddev(){
            return StdStats.stddev(this.nums);
        }
        public double confidenceLo(){
            return StdStats.mean(this.nums)-1.96/Math.sqrt(this.trails);
        }
        public double confidenceHi(){
            return StdStats.mean(this.nums)+1.96/Math.sqrt(this.trails);
        }
        
        public static void main(String[] args){

            int n = Integer.parseInt(args[0]);
            int trails = Integer.parseInt(args[1]);

            PercolationStats percolationStats = new PercolationStats(n,trails);
            
            System.out.println("mean: " + percolationStats.mean());
            System.out.println("stddev: " + percolationStats.stddev());
            System.out.println("95% confidence interval: " + "[ " + (percolationStats.mean()-1.96*percolationStats.stddev()/Math.sqrt(trails)) + ", " + (percolationStats.mean()+1.96*percolationStats.stddev()/Math.sqrt(trails)) + " ]" );
        }
}