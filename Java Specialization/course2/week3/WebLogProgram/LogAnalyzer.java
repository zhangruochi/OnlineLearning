
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         // complete constructor
         records = new ArrayList<LogEntry>();
     }
        
     public void readFile(String filename) {
         // complete method
         FileResource fr = new FileResource(filename);
         for(String log: fr.lines()){
             LogEntry entry = WebLogParser.parseEntry(log);
             records.add(entry);
         }
         
     }
     
     public int countUniqueIPs(){
         ArrayList<String> uniqueIps = new ArrayList<String>();
         for(LogEntry log : records){
             if(!uniqueIps.contains(log.getIpAddress()));
                uniqueIps.add(log.getIpAddress());
         }
         return uniqueIps.size();
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     public void printAllHigherThanNum(int num){
         for(LogEntry log : records){
             if(log.getStatusCode() > num){
                 System.out.println(log);
             }
         }
     }
     
     public ArrayList<String> uniqueIPVisitsOnDay(String someday){
         ArrayList<String> uniqueIps = new ArrayList<String>();

         for(LogEntry log : records){
             String[] timeArray = log.getAccessTime().toString().split(" ");
             if((timeArray[1]+" "+timeArray[2]).equals(someday) && !uniqueIps.contains(log.getIpAddress()))
                uniqueIps.add(log.getIpAddress());
         }
         return uniqueIps;
     }
     
     public int countUniqueIPsInRange(int low,int high){
         ArrayList<String> uniqueIps = new ArrayList<String>();

         for(LogEntry log : records){
           
             if(log.getStatusCode() >= low && log.getStatusCode() <= high && !uniqueIps.contains(log.getIpAddress()))
                uniqueIps.add(log.getIpAddress());
         }
         return uniqueIps.size();
     }
     
     public HashMap<String,Integer> countVisitPerIP(){
         HashMap<String,Integer> visitPerIP = new HashMap<String, Integer>();
         for(LogEntry log : records){
             if(!visitPerIP.containsKey(log.getIpAddress()))
                visitPerIP.put(log.getIpAddress(),1);
             else
                visitPerIP.put(log.getIpAddress(),visitPerIP.get(log.getIpAddress())+1);
         }
         return visitPerIP;
     }
     
     public int mostNumberVisitsByIP(HashMap<String,Integer> visitPerIP){
         int max = 0;
         for(String key : visitPerIP.keySet()){
             if(visitPerIP.get(key) > max)
                max = visitPerIP.get(key);
         }
         return max;
     }
     
     public ArrayList<String> iPsMostVisits(HashMap<String,Integer> visitPerIP){
         ArrayList<String> mostVisit = new ArrayList<String>();
         int most = 0;
         for(Map.Entry<String,Integer> entry: visitPerIP.entrySet()){
             int current = entry.getValue();
             if(current > most){
                most = current;
                mostVisit.clear();
                mostVisit.add(entry.getKey());
            }else if(current == most){
                mostVisit.add(entry.getKey());
            }else{}
         }
         return mostVisit;
     }
     
     public HashMap<String, ArrayList<String>> iPsForDays(){
          HashMap<String,ArrayList<String>> ipMaps = new HashMap<String, ArrayList<String>>();
         for(LogEntry log : records){
             String[] timeArray = log.getAccessTime().toString().split(" ");
             String dateKey = timeArray[1]+" "+timeArray[2];
             if(!ipMaps.containsKey(dateKey)){
                 ArrayList<String> ips = new ArrayList<String>();
                 ips.add(log.getIpAddress());
                 ipMaps.put(dateKey,ips);
             }else{
                 ipMaps.get(dateKey).add(log.getIpAddress());
             }
         }
         return ipMaps;
     }
     
    public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> ipMaps){
        int most = 0;
        String date = null; 
        for(String key:ipMaps.keySet()){
             if(ipMaps.get(key).size() > most){
                most = ipMaps.get(key).size(); 
                date = key;
            }
        }
        return date;
    }
    
    public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> records, String date){
        HashMap<String,Integer> visitPerIP = new HashMap<String, Integer>();
        for(String ip : records.get(date)){
            if(!visitPerIP.containsKey(ip))
                visitPerIP.put(ip,1);
            else
                visitPerIP.put(ip,visitPerIP.get(ip)+1);       
        }        
        return iPsMostVisits(visitPerIP);
    }
   
}
