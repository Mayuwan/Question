package com.company.MinimumSpanTree;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class MinHeap<T extends Comparable>{
    private ArrayList<T> arr;

    public MinHeap(){
        arr = new ArrayList();
        arr.add(null);
    }

    public void insert(T v){

        arr.add(v);
        shiftUp(arr.indexOf(v));
    }
    public boolean isEmpty(){
        if(arr.size() == 1){
            return true;
        }
        return false;
    }
    /**从堆顶开始删除*/
    public T deleteMin(){

        swap(1,arr.size()-1);
        T minimum = arr.remove(arr.size()-1);
        shiftDown(1);
        return minimum;
    }

    public void  shiftUp(int v){
        int i=v;
        while(i/2>0){
            int parent_index = i/2;

            if(arr.get(i).compareTo(arr.get(parent_index)) < 0){
                swap(parent_index,i);
            }
            i=i/2;
        }
    }

    public void swap( int a, int b){
        T temp = arr.get(a);
        arr.set(a,arr.get(b));
        arr.set(b,temp);
    }

    public void shiftDown(int v){
        while(v*2<arr.size()){
            int left_index = v*2,right_index = v*2+1;
            if(right_index < arr.size() && arr.get(left_index).compareTo(arr.get(right_index))>0) {
                left_index = left_index+1;
            }
            if(arr.get(v).compareTo(arr.get(left_index)) < 0){
                break;
            }
            swap(v,left_index);
            v = left_index;
        }
    }

    public void show(){
        for(int i=1;i<arr.size();i++){
            System.out.printf("%s ",arr.get(i));
        }
    }

    public void heapSort(){
        while(arr.size()-1>0){
            System.out.printf("%s ",deleteMin());
        }
    }
}
