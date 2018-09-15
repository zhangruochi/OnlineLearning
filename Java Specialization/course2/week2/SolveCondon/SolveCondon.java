
/**
 * Write a description of SolveCondon here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;

public class SolveCondon{
    private HashMap<String,Integer> condonMap;

    public SolveCondon(){
        condonMap = new HashMap<String,Integer>();
    }

    public void buildCodonMap(int start,String dna){
        for(int i=start;i<dna.length()-3;i+=3){

            String currentCondon = dna.substring(i,i+3);
            if(condonMap.keySet().contains(currentCondon)){
                condonMap.put(currentCondon,condonMap.get(currentCondon)+1);
            }else{
                condonMap.put(currentCondon,1);
            }
        }
    }

    public String getMostCommonCodon(){
        int maxFreq = 0;
        String maxFreqCondon = null;
        for(String condon: condonMap.keySet()){
            if(condonMap.get(condon) > maxFreq){
                maxFreq = condonMap.get(condon);
                maxFreqCondon = condon;
            }
        }
        return maxFreqCondon;
    }

    public void printCodonCounts(int low, int high){
        for(String condon: condonMap.keySet()){
            if(condonMap.get(condon) >= low && condonMap.get(condon) <= high  ){
                System.out.println(condon + " " + condonMap.get(condon));
            }
        }
    }

    public void tester(){
        FileResource fr = new FileResource();
        String dna = fr.asString().trim().toUpperCase();
        
        for(int i=0;i<3;i++){
            buildCodonMap(i,dna);
            System.out.println("Reading frame starting with " + i + " results in " + condonMap.size() + " unique codons");
            System.out.println("and most common codon is " + getMostCommonCodon()  +  " with count " + condonMap.get(getMostCommonCodon()));
            int low = 7, high = 7;
            System.out.println("Counts of codons between "+ low + " and " + high + " inclusive are: ");
            printCodonCounts(low,high);
            System.out.println();
            if(!condonMap.isEmpty())
                condonMap.clear();
        }


    }

}
