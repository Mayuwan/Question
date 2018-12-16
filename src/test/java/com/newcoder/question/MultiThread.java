package com.newcoder.question;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThread {
    public static Object obj = new Object();
    public static void testSynchronized1(){
        synchronized (obj){
            try{
                for(int j=0;j<10;j++) {
                    Thread.sleep(1000);
                    System.out.println(String.format("T3 %d",j));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public static void testSynchronized2() {
        synchronized (new Object()) {
            try {
                for (int j = 0; j < 10; ++j) {
                    Thread.sleep(1000);
                    System.out.println(String.format("T4 %d", j));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void testSynchronized(){
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    testSynchronized1();
                    testSynchronized2();
                }
            }).start();
        }
    }
    public  static  void testBlockingQueue(){
        BlockingQueue<String> q= new ArrayBlockingQueue<>(10);
        new Thread(new Producer(q)).start();
        new Thread(new Consumer(q),"consumer1").start();
        new Thread(new Consumer(q),"consumer2").start();
    }

    private static int userId;
    private static ThreadLocal<Integer> threadLocalUserId = new ThreadLocal();
    public static void testThreadLocal(){
        for(int i=0;i<10;i++){
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        threadLocalUserId.set(finalI);
                        Thread.sleep(1000);
                        System.out.println("ThreadLocal: "+ threadLocalUserId.get());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for(int i=0;i<10;i++){
            final int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        userId = finalI;
                        Thread.sleep(1000);
                        System.out.println("userId: "+ userId);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public static void testExecutor(){
        //ExecutorService service = Executors.newSingleThreadExecutor();
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    for (int i=0;i<10;i++){
                        Thread.sleep(1000);
                        System.out.println("Executor1: " +i);
                    }
                }catch (Exception e ){
                    e.printStackTrace();
                }
            }
        });
        service.submit(new Runnable() {
            @Override
            public void run() {
                try{
                    for (int i=0;i<10;i++){
                        Thread.sleep(1000);
                        System.out.println("Executor2: " +i);
                    }
                }catch (Exception e ){
                    e.printStackTrace();
                }
            }
        });
        //service.shutdown();
        while (!service.isTerminated()){
            try{
                Thread.sleep(1000);
                System.out.println("waiting for shut down" );
            } catch (Exception e ){
                e.printStackTrace();
            }
        }
    }

    private static int counter = 0;
    private static AtomicInteger atomiInterger = new AtomicInteger();
    public static void testWithoutAtomic() {
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(1000);
                        for(int j=0;j<10;j++){
                            counter++;
                            System.out.println("counter: "+ counter);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public static void testWithAtomic() {
        for(int i=0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Thread.sleep(1000);
                        for(int j=0;j<10;j++){
                            System.out.println("atomiInterger: "+ atomiInterger.incrementAndGet());
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
    public static  void testFuture(){
        ExecutorService service = Executors.newSingleThreadExecutor() ;
        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(5000);
                return 1;
            }
        });
        service.shutdown();
        try{
            System.out.println(future.get(1000,TimeUnit.MILLISECONDS));//future.get()等待直到计算结果完成
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        //testSynchronized();
        //testBlockingQueue();
        //testThreadLocal();
        //testExecutor();
        //testWithoutAtomic();
        //testWithAtomic();
        testFuture();
    }
}
class mThread implements Runnable{
    private int tid;
    public mThread(int tid){
        this.tid = tid;
    }
    @Override
    public void run() {
        try{
            for(int i=0;i<10;i++){
                Thread.sleep(1000);
                System.out.println(String.format("%d:%d ",tid,i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
//一直在取线程
class Consumer implements Runnable{
    BlockingQueue<String> q;
    public Consumer(BlockingQueue<String> q){
        this.q = q;
    }
    @Override
    public void run() {
        try{
            while (true){
                System.out.println(Thread.currentThread().getName()+":"+q.take());
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
}
class Producer implements Runnable{
    BlockingQueue<String> q;
    public Producer(BlockingQueue<String> q){
        this.q = q;
    }
    @Override
    public void run() {
        try{
            for (int i=0;i<100;i++){
                Thread.sleep(100);
                q.put(String.valueOf(i));
            }
        }catch (Exception e ){
            e.printStackTrace();
        }
    }
}