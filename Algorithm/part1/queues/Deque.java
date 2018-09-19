import java.util.Iterator;
import java.util.NoSuchElementException;



public class Deque<Item> implements Iterable<Item> {

    private int size;    
    private Node first, last;
    
    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    public Deque(){
        size = 0;
        first = last = null;

    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {

        if (item == null) throw new IllegalArgumentException("item is null");

        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;

        if (oldfirst != null)
            oldfirst.prev = first;
        else
            last = first;

        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("item is null");

        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.prev = oldLast;

        if (oldLast != null)
            oldLast.next = last;
        else
            first = last;
    
    
        size++;
    }

    public Item removeFirst() {

        if (size == 0) throw new NoSuchElementException("empty Deque");

        Item item = first.item;
        first = first.next;
        size--;
        if(size == 0)
            last = null;
        return item;
    }

    public Item removeLast() {
        if (size == 0) throw new NoSuchElementException("empty Deque");

        Item item = last.item;
        last = last.prev;
        size--;
        if(size == 0)
            first = null;
        return item;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {  return current != null; }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("empty Deque");
            
            Item item = current.item;
            current = current.next;
            return item;       
        }
        public void remove() {
            throw new UnsupportedOperationException("not support operation");
        }
    }


    public Iterator<Item> iterator(){
        return new DequeIterator();
    }
    

    public static void main(String[] args){

        Deque<Integer> deque = new Deque<Integer>();
        deque.isEmpty();
        deque.addLast(1);
        deque.removeFirst();
        deque.isEmpty();
        deque.addLast(4);
        deque.removeFirst();

        for (int i : deque)
            System.out.println(i);
    }


}