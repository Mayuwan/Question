package com.company;

public class Main {
    public static int binerySearch(int[] arr,int target){
        int l=0,r=arr.length-1;
        while (l<=r){
            int mid = (l+r)/2;
            if(arr[mid] >  target) r = mid-1;
            else if(arr[mid] <  target)  l = mid+1;
            else return mid;
        }
        return  -1;
    }
    public static void main(String[] args) {
	// write your code here
        bineryTree tree = new bineryTree();
        Integer[] arr = test.generateRandom(15,0,100);
        for(Integer num:arr){
            System.out.printf("%s ",num.toString());
            tree.insert(num,1);
        }
        System.out.println();
        tree.removeMin();
        tree.inOrder();
    }
}
