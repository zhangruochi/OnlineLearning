import java.io.*;

public class Sort{

    private static boolean less(Comparable a, Comparable b){
        return a.compareTo(b) < 0;

    }

    private static void exch(Comparable[] a, int i, int j){
        Comparable swap =  a[i];
        a[i] = a[j];
        a[j] = swap;

    }

    public static void selectionSort(Comparable[] array){
        for(int i=0;i<array.length;i++){
            int min = i;
            for(int j=i+1;j<array.length;j++){
                if(less(array[j],array[min]))
                    min = j;
            }
            exch(array,i,min);
        }
    }

    public static void insertSort(Comparable[] array){
        for(int i=0;i<array.length;i++){
            for(int j=i; j>0;j--){
                if(less(array[j],array[j-1]))
                    exch(array,j,j-1);
                else
                    break;
            }
        }
    }


    public static void main(String[] args) {
        Integer[] array = new Integer[]{5,4,3,2,1};
        Sort.selectionSort(array);
        for(Integer i : array)
            System.out.println(i);
        System.out.println("");

        Integer[] array2 = new Integer[]{5,4,3,2,1};
        Sort.insertSort(array2);
        for(Integer i : array2)
            System.out.println(i);
    }



}