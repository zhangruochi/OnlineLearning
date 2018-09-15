
/**
 * Find out how many times each word occurs, and
 * in particular the most frequently occurring word.
 * 
 * @author Duke Software Team
 * @version 1.0
 */
import edu.duke.*;
import java.util.ArrayList;

public class WordFrequencies
{
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    
    public WordFrequencies() {
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }
    
    public void findUnique(){
        FileResource resource = new FileResource();
        
        for(String s : resource.words()){
            s = s.toLowerCase();
            int index = myWords.indexOf(s);
            if (index == -1){
                myWords.add(s);
                myFreqs.add(1);
            }
            else {
                int freq = myFreqs.get(index);
                myFreqs.set(index,freq+1);
            }
        }
    }
    
    public int findIndexOfMax(){
        int maxIndex = 0;
        int maxFreq = 0;
        int count = 0;
        for(int freq : myFreqs){
            if(freq > maxFreq){
                maxFreq = freq;
                maxIndex = count;
            }
            count++;
        }
        return maxIndex;
    }
    
    public void tester(){
        findUnique();
        System.out.println("# unique words: "+myWords.size());
        int index = findMax();
        System.out.println("max word/freq: "+myWords.get(index)+" "+myFreqs.get(index));
        int maxIndex = findIndexOfMax();
        System.out.println("max count is: " + myFreqs.get(maxIndex) + " and the word is: " + myWords.get(maxIndex));
    }
    
    public int findMax(){
        int max = myFreqs.get(0);
        int maxIndex = 0;
        for(int k=0; k < myFreqs.size(); k++){
            if (myFreqs.get(k) > max){
                max = myFreqs.get(k);
                maxIndex = k;
            }
        }
        return maxIndex;
    }
}
