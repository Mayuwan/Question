package com.company.Sort;

public class mergeSort {
    public static void mergeSort(int[] arr){
        mergeSort(arr,0,arr.length-1);
    }
    private static void mergeSort(int[] arr, int l, int r){
        //if(l>=r) return;
        if(r-l <= 15) {
            insertSort(arr,l,r);
            return;
        }
        int mid = l+(r-l)/2;
        mergeSort(arr,l,mid);
        mergeSort(arr,mid+1,r);
        if(arr[mid] > arr[mid+1])//对于近乎有序数组的优化
            merge(arr,l,mid,r);
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
    private static void merge(int[] arr, int l, int mid, int r){
        int[] copy = new int[r-l+1];
        //for(int i=l;i<=r;i++) copy[i-l] = arr[l];
        //左数组[l,mid],右数组[mid+1,r]
        int i = l, j = mid+1;
        int k;
        for(k=0;k<copy.length;k++){
            if(i>mid || j >r) break;
            if(arr[i] <= arr[j]) {//有等号，这样才是稳定的
                copy[k] = arr[i];
                i++;
            }
            else{
                copy[k] = arr[j];
                j++;
            }
        }
        while(i<=mid){copy[k] = arr[i];k++;i++;}
        while(j<=r){copy[k] = arr[j];j++;k++;}
        for(int r1=0;r1<copy.length;r1++) arr[r1+l] = copy[r1];
    }

    public static void mergeSortButtomToUp(int[] arr){
        int n=arr.length;
        for(int sz=1;sz<=n;sz+=sz){
            for(int j=0; j+sz<n; j+=sz+sz){
                //对arr[l...l+sz-1]和arr[l+sz...l+sz+sz-1]进行归并  \
                if(arr[j+sz-1] > arr[j+sz]){
                    if(sz+sz<=15) insertSort(arr,j,Math.min(j+sz+sz-1,n-1));
                    else merge(arr,j,j+sz-1,Math.min(j+sz+sz-1,n-1));
                }
            }
        }
    }
}
