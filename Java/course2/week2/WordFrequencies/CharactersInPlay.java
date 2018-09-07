
/**
 * Write a description of CharactersInPlay here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
public class CharactersInPlay {

    private ArrayList<String> nameOfCharacters;
    private ArrayList<Integer> freqOfCharacters;
    
    public CharactersInPlay(){
        nameOfCharacters = new ArrayList<String>();
        freqOfCharacters = new ArrayList<Integer>();
    }

    public void update(String person){
        int index = nameOfCharacters.indexOf(person);
        if(index == -1){
            nameOfCharacters.add(person);
            freqOfCharacters.add(1);
        }else{
            freqOfCharacters.set(index,freqOfCharacters.get(index)+1);
        }
    }

    public void findAllCharacters(){
        FileResource fr = new FileResource();
        for(String line : fr.lines()){
            int index = line.indexOf('.');
            if(index != -1){
                String name = line.substring(0,index+1);
                update(name);
            }
        }
    }

    public int getNum(){
        int max = 0;
        int min = 1;
        for(int num : freqOfCharacters)
            if(num > max)
                max = num;

        return max/100;    
    }

    public void tester(){
        findAllCharacters();
        int minPrintedFreq = getNum();
        int index = 0;
        for(int freq : freqOfCharacters){
            if(freq > minPrintedFreq){
                System.out.println(nameOfCharacters.get(index) + ": " + freqOfCharacters.get(index));
            }
            index ++;
        }

    }

    public void charactersWithNumParts(int num1, int num2){
        findAllCharacters();  
        int index = 0;
        for(int freq : freqOfCharacters){
            if(freq >= num1 && freq <= num2){
                System.out.println(nameOfCharacters.get(index) + ": " + freq);
            }
            index ++;
        }  
    }
}
