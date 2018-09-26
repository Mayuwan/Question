package com.think.Thread;
import java.util.concurrent.*;
import java.util.ArrayList;
public class prac5 implements Callable{
    private int n;
    public prac5(int m) {n = m;}
    private int fib(int m) {
        if(m<2) return 1;
        return fib(m-2) + fib(m-1);
    }

    public Integer call() {
        int sum=0;
        for(int i=0;i<n;i++) {
            sum += fib(i);
        }
        return sum;
    }
    public static void main(String[] args) {
        ArrayList<Future> results = new ArrayList();
        ExecutorService serivce = Executors.newCachedThreadPool();
        for(int i=5;i<10;i++) {
            results.add(serivce.submit(new prac5(i)));
        }
        for(Future re:results) {
            if(re.isDone()) {
                try {
                    System.out.println(re.get());
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }finally {
                    serivce.shutdown();
                }
            }
        }
    }

}
