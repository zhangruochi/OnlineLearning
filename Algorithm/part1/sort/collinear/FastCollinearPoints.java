import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;
import java.lang.IllegalArgumentException;

public class FastCollinearPoints {

    private int index;
    private Point[] points;
 
    private void exchange(Point[] points,int i, int j){
        if(i == j){
            return ;
        }
        else{
            Point tmp = points[j];
            points[j] = points[i];
            points[i] = tmp;            
        }
        return ;

   }

    // finds all line segments containing 4 points
   public FastCollinearPoints(Point[] points){
        this.points = points;
        if (points == null) throw new IllegalArgumentException("points is null"); 
        for(Point point : points){
            if(point == null) 
                throw new IllegalArgumentException("point is null");
        }

        for(int i=0;i<points.length-1;i++){
            for(int j=0;j<points.length;j++){
                if(i != j && points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("two point is same");
            }
        }

        this.index = 1;

        for(int i=0;i<points.length;i+=index){
            //System.out.println(i);
            Arrays.sort(points,1,points.length,points[i].slopeOrder());

            for(int j=1;j<points.length-2;j++){
                if(points[i].slopeTo(points[j]) == points[i].slopeTo(points[j+2])){
                    //System.out.println(i+" "+j+" "+(j+1)+" "+(j+2));
                    //System.out.println(points[i].slopeTo(points[j]) + " " + points[i].slopeTo(points[j+2]));
                    exchange(points,index++,j);
                    exchange(points,index++,j+1);
                    exchange(points,index++,j+2);

                }
            }

        }
        
    }


   public int numberOfSegments(){
        return index;
   }

   public LineSegment[] segments(){
        LineSegment[] lineSegment = new LineSegment[this.points.length/4];
        int index = 0;
        for(int i=0;i<points.length;i+=4){
             Arrays.sort(points,i,i+4);
             lineSegment[index] = new LineSegment(points[i],points[i+3]);
             index++;
        }
        return lineSegment;
   }

   public static void main(String[] args) {
         // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];

        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

         // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw(); 
        }

        StdDraw.show();
        //print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show(); 
    }








}