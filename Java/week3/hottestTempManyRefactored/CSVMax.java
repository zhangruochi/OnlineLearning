/**
 * Find the highest (hottest) temperature in any number of files of CSV weather data chosen by the user.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class CSVMax {
    public CSVRecord hottestHourInFile(CSVParser parser) {
        //start with largestSoFar as nothing
        CSVRecord largestSoFar = null;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            // use method to compare two records
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        //The largestSoFar is the answer
        return largestSoFar;
    }

    public void testHottestInDay () {
        FileResource fr = new FileResource("data/2015/weather-2015-01-01.csv");
        CSVRecord largest = hottestHourInFile(fr.getCSVParser());
        System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                   " at " + largest.get("TimeEST"));
    }

    public CSVRecord hottestInManyDays() {
        CSVRecord largestSoFar = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            // use method to get largest in file.
            CSVRecord currentRow = hottestHourInFile(fr.getCSVParser());
            // use method to compare two records
            largestSoFar = getLargestOfTwo(currentRow, largestSoFar);
        }
        //The largestSoFar is the answer
        return largestSoFar;
    }

    public CSVRecord getLargestOfTwo (CSVRecord currentRow, CSVRecord largestSoFar) {
        //If largestSoFar is nothing
        if (largestSoFar == null) {
            largestSoFar = currentRow;
        }
        //Otherwise
        else {
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
            //Check if currentRow’s temperature > largestSoFar’s
            if (currentTemp > largestTemp) {
                //If so update largestSoFar to currentRow
                largestSoFar = currentRow;
            }
        }
        return largestSoFar;
    }

    public void testHottestInManyDays () {
            CSVRecord largest = hottestInManyDays();
            System.out.println("hottest temperature was " + largest.get("TemperatureF") +
                       " at " + largest.get("DateUTC"));
    }

    public CSVRecord coldestHourInFile(CSVParser parser){
            CSVRecord coldestHour = null;
            for (CSVRecord currentRow : parser){
                coldestHour = getLowestOfTwo(currentRow,coldestHour);
            }
            
            return coldestHour;
    }

    public CSVRecord getLowestOfTwo(CSVRecord currentRow, CSVRecord coldestHour){
        if(Double.parseDouble(currentRow.get("TemperatureF")) == -9999)
            return coldestHour;
        if(coldestHour == null)
            coldestHour = currentRow;
        else{
            double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
            double coldestTemp = Double.parseDouble(coldestHour.get("TemperatureF"));
            if(coldestTemp > currentTemp)
                coldestHour = currentRow;
        }
        return coldestHour;
    }

    public void testColdestHourInFile(){
        FileResource fr = new FileResource();
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("coldest temperature was " + coldest.get("TemperatureF") +
                   " at " + coldest.get("TimeEDT"));
                   
    }
    
    public String fileWithColdestTemperature(){
        String coldestDay = null;
        Double coldestAllDays = Double.POSITIVE_INFINITY;
        
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
            if(Double.parseDouble(coldest.get("TemperatureF")) < coldestAllDays){
                coldestAllDays = Double.parseDouble(coldest.get("TemperatureF"));
                coldestDay = f.getName();
            }
        }
        return coldestDay;
    }

    public void testFileWithColdestTemperature(){
        String coldestDay = fileWithColdestTemperature();
        System.out.println("Coldest day was in file "+ coldestDay);
        FileResource fr = new FileResource("data/2013/"+coldestDay);
        CSVParser parser = fr.getCSVParser();
        System.out.println("coldest temperature is: " + coldestHourInFile(parser).get("TemperatureF"));
        //for(CSVRecord record : fr.getCSVParser())
        //    System.out.println(record.get("DateUTC") +" "+ record.get("TemperatureF"));
        //System.out.println("fuck!");
    }
    
    public CSVRecord getlowestHumidityOfTwo (CSVRecord currentRow, CSVRecord lowestHumiditySoFar) {
        //If lowestHumiditySoFar is nothing
        if(currentRow.get("Humidity").equals("N/A"))
            return lowestHumiditySoFar;

        if (lowestHumiditySoFar == null) {
            lowestHumiditySoFar = currentRow;
        }
        else {
            double currentHumidity = Double.parseDouble(currentRow.get("Humidity"));
            double lowestHumidity = Double.parseDouble(lowestHumiditySoFar.get("Humidity"));
            //Check if currentRow’s temperature > lowestHumiditySoFar’s
            if (currentHumidity < lowestHumidity) {
                //If so update lowestHumiditySoFar to currentRow
                lowestHumiditySoFar = currentRow;
            }
        }
        return lowestHumiditySoFar;
    }


    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord lowestHumiditySoFar = null;
        //For each row (currentRow) in the CSV File
        for (CSVRecord currentRow : parser) {
            // use method to compare two records
            lowestHumiditySoFar = getlowestHumidityOfTwo(currentRow, lowestHumiditySoFar);
        }
        //The lowestHumiditySoFar is the answer
        return lowestHumiditySoFar;
    }

    public void testLowestHumidityInFile(){
        FileResource fr = new FileResource(); 
        CSVParser parser = fr.getCSVParser(); 
        CSVRecord lowestHumidityRecord = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was " + lowestHumidityRecord.get("Humidity") + " at " + lowestHumidityRecord.get("DateUTC"));

    }
    
    public CSVRecord lowestHumidityInManyFiles​(){
        CSVRecord lowestHumidityFar = null;
        DirectoryResource dr = new DirectoryResource();
        // iterate over files
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            // use method to get largest in file.
            CSVRecord currentRow = lowestHumidityInFile(fr.getCSVParser());
            // use method to compare two records
            lowestHumidityFar = getLargestOfTwo(currentRow, lowestHumidityFar);
        }
        return lowestHumidityFar;
    }


    public void testlowestHumidityInManyDays () {
            CSVRecord lowestHumidity = lowestHumidityInManyFiles​();
            System.out.println("lowest Humidity was " + lowestHumidity.get("Humidity") +
                       " at " + lowestHumidity.get("DateUTC"));
    }
    
    public double averageTemperatureInFile​(){
        Double tatolTemperature = 0.0;
        // iterate over files
        int count = 0;
        FileResource fr = new FileResource();
        for (CSVRecord record : fr.getCSVParser()) {
            tatolTemperature += Double.parseDouble(record.get("TemperatureF"));
            count++;
            
        }
        return tatolTemperature/count;
    }

    public void testAverageTemperatureInFile(){
        System.out.println("Average temperature in file is " +  averageTemperatureInFile());
    }
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser,int value){
        Double tatolTemperature = 0.0;
        // iterate over files
        int count = 0;
        
        for (CSVRecord record : parser) {
            if (Integer.parseInt(record.get("Humidity")) >= value){
                tatolTemperature += Double.parseDouble(record.get("TemperatureF"));
                count++;
            }
        }
        
        if(count == 0){
            System.out.println("No temperatures with that humidity");
            return -1;
        }
        
        return tatolTemperature/count;
    }

    public void testAverageTemperatureWithHighHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        int value = 80;
        System.out.println("Average Temp when high Humidity " +  averageTemperatureWithHighHumidityInFile(parser,value));
    }

    
    
}
