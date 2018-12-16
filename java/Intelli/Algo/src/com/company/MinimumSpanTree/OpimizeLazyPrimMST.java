package com.company.MinimumSpanTree;

import java.util.ArrayList;

/***使用 IndexMinHeap保存每个节点给邻接边中最小权值的边*/
public class OpimizeLazyPrimMST<W extends Comparable> {
    private DenseGraph G;
    private ArrayList<Edage<W>> mst;//存放最小生成树的横切边

    private boolean[] marked;
    private IndexMinPQ pq;
    private Edage<W>[] edageTo;// 存放每个节点的最小横切边

    public OpimizeLazyPrimMST(Class<W> type,DenseGraph G){
        this.G = G;
        mst = new ArrayList(G.V()-1);

        //初始化最小索引堆
        pq = new IndexMinPQ(G.V());
        //pq.insert(0,0.0);
        marked = new boolean[G.V()];
        for(int i=0;i<G.V();i++){
            marked[i] = false;
        }
        edageTo = new Edage[G.V()];//每个元素为空

        //最小生成树算法
        visit(0);
        while(!pq.isEmpty()){//IndexMinHeap不为空
            int index = pq.delMin();//权值最小的横切边
            if(edageTo[index]!=null)mst.add(edageTo[index]);
            visit( index );
        }
    }
    
    private void visit(int v){
        marked[v] = true;
        DenseGraph.adjIterator adj = G.getAdjIterater(v);
        for(Edage edg = adj.begin(); !adj.end(); edg =adj.next()){
            int other = edg.other(v);
            if(marked[other]){continue;}//是横切边
            //与IndexMinHeap[edg.other]比较，如果为空，直接放入，否则比较，数值小的边放入IndexMinHeap

            if(edageTo[other]==null){
                edageTo[other] = edg;
                pq.insert(other,edg.getWeight());
            }
            else if(edg.getWeight().compareTo(edageTo[other].getWeight())<0){
                edageTo[other] = edg;
              pq.change(other,edg.getWeight());
            }


        }
    }

    public ArrayList<Edage<W>> getMst() {
        return mst;
    }


}
