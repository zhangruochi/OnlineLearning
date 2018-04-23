
/**
 * Write a description of Part2 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Part2{

    public int howMany(String a, String b){
        int count = 0;
        int startIndex = 0;
        while(true){
            if(b.indexOf(a,startIndex) == -1)
                break;
            count ++;
            startIndex = b.indexOf(a,startIndex) + a.length();
        }
        return count;    
    }

    public void testHowMany(){
        System.out.println(howMany("GAA", "ATGAACGAATTGAATC"));
    }

}
