import edu.duke.*;
import java.io.File;

public class PerimeterAssignmentRunner {
    public double getPerimeter (Shape s) {
        // Start with totalPerim = 0
        double totalPerim = 0.0;
        // Start wth prevPt = the last point 
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // Update totalPerim by currDist
            totalPerim = totalPerim + currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // totalPerim is the answer
        return totalPerim;
    }

    public int getNumPoints (Shape s) {
        int num = 0;
        for(Point i: s.getPoints())
            num ++;
        return num;
    }

    public double getAverageLength(Shape s) {
        
        return getPerimeter(s)/(getNumPoints(s));
    }

    public double getLargestSide(Shape s) {
        double largestside = 0.0;
        Point prevPt = s.getLastPoint();
        
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            if(currDist >= largestside)
                largestside = currDist;
        }
        return largestside;
    }

    public double getLargestX(Shape s) {
        double largestX = 0.0;
        for(Point currPt : s.getPoints()){
            if(currPt.getX() > largestX)
                largestX = currPt.getX();
        }
        return largestX;
    }

    public double getLargestPerimeterMultipleFiles() {
        DirectoryResource dr = new DirectoryResource();
        double largestperimeter = 0.0;
        double currperimeter = 0.0;

        for (File f : dr.selectedFiles()) {
            System.out.println(f);
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            currperimeter = getPerimeter(s);
            if(currperimeter >= largestperimeter){
                largestperimeter = currperimeter;
            }
        }
        return largestperimeter;
    }

    public String getFileWithLargestPerimeter() {
        // Put code here
        File temp = null;    // replace this code
        
        DirectoryResource dr = new DirectoryResource();
        double largestperimeter = 0.0;
        double currperimeter = 0.0;

        for (File f : dr.selectedFiles()) {
            System.out.println(f);
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            currperimeter = getPerimeter(s);
            if(currperimeter >= largestperimeter){
                largestperimeter = currperimeter;
                temp = f;
            }
        }
      
        return temp.getName();
    }

    public void testPerimeter () {
        FileResource fr = new FileResource();
        Shape s = new Shape(fr);
        double length = getPerimeter(s);
        System.out.println("perimeter = " + length);
        int numpoints = getNumPoints (s);
        System.out.println("points num = " + numpoints);
        double AverageLength = getAverageLength(s);
        System.out.println("Average = " + AverageLength);
        double large = getLargestSide(s);
        System.out.println("Largest side = " + large);
        double largeX = getLargestX(s);
        System.out.println("Largest X = " + largeX);
        
    }
    
    public void testPerimeterMultipleFiles() {
        double large = getLargestPerimeterMultipleFiles();
        System.out.println("Largest perimeter= " + large);
        

    }

    public void testFileWithLargestPerimeter() {
        // Put code here
        String largestFile = getFileWithLargestPerimeter();
        System.out.println("Largest File = " + largestFile);
        
    }

    // This method creates a triangle that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = "+peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
        }
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        //pr.testPerimeter();
       
        //pr.testPerimeterMultipleFiles();
        pr.testFileWithLargestPerimeter();
        
    }
}
