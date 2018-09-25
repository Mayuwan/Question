package com.company;

public class MaxHeap {
    private int capacity;
    private int count;
    private int[] array;

    public MaxHeap(int capacity) {
        this.capacity = capacity;
        array = new int[capacity+1];
        count = 0;//array[0]无用
    }
    public MaxHeap(int[] arr, int capacity) {
        this.capacity = capacity;
        array = new int[capacity+1];
        for(int i=0; i<arr.length; i++) {
            array[i+1] = arr[i];
            count++;
        }
        for(int i=count/2; i>=1; i--)
            shiftDown(i);
    }
    public void insert(int key) {
        if(count<=capacity) {
            array[count+1] = key;
            count++;

            shiftUp(count);

        }
    }
    private void shiftUp(int i) {
        //父节点：array[i/2]
        while(i/2 > 0) {
            int parent  = i/2;
            if(array[parent] < array[i]) {
                swap(array,parent,i);
            }
            i=i/2;
        }
    }
    public int removeMax() {
        int max = array[1];
        swap(array,1,count);
        count--;
        shiftDown(1);
        return max;
    }
    private void shiftDown(int root) {
        //子节点：左---2i；右---2i+1
        //int maxIndex=0, left = 2*root;
        while(2*root <= count) {
            int left = 2*root,right  =2*root+1;
            if(right<= count && array[left] < array[right]) {
                left=left+1;
            }
            if(array[root]> array[left]) break;
            swap(array,root,left);
            root = left;
        }
    }
    public boolean isEmpty() {
        return count == 0 ?  true:false;
    }
    public int getSize() {
        return count;
    }
    public void getArray() {
        for(int i=1; i<=count; i++) {
            System.out.printf("%d ", array[i]);
        }
        System.out.println();
    }
    public void swap(int[] arr, int i, int j) {
        int temp;
        temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public static  int[] heapSort1(int[] arr) {
        MaxHeap  heap = new MaxHeap(100);
        for(int num:arr) heap.insert(num);

        for(int i=arr.length-1;i>=0;i--) {
            arr[i] = heap.removeMax();
        }
        return arr;
    }
    public static  int[] heapSort2(int[] arr) {
        MaxHeap heap = new MaxHeap(arr,100);
        for(int i=arr.length-1;i>=0;i--)
            arr[i] = heap.removeMax();
        return arr;
    }
}
