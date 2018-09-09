import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        //REPLACE WITH YOUR CODE
        StringBuilder slice = new StringBuilder();
        for(int i=whichSlice;i<message.length(); i+=totalSlices)
            slice.append(message.charAt(i));
        return slice.toString() ;
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cracker = new CaesarCracker(mostCommon);

        for(int i=0;i<klength;i++){
            String currentSlice = sliceString(encrypted,i,klength);
            key[i] = cracker.getKey(currentSlice);
        }
        return key;
    }

    public String breakVigenere(String message,int klength, char mostCommon) {
        
        int[] key = tryKeyLength(message,klength,mostCommon);    
        VigenereCipher cipher = new VigenereCipher(key);
        return cipher.decrypt(message);
        
    }

    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> dictionary = new HashSet<String>();

        for(String word : fr.lines()){
            dictionary.add(word.toLowerCase());
        }   
        return dictionary;
    }

    public int countWords(String message, HashSet<String> dictionary){
        int count = 0;
        for(String word : message.split("\\W")){
            if(dictionary.contains(word.toLowerCase())){
                count++;
            }
        }
        return count;
    } 

    public int breakForLanguage(String encrypted,HashSet<String> dictionary,char mostCommon){
        String returnString = null;
        int maxWords = 0;
        int returnKlength = 0;
       
        for(int klength = 1; klength<=100; klength++){
            String decryptMessage = breakVigenere(encrypted,klength,mostCommon);
            int wordsNum = countWords(decryptMessage,dictionary);
            if(wordsNum > maxWords){
                returnString = decryptMessage;
                maxWords = wordsNum;
                returnKlength = klength;
            }

        }
        //System.out.println(returnKlength);
        //System.out.println(maxWords);
        System.out.println(returnString.substring(0,100));
        return maxWords;
    }

    public char mostCommonCharIn(HashSet<String> dictionary){
        HashMap<Character,Integer> charCounts = new HashMap<Character,Integer>();
        for(String word : dictionary){
            for(char c : word.toCharArray()){
                if(!charCounts.keySet().contains(c))
                    charCounts.put(c,1);
                else    
                    charCounts.put(c,charCounts.get(c)+1);
            }
        }
        int max = 0;
        char mostCommon = 'a';
        for(char c : charCounts.keySet()){
            if(charCounts.get(c) > max){
                max = charCounts.get(c);
                mostCommon = c; 
            }
        }
        
        return mostCommon;
    }

    public void breakForAllLanguages(String message, HashMap<String,HashSet<String>> l​anguages){
        int max = 0;
        String resultLanguage = null;
        for(String l​anguage : l​anguages.keySet()){
            HashSet<String> dictionary = l​anguages.get(l​anguage);
            char mostCommon = mostCommonCharIn(dictionary);
            int maxWords = breakForLanguage(message,dictionary,mostCommon);
            if(maxWords > max){
                max = maxWords;
                resultLanguage = l​anguage;
            }   
        }
        System.out.println(resultLanguage);     
    }

    
    public void test(){
        HashMap<String,HashSet<String>> languages = new HashMap<String,HashSet<String>>();
         FileResource fr = new FileResource();
         String message = fr.asString();
         DirectoryResource dir = new DirectoryResource();
         for(File f : dir.selectedFiles()){
             FileResource frd = new FileResource(f);
             HashSet<String> dictionary = readDictionary(frd);
             languages.put(f.getName(),dictionary);
         }
 
         breakForAllLanguages(message,languages);

         
    }

  
}
