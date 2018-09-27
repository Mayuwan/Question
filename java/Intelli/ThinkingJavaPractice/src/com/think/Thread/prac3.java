package com.think.Thread;
import java.util.concurrent.*;
public class prac3 {
    public static void main(String[] args) {
        //
        ExecutorService exe = Executors.newSingleThreadExecutor();
        for(int i=0;i<5;i++) {
            exe.execute(new prac1("start "+i));
        }
        exe.shutdown();

    }
}

