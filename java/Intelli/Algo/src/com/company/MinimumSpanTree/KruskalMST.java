package com.company.MinimumSpanTree;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;

public class KruskalMST {
    private ArrayList<Edage> mst;
    //private QuickUnionFound

    public KruskalMST(Graph graph){
        mst = new ArrayList(graph.E()-1);
        MinHeap<Edage> pq = new MinHeap();
        //并查集
        QuickUnionFound uf = new QuickUnionFound(graph.V());
        //Krustal算法
        for(Edage d: graph.getAllEdages()){pq.insert(d);}
        /*Edage[] allEdages = graph.getAllEdages();
        quickSort.partation3Ways(allEdages);
        for(Edage d:  allEdages){
            if(uf.isConnected(d.getEnd(),d.getFrom())){
                continue;
            }
            mst.add(d);
            uf.union(d.getEnd(),d.getFrom());
        }*/
        while(!pq.isEmpty() && mst.size() <= graph.V()-1){
            Edage d = pq.deleteMin();
            if(uf.isConnected(d.getEnd(),d.getFrom())){
                continue;
            }
            mst.add(d);
            uf.union(d.getEnd(),d.getFrom());
        }
    }

    public ArrayList<Edage> getMst() {
        return mst;
    }
}
