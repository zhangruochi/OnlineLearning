
/**
 * Write a description of Part3 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
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