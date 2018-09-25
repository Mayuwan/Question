package com.company.Sort;

public class N2Sort {
    public static int[] BubbleSort(int[] arr) {
        int len = arr.length;
        for(int i=0;i<len-1;i++) {
            for(int j=0;j<len-1-i;j++){
                if(arr[j] > arr[j+1]) {
                    int temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }
    //O(n^2)
    public static <T extends Comparable> void selectionSort(T[] arr) {
        int n = arr.length;
        int min ;
        /*[i,j]中选择最小值，放在数组首位，两者交换*/
        /*寻找[i,n)最小值*/
        for(int i=0;i<n;i++) {
            min = i;
            for(int j=i+1;j<n;j++){
                if(arr[min].compareTo(arr[j])>0) min = j;
            }
            swap(arr,i,min);
        }
    }

    //插入排序比选择排序慢
    //优化后的插入排序不交换位置，并且可以提前结束循环，对于大量重复元素或者近乎有序的数组 运行速度高
    public static <T extends Comparable> void insertSort(T[] arr) {
        for(int i=1;i<arr.length;i++) {//[i,n-1]中的元素插入前面[0,i-1]
            T temp = arr[i];
            int j;//j表示元素应该插入的位置
            for(j=i;j>0 &&arr[j-1].compareTo(temp)>0;j--) {
                arr[j] = arr[j-1];
            }
            arr[j] = temp;
        }
    }

    public static void insertSort(int[] arr){
        for(int i=1; i<arr.length; i++){
            int e =arr[i];
            for(int j =i; j>0; j--){
                if(arr[j] < arr[j-1]) swap(arr,j,j-1);
            }
        }
    }
    public static void swap(int[] arr, int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    public static <T extends Comparable> void swap(T[] arr, int i,int j){
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
