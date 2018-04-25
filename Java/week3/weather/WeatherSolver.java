
/**
 * Write a description of WeatherSolver here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;

public class WeatherSolver {


    public CSVRecord compareTemp(CSVRecord coldestSoFar,CSVRecord currentRow){
         if(coldestSoFar == null){
                coldestSoFar = currentRow;
            }else{
                double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                if(currentTemp < Double.parseDouble(coldestSoFar.get("TemperatureF")))
                    coldestSoFar = currentRow;
        }
        return coldestSoFar;

    }

    public CSVRecord compareHumi(CSVRecord lowestHumidity,CSVRecord currentRow){
         if(lowestHumidity == null){
                lowestHumidity = currentRow;
            }else{
                double currentHuminity = Double.parseDouble(currentRow.get("Humidity"));
                if(currentHuminity < Double.parseDouble(lowestHumidity.get("Humidity")))
                    lowestHumidity = currentRow;
        }
        return lowestHumidity;

    }


    public CSVRecord coldestHourInFile(CSVParser parser){
        CSVRecord coldestSoFar = null;

        for(CSVRecord currentRow : parser){
            if(Double.parseDouble(currentRow.get("TemperatureF")) < 0)
                continue;
            coldestSoFar = compareTemp(coldestSoFar,currentRow);
        }
        return coldestSoFar;
    }

    public void testColdestHourInFile(){
        FileResource fr = new FileResource();
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("coldest temperature is: "+ coldest.get("TemperatureF") + " at " + coldest.get("TimeEST"));
    }

    public String fileWithColdestTemperature(){
        DirectoryResource dr = new DirectoryResource();
        CSVRecord coldestDay = null;
        String filename = "";

        for(File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord coldestForOneDay = coldestHourInFile(fr.getCSVParser());

            if(coldestDay == null){
                coldestDay = coldestForOneDay;
            }else{
                double currentTemp = Double.parseDouble(coldestForOneDay.get("TemperatureF"));
                if(currentTemp < Double.parseDouble(coldestDay.get("TemperatureF"))){
                    coldestDay = coldestForOneDay;
                    filename = f.getName();
                }
            }
        }

        System.out.println("coldest temperature is: "+ coldestDay.get("TemperatureF") + " at " + coldestDay.get("DateUTC"));
        return filename;
    }
    
    public void testFileWithColdestTemperature(){
        System.out.println(fileWithColdestTemperature());
    }

    public CSVRecord lowestHumidityInFile(CSVParser parser){
        CSVRecord lowestHumidity = null;

        for(CSVRecord currentRow : parser){
            if(currentRow.get("Humidity").equals("N/A"))
                continue;
            lowestHumidity = compareHumi(lowestHumidity,currentRow);
        }
        return lowestHumidity;
    }

    public void testLowestHumidityInFile(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("lowest humidity is: "+csv.get("Humidity") + " at " + csv.get("DateUTC"));
    }


    public String lowestHumidityInManyFiles(){
        DirectoryResource dr = new DirectoryResource();
        CSVRecord HumidityDay = null;
        String filename = "";
        for(File f: dr.selectedFiles()){
            FileResource fr = new FileResource(f);
            CSVRecord HumidityForOneDay = lowestHumidityInFile(fr.getCSVParser());
            if(HumidityDay == null){
                HumidityDay = HumidityForOneDay;
            }else{
                double currentHuminity = Double.parseDouble(HumidityForOneDay.get("Humidity"));
                if(currentHuminity < Double.parseDouble(HumidityDay.get("Humidity"))){
                    HumidityDay = HumidityForOneDay;
                    filename = f.getName();
                }
            }    
        }

        System.out.println("Lowest Humidity is: "+ HumidityDay.get("Humidity") + " at " + HumidityDay.get("DateUTC"));
        return filename;
    }
    
    public void testLowestHumidityInManyFiles(){
        System.out.println(lowestHumidityInManyFiles());
    }


    public double averageTemperatureInFile(CSVParser parser){
        double sum = 0;
        int count = 0;

        for(CSVRecord currentRow : parser){
            sum += Double.parseDouble(currentRow.get("TemperatureF"));
            count ++;
        }
        return sum/count;
    }

    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value){
        double sum = 0;
        int count = 0;

        for(CSVRecord currentRow : parser){
            if(Integer.parseInt(currentRow.get("Humidity")) >= value){
                sum += Double.parseDouble(currentRow.get("TemperatureF"));
                count ++;
            }
        }
        
        if(count == 0){
            System.out.println("No temperatures with that humidity");
            return 0.0;
        }
        return sum/count;
    }
    
    
    public void testAverage(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        System.out.println(averageTemperatureInFile(parser));
        parser = fr.getCSVParser();
        int value = 80;
        System.out.println(averageTemperatureWithHighHumidityInFile(parser,value));
        
    }
}


