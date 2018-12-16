package com.company;

import com.company.unionFind.UnionFound1;
import com.company.unionFind.UnionFound2;
import com.company.unionFind.UnionFound3;
import com.company.unionFind.UnionFound4;

import java.util.Random;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
public class test {
    public  static int[] generateRandom(int n, int start, int end){
        int[] arr = new int[n];
        for(int i=0;i<n;i++){
            arr[i] = (int)(start+Math.random()*(end-start+1));
        }
        return arr;
    }
    public static <T extends Comparable> boolean isSorted(int[] a) {
        for(int i=0;i<a.length-1;i++) {
            if(a[i]>a[i+1] )
                return false;
        }
        return true;
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
    public  static void testUF1(int n ){
        Random random = new Random();
        UnionFound1 union = new UnionFound1(n);
        long start = System.currentTimeMillis();
        for (int i=0; i<n;i++){
            int a =  random.nextInt(n);
            int b  =  random.nextInt(n);
            union.union(a,b);
        }
        System.out.println("UnionFound1;"+(double)(System.currentTimeMillis()-start)/1000);

    }
    public  static void testUF2(int n ){
        Random random = new Random();
        UnionFound2 unio2 = new UnionFound2(n);
        long start = System.currentTimeMillis();
        for (int i=0; i<n;i++){
            int a =  random.nextInt(n);
            int b  =  random.nextInt(n);
            unio2.union(a,b);
        }
        System.out.println("UnionFound2:"+(double)(System.currentTimeMillis()-start)/1000);
    }
    public  static void testUF3(int n ){
        Random random = new Random();
        UnionFound3 union = new UnionFound3(n);
        long start = System.currentTimeMillis();
        for (int i=0; i<n;i++){
            int a =  random.nextInt(n);
            int b  =  random.nextInt(n);
            union.union(a,b);
        }
        System.out.println("UnionFound3:"+(double)(System.currentTimeMillis()-start)/1000);
    }
    public  static void testUF4(int n ){
        Random random = new Random();
        UnionFound4 union = new UnionFound4(n);
        long start = System.currentTimeMillis();
        for (int i=0; i<n;i++){
            int a =  random.nextInt(n);
            int b  =  random.nextInt(n);
            union.union(a,b);
        }
        System.out.println("UnionFound4:"+(double)(System.currentTimeMillis()-start)/1000);
    }


        public static void main(String[] args) {
            System.out.println("below is thread info:");
            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            long[] threadIds = threadMXBean.getAllThreadIds();
            ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadIds);
            for (ThreadInfo threadInfo : threadInfos) {
                System.out.println(threadInfo.getThreadId()+": "+threadInfo.getThreadName());
            }
        }

}
