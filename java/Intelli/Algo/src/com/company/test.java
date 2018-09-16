package com.company;

public class test {
    public  static Integer[] generateRandom(int n, int start, int end){
        Integer[] arr = new Integer[n];
        for(int i=0;i<n;i++){
            arr[i] = (int)(start+Math.random()*(end-start+1));
        }
        return arr;
    }
}
