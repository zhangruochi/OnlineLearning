
/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2 {
    public String findSimpleGene(String dna, String startCodon, String endCodon){
        int start = -1;
        int end = -1;
        
        if(Character.isUpperCase(startCodon.charAt(0))){

            start = dna.indexOf(startCodon.toUpperCase());
            end = dna.indexOf(endCodon.toUpperCase(),start);
        }

        else{

            start = dna.indexOf(startCodon.toLowerCase());
            end = dna.indexOf(endCodon.toLowerCase(),start);
        }

        if(start == -1 || end == -1 || (start - end)%3 != 0)
            return "";

        return dna.substring(start,end+3);
    }
    
    public void testSimpleGene() {
        String a = "ATGCCCTAA";
        String result = findSimpleGene(a,"ATG","TAA");
        System.out.println(result);
        
        a = "atgccctaa";
        result = findSimpleGene(a,"atg","taa");
        System.out.println(result);
        
        a = "CTGCCCTAA";
        result = findSimpleGene(a,"ATG","TAA");
        System.out.println(result);
        
        a = "ATGCCCCTAA";
        result = findSimpleGene(a,"ATG","TAA");
        System.out.println(result);
        
        a = "CCCC";
        result = findSimpleGene(a,"ATG","TAA");
        System.out.println(result);
        
    }
    
}
