package com.company;

public class test {
    public  static Integer[] generateRandom(int n, int start, int end){
        Integer[] arr = new Integer[n];
        for(int i=0;i<n;i++){
            arr[i] = (int)(start+Math.random()*(end-start+1));
        }
        return arr;
    }
    public  static Integer[] generateAlmostSorted(int n, int count){
        Integer[] arr = new Integer[n];
        for(int i=0;i<n;i++)
            arr[i] = i;

        for(int i=0;i<count; i++){
            int j = (int)Math.random()*n;
            swap(arr,i,j);
        };
        return arr;
    }
    public static <T> void swap(T[] arr,int i,int j){
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
