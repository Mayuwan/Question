package com.think.Thread;
import java.util.concurrent.*;
public class prac6 implements Runnable{
    public void run() {
        Double mills =((1 + Math.random() * (10-1) ) *1000);
        long millsec = mills.longValue();
        try {
            //Thread.sleep(millsec);
            //java SE5/6 style
            TimeUnit.MILLISECONDS.sleep(millsec);
            System.out.printf("%fs\n",mills/1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        ExecutorService exe= Executors.newCachedThreadPool();
        for(int i=0;i<5;i++) {
            exe.execute(new prac6());
        }
        exe.shutdown();
    }
}
