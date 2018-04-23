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



public class Part3{

    public Boolean twoOccurrences(String a, String b){

        int count = 0;
        int start = a.indexOf(b);

        if(start == -1)
            return false;

        else{
            start = a.indexOf(b,start+b.length());
            if(start == -1)
                return false;
        }

        return true;


    }

    public void testing(){
        String a = "A story by Abby Long";
        String b = "by";
        if(twoOccurrences(a,b))
            System.out.println("true");

        a = "A story by Ab Long";
        b = "by";
        if(twoOccurrences(a,b))
            System.out.println("true");

    }
    
    public String lastPart(String a, String b){
        int start = a. indexOf(b);
        if(start == -1)
            return b;
        else
            return a.substring(start+b.length(),a.length());

    }

    public void testinglastPart(){
        String a = "A story by Abby Long";
        String b = "byc";
        
        System.out.println(lastPart(a,b));
    }
}


public class Part4{
    URLResource url = new URLResource("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
    

}




