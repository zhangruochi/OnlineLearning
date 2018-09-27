/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        
        if(this.x == that.x && this.y == that.y)
            return Double.NEGATIVE_INFINITY;
        else if(this.y == that.y)
            return 0.0;
        else if(this.x == that.x)
            return Double.POSITIVE_INFINITY;
        else
            return (double)(that.y - this.y)/((double)(that.x - this.x));
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if(this.y < that.y || (this.y == that.y && this.x < that.x))
            return -1;
        else if(this.y == that.y && this.x == that.x)
            return 0;
        else
            return 1;

    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    

    public Comparator<Point> slopeOrder() {
        class BySlope implements Comparator<Point>{
            public int compare(Point v, Point w){  
                if(Point.this.slopeTo(v) > Point.this.slopeTo(w))
                    return 1;
                else if(Point.this.slopeTo(v) == Point.this.slopeTo(w))
                    return 0;
                else
                    return -1;
            }
        }
        return new BySlope();
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point a = new Point(3,4);
        Point b = new Point(5,6);

        assert a.compareTo(b) == -1 : "x1<x2,y1<y2 error";
        assert a.slopeTo(b) > 0 : "x1<x2,y1<y2 error";

        Point c = new Point(3,4);
        Point d = new Point(3,5);
        assert c.compareTo(d) == -1 : "x==x error";
        assert c.slopeTo(d) == Double.POSITIVE_INFINITY : "x==x error";

        Point e = new Point(3,4);
        Point f = new Point(2,4);
        assert e.compareTo(f) == 1 : "y==y error";
        assert e.slopeTo(f) == 0: "y==y error";


        assert a.slopeTo(a) == Double.NEGATIVE_INFINITY: "x==x,y==y error";

        Point m = new Point(6,8);
        Point zero = new Point(0,0);
        assert zero.slopeOrder().compare(a,b) == 1 : "slopeOrder error";
        assert zero.slopeOrder().compare(a,m) == 0 : "slopeOrder error";

        System.out.println("successful!");

    }
}