
/**
 * Write a description of CaesarBreaker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import edu.duke.*;

public class CaesarBreaker {

    public String decrypt(String encrypted,int key){
        CaesarCipher cc = new CaesarCipher();
        String message = cc.encrypt(encrypted, 26-key);
        return message;

    }

    public void testDecryptTwoKey(){
        FileResource fr = new FileResource();
        String message = fr.asString();
        
        String decryptMessage = decryptTwoKeys(message);
        System.out.println(decryptMessage);
    }

    public String halfOfString(String message, int start){
        StringBuilder result = new StringBuilder();
        for(int i=start;i<message.length();i+=2){
            result.append(message.charAt(i));
        }
        return result.toString();
    }

    public int maxIndex(int[] array){
        int max = 0;
        int maxFreq = array[0];
        for(int i = 1; i<array.length; i++){
            if(maxFreq < array[i]){
                maxFreq = array[i];
                max = i;  
            }
        }
        return max;
    }

    public int[] countLetter(String s){
        int[] freq = new int[26];
        for(char c: s.toCharArray()){
            c = Character.toLowerCase(c);
            if(c>='a'&& c <='z')
                freq[c-'a']++; 
        }
        return freq;
    }

    public int getKey(String s){
        return maxIndex(countLetter(s));
    }
    
    public int getDeKey(int key){
        int deKey = key - 4;
        if(key < 4)
            deKey = 26 - (4 - key);
        return deKey;    
    }

    public String decryptTwoKeys(String s){
        String partOne = halfOfString(s,0);
        String partTwo = halfOfString(s,1);
        int maxIndex1 = getKey(partOne);  
        int maxIndex2 = getKey(partTwo);
        
        int deKey1 = getDeKey(maxIndex1);
        int deKey2 = getDeKey(maxIndex2);
        
        //int deKey1 = 14;
        //int deKey2 = 24;
        
        System.out.println(deKey1);
        System.out.println(deKey2);
        String partOneOlder = decrypt(partOne,deKey1);
        String partTwoOlder = decrypt(partTwo,deKey2);
        
        StringBuilder originalString = new StringBuilder();
        int index1 = 0,index2 = 0;
        for(int i=0;i<s.length();i++){
            if(i%2==0)
                originalString.append(partOneOlder.charAt(index1++));
            else
                originalString.append(partTwoOlder.charAt(index2++));
        }
        System.out.println(originalString);
        return originalString.toString();
    }
}
