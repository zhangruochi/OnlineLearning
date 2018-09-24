import edu.princeton.cs.algs4.StdIn;
import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int k = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
              String str = StdIn.readString();
              queue.enqueue(str);      
        }
        Iterator<String> iterator = queue.iterator();
        
        for (int i = 0; i < k; i++) {
            if (iterator.hasNext())
                System.out.println(iterator.next());
        }
    }
}
