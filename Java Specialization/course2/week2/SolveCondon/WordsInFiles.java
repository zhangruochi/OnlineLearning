
/**
 * Write a description of WordsInFiles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.*;
import edu.duke.*;
import java.lang.*;
import java.io.*;

public class WordsInFiles{
    private HashMap<String,ArrayList<String>> wordsFileMap;
    
    public WordsInFiles(){
        wordsFileMap = new HashMap<String,ArrayList<String>>();
    }

    public void addWordsFromFile(File f){
        FileResource fr = new FileResource(f);
        for(String word : fr.words()){
            if(!wordsFileMap.keySet().contains(word)){
                ArrayList<String> fileList = new ArrayList<String>();
                fileList.add(f.getName());
                wordsFileMap.put(word,fileList);
            }else{
                if(!wordsFileMap.get(word).contains(f.getName()))
                    wordsFileMap.get(word).add(f.getName());
            }
        }
    }

    public void buildWordFileMap(){
        if(!wordsFileMap.isEmpty())
            wordsFileMap.clear();
        DirectoryResource dir = new DirectoryResource();
        for(File f : dir.selectedFiles()){
            addWordsFromFile(f);
        }
    }

    public int m​axNumber(){
        if(wordsFileMap.isEmpty())
            return 0;
        else{
            int max = 0;
            for(String word : wordsFileMap.keySet()){
                if(wordsFileMap.get(word).size() > max)
                    max = wordsFileMap.get(word).size();
            }
            return max;
        }
    }

    public ArrayList<String> wordsInNumFiles(int num){
        if(wordsFileMap.isEmpty())
            return null;
        ArrayList<String> nameList = new ArrayList<String>();
        for(String word : wordsFileMap.keySet()){
            if(wordsFileMap.get(word).size() == num){
                nameList.add(word);
            }
        }
        return nameList;
    }

    public void printFilesIn(String word){
        if(wordsFileMap.isEmpty())
            return ;
        for(String file : wordsFileMap.get(word)){
            System.out.p​rintln(file);
        }
    }

    public void tester(){
        buildWordFileMap();
        for(String file : wordsFileMap.get("tree")){
            System.out.println(file);
        }
        //System.out.println(wordsFileMap.size());
        //int max = maxNumber();
        //System.out.println(max);
        //ArrayList<String> wordList = wordsInNumFiles(4);
        //System.out.println(wordList.size());
        //for(String word : wordList ){
        //    System.out.println(word + ": ");
        //    printFilesIn(word) ;
        //}

    }
}
