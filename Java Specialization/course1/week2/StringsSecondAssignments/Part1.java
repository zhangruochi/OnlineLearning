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
    
    public void printAllString(String dna){
        int startIndex = 0;
        while(true){
            String currGene = findGene(dna,startIndex);
            if(currGene.isEmpty())
                break;
            System.out.println(currGene);
            startIndex = dna.indexOf(currGene,startIndex) + currGene.length();
        }
    }

}