
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import edu.duke.*;


public class Part1{

    public int findStopCodon(String dna, int startIndex, String stopCodon){

        int currIndex = dna.indexOf(stopCodon,startIndex+3);
        while(currIndex != -1){
            int diff = currIndex - startIndex;
            if(diff % 3 == 0)
                return currIndex;
            else
                currIndex = dna.indexOf(stopCodon,currIndex+1);
        }

        return dna.length();

    }

    public void testFindStopCodon(){
        String dna = "xxxyyyzzzTAAxxxyyyzzzTAAxx";
        int dex = findStopCodon(dna,0,"TAA");
        if(dex == 9)
            System.out.println("TRUE");
        dex = findStopCodon(dna,9,"TAA");
        if(dex == 21)
            System.out.println("TRUE");

    }

    public String findGene(String dna, int where){
        int startIndex = dna.indexOf("ATG",where);
        if(startIndex == -1){
            return "";
        }

        int taaIndex = findStopCodon(dna,startIndex,"TAA");
        int tagIndex = findStopCodon(dna,startIndex,"TAG");
        int tgaIndex = findStopCodon(dna,startIndex,"TGA");

        int temp = Math.min(taaIndex,tagIndex);
        int minIndex = Math.min(temp, tgaIndex);

        if(minIndex == dna.length())
            return "";

        return dna.substring(startIndex,minIndex+3);

    }

    public void testFindGene(){
        String dna = "XXXYATGYYYZZZTAAXXXATGYYYZZZXXXTAAXX";
        String gene = findGene(dna,0);
        System.out.println(gene);
    }
    
    public int countGenes(String dna){
        int startIndex = 0;
        int count = 0;
        String longString = "";
        while(true){
            String currGene = findGene(dna,startIndex);
            if(currGene.isEmpty())
                break;
            
            if(currGene.length() > longString.length())
                longString = currGene;
                
            if(cgRatio(currGene) > 0.35)
                count++;
                
                
            startIndex = dna.indexOf(currGene,startIndex) + currGene.length();
        }
        
        System.out.println(longString.length());
        return count;
    }
    
    public void getNumOfGene(){
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString().toUpperCase();
        
        countGenes(dna);
        //System.out.println(countCTG(dna));
    }
    
    public void testSomething(){
        FileResource fr = new FileResource("brca1line.fa");
        String dna = fr.asString().toUpperCase();
        String s = findGene(dna,0);
        System.out.println(s.length());
        System.out.println(cgRatio(dna));
        
    }
    
    public StorageResource getAllString(String dna){
        int startIndex = 0;
        StorageResource geneList = new StorageResource();
        while(true){
            String currGene = findGene(dna,startIndex);
            if(currGene.isEmpty())
                break;
            geneList.add(currGene);
            startIndex = dna.indexOf(currGene,startIndex) + currGene.length();
        }
        return geneList;
    }
    
  
    
    public void testStorageResource(){
        String dna = "XXXYATGYYYZZZTAAXXXATGYYYZZZXXXTAAXX";
        StorageResource geneList = getAllString(dna);
        for(String s: geneList.data())
            System.out.println(s);
    }
    
    public double cgRatio(String dna){
        double count = 0;
        for(int i=0; i<dna.length();i++){
            if(dna.charAt(i) == 'C' || dna.charAt(i) == 'G')
                count ++;
            }
    return count/dna.length();
    }

    public void testCgRatio(){
        String dna = "ATGCCATAG";
        System.out.println(cgRatio(dna));
    }
    
    public int countCTG(String dna){

        int count = 0;
        int startIndex = 0;
        int currIndex = dna.indexOf("CTG",startIndex);
        while(currIndex != -1){
            count++;
            startIndex = currIndex+1;
            currIndex = dna.indexOf("CTG",startIndex);
        }
    return count;
    }


public void testCountCTG(){
    String dna = "CTGAACTGAACTGAACTG";
    System.out.println(countCTG(dna));
}
    
public void processGenes(StorageResource sr){
    int longerNine = 0;
    int higer_zeros_dot_three_five = 0;

    String longString = "";

    for(String s: sr.data()){
        if(s.length() >= 9){
            System.out.println("Longer 9: " + s);
            longerNine++;
        }

        if(cgRatio(s) > 0.35){
            System.out.println("Higer than 0.35: " + s);
            higer_zeros_dot_three_five ++;
        }

        if(s.length() > longString.length())
            longString = s;
        

    }

    System.out.println(longerNine);
    System.out.println(higer_zeros_dot_three_five);
    System.out.println(longString.length());

}

    
    
    
    
}
