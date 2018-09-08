
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import java.io.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
        //System.out.println("");
        //String[] timeArray = le2.getAccessTime().toString().split(" ");
        //System.out.println(timeArray[1]+" "+timeArray[2]);
    }
    
    public void testLogAnalyzer() {
       LogAnalyzer analyzer = new LogAnalyzer();
       analyzer.readFile("weblog2_log");
       analyzer.printAll();
    }
    
    public void testUniqueIP(){
       LogAnalyzer analyzer = new LogAnalyzer();
       analyzer.readFile("weblog2_log");
       System.out.println(analyzer.countUniqueIPs());
    }
    
    public void testPrintAllHigherThanNum(int num){
       LogAnalyzer analyzer = new LogAnalyzer();
       analyzer.readFile("weblog2_log");
       analyzer.printAllHigherThanNum(num);
    }
    
    public void testUniqueIPVisitsOnDay(String someday){
       LogAnalyzer analyzer = new LogAnalyzer();
       analyzer.readFile("weblog2_log");
       ArrayList<String> uniqueIps = analyzer.uniqueIPVisitsOnDay(someday);
       for(String ip : uniqueIps){
        System.out.println(ip);
       }
    }
    
    public void testCountUniqueIPsInRange(int low,int high){
       LogAnalyzer analyzer = new LogAnalyzer();
       analyzer.readFile("weblog2_log");
       int uniqueIpsCount = analyzer.countUniqueIPsInRange(low,high);
       System.out.println(uniqueIpsCount);
       
    }
    
    public void test(){
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("weblog2_log");
        HashMap<String,Integer> visitPerIP = analyzer.countVisitPerIP();
        System.out.println(analyzer.mostNumberVisitsByIP(visitPerIP));
        for(String s: analyzer.iPsMostVisits(visitPerIP)){
            System.out.println(s);
        }
        HashMap<String,ArrayList<String>> ipMaps = analyzer.iPsForDays();
        //for(Map.Entry<String,ArrayList<String>> entry: ipMaps.entrySet()){
        //    System.out.println(entry.getKey());
        //    System.out.println(entry.getValue().size());
        //}
        System.out.println(analyzer.dayWithMostIPVisits(ipMaps));
        System.out.println(analyzer.iPsWithMostVisitsOnDay(ipMaps,"Sep 30"));
    }
}

