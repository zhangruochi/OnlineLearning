
/**
 * Write a description of WordLengths here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import java.util.*;

public class WordLengths {
    
    public void countWordLengths(FileResource fr, int[] counts, ArrayList<ArrayList<String>> packages){
       
        for(String word : fr.words()){
            int currentLength = 0;
            StringBuilder currentWord = new StringBuilder();
            for(char c: word.toCharArray()){
                if(Character.isLetter(c)){
                    currentLength++;
                    currentWord.append(c);
                }
            }
            counts[currentLength]++;
            packages.get(currentLength).add(currentWord.toString());
            
        }     
    }

    public void testCountWordLengths(){
        int[] counts = new int[31];
        ArrayList<ArrayList<String>> packages = new ArrayList<ArrayList<String>>();
        for(int i=0;i<31;i++){
            packages.add(new ArrayList<String>());
        }
        FileResource fr = new FileResource();
        countWordLengths(fr,counts,packages);
        for(int i=0; i< counts.length; i+=1){
            if(counts[i] > 0){
                System.out.print(counts[i] + " words of length " + i + ": ");
                for(int k=0; k<counts[i]; k+=1)
                    System.out.print(packages.get(i).get(k) + " ");
                System.out.println("");
            }
            
        }
        System.out.println(indexOfMax(counts));

    }

    public int indexOfMax​(int[] values){
        int maxIndex = 0;
        for(int i = 1;i<values.length;i=i+1){
            if(values[maxIndex] <= values[i])
                maxIndex = i;
        }
        return maxIndex;
    }
}
