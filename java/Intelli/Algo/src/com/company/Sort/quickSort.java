package com.company.Sort;

public class quickSort {
    public static void quickSort(int[] arr){
        quickSort(arr,0,arr.length-1);
    }
    private static void quickSort(int[] arr,int l, int r){
        if(l>=r) return;
        int mid = partation2(arr,l,r);
        quickSort(arr,l,mid-1);
        quickSort(arr,mid+1,r);
    }

    private static int partation(int[] arr,int l, int r){
        swap(arr,l,l+(int)(Math.random()*(r-l+1)));
        int V = arr[l];
        //arr[l+1...j]<V;arr[j+1...i-1]>V
        int j=l, i=l+1;
        for(;i<=r;i++){
            if(arr[i]<V)
                swap(arr,++j,i);
        }
        swap(arr,l,j);
        return j;
    }
    //从左右向中间推
    private static int partation2(int[] arr,int l, int r){
        swap(arr,l,l+(int)(Math.random()*(r-l+1)));
        int V = arr[l];
        //arr[l+1...i)<V;arr[j...r]>V
        int j=r, i=l+1;
        while(true){
            while(i<=r && arr[i] <= V) i++;
            while(j>=l+1 && arr[j] >= V) j--;
            if(i > j) break;
            swap(arr,i,j);
            i++;j--;
        }
        swap(arr,l,j);
        return j;
    }
    private static void insertSort(int[] arr, int l,int r){
        for(int i=l+1;i<=r;i++){
            int temp = arr[i];
            int j;
            for(j=i;j>l && temp<arr[j-1];j--){
                arr[j] = arr[j-1];
            }
            arr[j] = temp;
        }
    }
    public static void partation3Ways(int[] arr){
        partation3Ways(arr,0,arr.length-1);
    }
    private static void partation3Ways(int[] arr,int l, int r){
        if(r-l<=15){
            insertSort(arr,l,r);
            return;
        }
        swap(arr,l,l+(int)(Math.random()*(r-l+1)));
        int V =arr[l];
        //arr[l+1...lt]<V; arr[lt+1...i)==V; arr[gt...r]>V
        int lt= l, gt = r+1,i=l+1;
        while(i < gt){//注意是小于没有等于
            if(arr[i] == V) i++;
            else if(arr[i]<V){
                swap(arr,lt+1,i);
                lt++;
                i++;
            }
            else{
                swap(arr,gt-1,i);
                gt--;
            }
        }
        swap(arr,lt,l);
        partation3Ways(arr,l,lt);
        partation3Ways(arr,gt,r);
    }
    public static void swap(int[] arr, int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
