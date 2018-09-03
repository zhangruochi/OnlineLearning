/**
 * Print out total number of babies born, as well as for each gender, in a given CSV file of baby name data.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyBirths {
    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                           " Gender " + rec.get(1) +
                           " Num Born " + rec.get(2));
            }
        }
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;

        int totalBoyNames = 0;
        int totalGirlNames = 0;
        int totalNames = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                totalBoyNames++;
            }
            else {
                totalGirls += numBorn;
                totalGirlNames++;
            }
        }
        totalNames = totalBoyNames + totalGirlNames;
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);

        System.out.println("total birth names = " + totalBoyNames);
        System.out.println("female girl names = " + totalGirlNames);
        System.out.println("total names = " + totalNames);
    }

    public int getRank(int year,String name,String gender){
        FileResource fr = new FileResource("data/yob" + year + ".csv");
        int rank = 1;
        int count = 0;
        for (CSVRecord rec : fr.getCSVParser(false)){
            if(rec.get(1).equals("F"))
                count ++;
            if(rec.get(0).equals(name) && rec.get(1).equals(gender)){
                if(rec.get(1).equals("M"))
                    rank -= count;
                return rank;
            }    
            rank ++;      
        }
        return -1;
    }
    
    public int getRankTwo(FileResource fr,String name,String gender){
        int rank = 1;
        int count = 0;
        for (CSVRecord rec : fr.getCSVParser(false)){
            if(rec.get(1).equals("F"))
                count ++;
            if(rec.get(0).equals(name) && rec.get(1).equals(gender)){
                if(rec.get(1).equals("M"))
                    rank -= count;
                return rank;
            }    
            rank ++;      
        }
        return -1;
    }
    
    public String getName(int year, int rank, String gender){
        FileResource fr = new FileResource("data/yob" + year + ".csv");
        int count = 0;
        for(CSVRecord rec : fr.getCSVParser(false)){
            if(gender.equals("F")){
                count ++;
                if(count == rank)
                    return rec.get(0);
  
            }else{
                if(rec.get(1).equals("F"))
                    continue;
                else{
                    count ++;
                    if(count == rank)
                        return rec.get(0);
                }
            }
        }
        return "NO NAME";
    }
    
    public void whatIsNameInYear(String name,int year,int newYear,String gender){
        int rank = getRank(year,name,gender);
        System.out.println(getName(newYear,rank,gender));
    }
    
    public int yearOfHighestRank(String name,String gender){
        double maxRank = Double.POSITIVE_INFINITY;
        int currentRank = -1;
        String year = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            currentRank = getRankTwo(fr,name,gender);
            if(currentRank < maxRank || maxRank == -1){
                maxRank = currentRank;
                year = f.getName();
            }
                
        }
        if(maxRank == -1)
            return -1;
        System.out.println(year);    
        return 0;
    
    }
    
    public double getAverageRank(String name,String gender){
        double total = 0.0;
        int count = 0;
        int currentRank = 0;
        
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            currentRank = getRankTwo(fr,name,gender);
            total += currentRank;
            count ++;
        }
        
        return total/count;        
   }
        
   public int getTotalBirthsRankedHigher(String name,int year,String gender){
       FileResource fr = new FileResource("data/yob" + year + ".csv");
       int total = 0;
       for (CSVRecord rec : fr.getCSVParser(false)){
           if(gender.equals(rec.get(1))){
               if(rec.get(0).equals(name))
                    break;
               total += Integer.parseInt(rec.get(2));     
            }
       }
       return total;
    }
   

   public void testTotalBirths () {
        //FileResource fr = new FileResource();
        FileResource fr = new FileResource("data/yob1905.csv");
        totalBirths(fr);
    }
}
