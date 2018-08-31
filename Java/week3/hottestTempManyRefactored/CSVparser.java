
/**
 * Write a description of CSVparser here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;

public class CSVparser {

    public void tester(){
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        //countryInfo(parser,"Nauru");
        //parser = fr.getCSVParser();
        //listExportersTwoProducts(parser,"cotton","flowers");
        //parser = fr.getCSVParser();
        bigExporters(parser,"$999,999,999,999");
        //parser = fr.getCSVParser();
        //System.out.println(sugerNum(parser));
       
    }

    public String countryInfo(CSVParser parser, String country){
        for(CSVRecord record : parser ){
            if(record.get("Country").equals(country))
                System.out.println(record.get("Country") +": " + record.get("Exports")+ ": "+ record.get("Value (dollars)"));
        }
        return "NOT FOUND";
    }

    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2){
        for(CSVRecord record : parser ){
            if(record.get("Exports").contains(exportItem1) && record.get("Exports").contains(exportItem2))
                System.out.println(record.get("Country"));
        }   
    }

    public void bigExporters(CSVParser parser, String amount){
        for(CSVRecord record : parser ){
            if(record.get("Value (dollars)").length() > amount.length()){
                System.out.println(record.get("Country") + " " + record.get("Value (dollars)"));
            } 
        }        
    }
    
    public int sugerNum(CSVParser parser){
        int count= 0;
        for(CSVRecord record : parser ){
            if(record.get("Exports").contains("cocoa")){
               count++;
            } 
        } 
        return count;
    }
    
    
}