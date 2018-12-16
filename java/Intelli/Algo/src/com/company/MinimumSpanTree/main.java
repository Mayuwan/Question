package com.company.MinimumSpanTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class main {
    public static void main(String[] args){

        ///test MinHeap
        /*MinHeap heap = new MinHeap(8);
        Integer[] ints= {8,15,4,6,2,5,10,13};
        for(Integer i: ints){
            heap.insert(i);
        }
        heap.show();
        System.out.println();
        heap.heapSort();
        */
        int v = 8;
        DenseGraph graph2 = new DenseGraph<>(v,false);
        ReadGraph<Double> readSparse = new ReadGraph<Double>(graph2,"G:\\java\\newCoder\\java\\Intelli\\Algo\\src\\com\\company\\MinimumSpanTree\\testG1.txt");
        graph2.show();
        System.out.println();
        //LazyPrimMST lazyPrimMST= new LazyPrimMST(graph2);
        OpimizeLazyPrimMST lazyPrimMST= new OpimizeLazyPrimMST(Double.class,graph2);
        ArrayList<Edage> mst = lazyPrimMST.getMst();
        System.out.println(mst.size());
        for( Edage d : mst){
            System.out.printf("%d-%d:%s\n",d.getFrom(),d.getEnd(),d.getWeight());/**错误：最小索引堆有问题*/
        }

    }
}
