import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Node first, last;

    private class Node {      
        Item item;
        Node next;
    }

    public RandomizedQueue() {
        first = last = null;
        size = 0;
    }
    public boolean isEmpty() {  
        return size == 0;  

   }
    
    public int size() {
        return size;
    }
    
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("item is null");

        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty())
            first = last;
        else
            oldlast.next = last;
        size++;
    }

    public Item dequeue() {
        if(size == 0) throw new NoSuchElementException("empty Deque");
        
        Item item = first.item;
        first = first.next;
        size--;

        if (isEmpty())
            last = first;
            
        return item;    
    }

    public Item sample() {
        if (size == 0) throw new NoSuchElementException("empty Deque");

        int num = StdRandom.uniform(this.size);
        Node current = first;
        for (int i = 0; i < num; i++) {
            current = current.next;
        }
        return current.item;
    }

    
    private class QueueIterator implements Iterator<Item> {
        private final Item[] randomItems;
        private int current = RandomizedQueue.this.size;

        public QueueIterator() {
            randomItems = (Item[]) new Object[RandomizedQueue.this.size];
            int n = RandomizedQueue.this.size;
            for (int i = 0; i < n; i++){
                randomItems[i] = first.item;
                first = first.next;
            }
            StdRandom.shuffle(randomItems);
        }


        public boolean hasNext() { return current != 0; }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("empty Deque");
            
            Item item = randomItems[current-1];
            current--;
            return item;       
        }
        
        public void remove() {
            throw new UnsupportedOperationException("not support operation");
        }
        
    }


    public Iterator<Item> iterator() {
        return new QueueIterator();
    }


    public static void main(String[] args) {

        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);


        for(int i: queue){
            System.out.println(i);
        }

    }
}

