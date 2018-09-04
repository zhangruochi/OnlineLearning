import edu.duke.*;

public class CaesarCipher {
    public String encrypt(String input, int key) {
        //Make a StringBuilder with message (encrypted)
        StringBuilder encrypted = new StringBuilder(input);
        //Write down the alphabet
        String alphabetUpper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String alphabetLower = "abcdefghijklmnopqrstuvwxyz";
        //Compute the shifted alphabet
        String shiftedAlphabetUpper  = alphabetUpper.substring(key)+
        alphabetUpper.substring(0,key);
        String shiftedAlphabetLower = alphabetLower.substring(key) + 
        alphabetLower.substring(0,key);
        int idx = -1;
        //Count from 0 to < length of encrypted, (call it i)
        for(int i = 0; i < encrypted.length(); i++) {
            //Look at the ith character of encrypted (call it currChar)
            char currChar = encrypted.charAt(i);
            //Find the index of currChar in the alphabet (call it idx)
            if(Character.isUpperCase(currChar)){
                idx = alphabetUpper.indexOf(currChar);
                if(idx != -1){
                    char newChar = shiftedAlphabetUpper.charAt(idx);
                    encrypted.setCharAt(i, newChar);
                }
            }
            else{
                idx = alphabetLower.indexOf(currChar);
                if(idx != -1){
                    char newChar = shiftedAlphabetLower.charAt(idx);
                    encrypted.setCharAt(i, newChar);
                    encrypted.setCharAt(i, newChar);
                }
            }
        }
        System.out.println(encrypted);
        //Your answer is the String inside of encrypted
        return encrypted.toString();
    }
    
    public String encryptTwoKey(String phase, int key1, int key2){
        StringBuilder result = new StringBuilder();    
        //StringBuilder phase = new StringBuilder(phase);
        for(int i= 0; i<phase.length();i+=2){
            result.append(phase.charAt(i));
        }
        String encryptPartOne = encrypt(result.toString(),key1);
        result.delete(0,result.length());
        System.out.println(result);
        
        for(int i= 1; i<phase.length();i+=2){
            result.append(phase.charAt(i));
        }
        String encryptPartTwo = encrypt(result.toString(),key2);
        result.delete(0,result.length());
        
        int index1 = 0,index2 = 0;
        for(int i = 0;i<phase.length();i++){
            if(i%2 == 0){
                result.append(encryptPartOne.charAt(index1));
                index1++;
            }else{
                result.append(encryptPartTwo.charAt(index2));
                index2++;
            }
        }
        System.out.println(result);
        return result.toString();
        
        
    } 
    
    public boolean isVowel(char ch){
        char lowerCase = Character.toLowerCase(ch);
        String vowel = "aeiou";
        if(vowel.indexOf(ch) >= 0)
            return true;
        else
            return false;
        
    } 
    
    public String repeplaceVowels(String phase, char ch){
        StringBuilder init = new StringBuilder(phase);
   
        for(int i=0; i<init.length(); i++){
           if(isVowel(init.charAt(i)))
                init.setCharAt(i,ch);
        }
        return init.toString();
    }
    
    public String emphasize(String phase, char ch){
        StringBuilder init = new StringBuilder(phase);
        for(int i=0; i<init.length(); i++){
           if(init.charAt(i) == ch){
               if((i+1)%2 == 0)
                    init.setCharAt(i,'+');
               else
                    init.setCharAt(i,'*');
            }   
        }
        return init.toString();
    }
    
    public void testCaesar() {
        int key = 17;
        FileResource fr = new FileResource();
        String message = fr.asString();
        String encrypted = encrypt(message, key);
        System.out.println(encrypted);
        String decrypted = encrypt(encrypted, 26-key);
        System.out.println(decrypted);
    }
}

