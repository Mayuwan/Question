package com.company.MinimumSpanTree;

import java.util.ArrayList;
import java.util.LinkedList;

public class LazyPrimMST<W extends Comparable> {
    private DenseGraph G;
    private boolean[] marked;
    //private W minweight;
    private ArrayList<Edage<W>> mst;//存放最小生成树的所有横切边
    private MinHeap<Edage<W>> heap;//用于存放横切边,选取权值最小的边

    public LazyPrimMST(DenseGraph G){
        this.G = G;
        heap = new MinHeap();
        mst = new ArrayList<>();
        marked = new boolean[G.V()];
        for(int i=0;i<G.V();i++){
            marked[i] = false;
        }

        //最小生成树算法
        visit(0);
        while(!heap.isEmpty()){
            Edage d = heap.deleteMin();//权值最小的横切边

            if(marked[d.getEnd()] == marked[d.getFrom()]){      //判断是否为横切边
                continue;
            }

            mst.add(d);
            if(!marked[d.getEnd()]){ visit(d.getEnd()); }
            else{ visit(d.getFrom()); }
        }
        /*minweight = mst.get(0).getWeight();
        for(int i=1; i<mst.size(); i++){
            minweight = mst.get(i).getWeight();
        } */
    }
    
    private void visit(int v){
        marked[v] = true;
        DenseGraph.adjIterator adj = G.getAdjIterater(v);
        for(Edage edg = adj.begin(); !adj.end(); edg =adj.next()){
            if(!marked[edg.other(v)]){//是横切边
                heap.insert(edg);
            }
        }
    }

    public ArrayList<Edage<W>> getMst() {
        return mst;
    }

   /* public W getMinweight() {
        return minweight;
    }*/
}
