import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int[] openOrNot;
    private int width;
    private WeightedQuickUnionUF unionUF;

    public Percolation(int n){
        // create n-by-n grid, with all sites blocked
        if (n <= 0) throw new IllegalArgumentException("n should positive");
        width = n;
        
        unionUF = new WeightedQuickUnionUF(width*width+2);
        openOrNot = new int[width*width+2];
        openOrNot[0] = 1;
        openOrNot[width*width+1] = 1;
    }                

    public void open(int row, int col){
        // open site (row, col) if it is not open already
        if (row <= 0 || row > width) throw new IllegalArgumentException("row index out of bounds");
        if (col <= 0 || col > width) throw new IllegalArgumentException("col index out of bounds");


        int index = (row-1)*width + col;
        this.openOrNot[index] = 1;

        if(row == 1)
            this.unionUF.union(index,0);
        if(row == width)
            this.unionUF.union(index,width*width+1);

        if(row>1 && this.openOrNot[index-width] == 1)
            this.unionUF.union(index,index-width);
        if(col>1 && this.openOrNot[index-1] == 1)
            this.unionUF.union(index,index-1);
        if(col<width && this.openOrNot[index+1] == 1)
            this.unionUF.union(index,index+1);
        if(row<width && this.openOrNot[index+width] == 1)
            this.unionUF.union(index,index+width);   

    }    

    public boolean isOpen(int row, int col){
        // is site (row, col) open?
        if (row <= 0 || row > width) throw new IllegalArgumentException("row index out of bounds");
        if (col <= 0 || col > width) throw new IllegalArgumentException("col index out of bounds");

        if(this.openOrNot[(row-1)*width + col] == 1)
            return true;
        else
            return false;
    } 

    public boolean isFull(int row, int col){
        // is site (row, col) full?
        if (row <= 0 || row > width) throw new IllegalArgumentException("row index out of bounds");
        if (col <= 0 || col > width) throw new IllegalArgumentException("col index out of bounds");
        
        return unionUF.connected((row-1)*width + col,0);
    }  

    public int numberOfOpenSites(){
        // number of open sites
        int count = 0;
        for(int i=1; i<= this.width * this.width; i++){
            if(openOrNot[i]==1)
                count++;
        }
        //System.out.println(count);
        return count;
    }       

    public boolean percolates(){
        int row = width;
        return unionUF.connected(0,width*width+1);

    }     

}