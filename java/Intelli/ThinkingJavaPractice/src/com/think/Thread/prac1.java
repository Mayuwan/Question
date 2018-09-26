package com.think.Thread;

public class prac1 implements Runnable{
    private static int taskCount=0;
    private final int id= taskCount++;

    public prac1(String startMessage) {
        System.out.println(startMessage);
    }
    public void run(){
        int i=0;
        while (i<3) {
            System.out.println(id+"("+i+")");
            i++;
            Thread.yield();
        }
        System.out.println("end!!!");
    }
    public static void main(String[] args) {
        //pra1 task = new pra1("start");
        Thread t = new Thread(new prac1("start thread"));
        t.start();
        System.out.println("waiting for the end");

        for(int i=0;i<5;i++) {
            new Thread(new prac1("start "+i)).start();
        }
    }
}
