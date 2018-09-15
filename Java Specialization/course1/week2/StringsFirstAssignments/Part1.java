
/**
 * Write a description of Part1 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part1 {
    public String findSimpleGene(String dna){
        int start = dna.indexOf("ATG");
        int end = dna.indexOf("TAA",start);

        if(start == -1 || end == -1 || (start - end)%3 != 0)
            return "";

        return dna.substring(start,end+3);
    }
    
    public void testSimpleGene() {
        String a = "ATGCCCTAA";
        String result = findSimpleGene(a);
        System.out.println(result);
        
        a = "ATGCCCTAC";
        result = findSimpleGene(a);
        System.out.println(result);
        
        a = "CTGCCCTAA";
        result = findSimpleGene(a);
        System.out.println(result);
        
        a = "ATGCCCCTAA";
        result = findSimpleGene(a);
        System.out.println(result);
        
        a = "CCCC";
        result = findSimpleGene(a);
        System.out.println(result);
        
    }
    
}