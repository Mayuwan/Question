package com.think.Thread;

public class prac2 implements Runnable{
    private int n;
    private int count =0;
    public prac2(int n) {this.n = n;}
    //public int next() {return fib(count++);}
    public int fib(int m) {
        if(m<2) return 1;
        return fib(m-2) + fib(m-1);
    }
    public void run() {
        for(int i=0;i<n;i++) {
            System.out.print(fib(i) + " ");
        }
        System.out.println("end!!");
    }
    public static void main(String[] args) {
        Thread t= new Thread(new prac2(10));
        t.start();
        System.out.println("fibonacci----------");
        int i=0;
    }
}

