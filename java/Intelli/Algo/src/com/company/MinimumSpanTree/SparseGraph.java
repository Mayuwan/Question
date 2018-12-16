package com.company.MinimumSpanTree;

import sun.plugin.com.event.COMEventHandler;

import java.util.LinkedList;

public class SparseGraph<W extends Comparable> extends Graph {
    private int V;//顶点数
    private int E;//边数
    private LinkedList<LinkedList<Edage<W>>> matrix;
    private boolean isDirected;//有向图 | 无向图

    public  SparseGraph(boolean isDirected){
        this.isDirected = isDirected;
    }

    public SparseGraph(int v, boolean isDirected){
        this.V = v;
        this.E =0;
        this.isDirected = isDirected;
        matrix = new LinkedList();
        for(int i=0;i<v;i++) matrix.add(new LinkedList());
    }


    @Override
    int V() {
        return V;
    }

    @Override
    int E() {
       return E;
    }

    @Override
    Edage[] getAllEdages() {
        Edage[] edages = new Edage[E];
        int k=0;
        for(int i=0;i<V;i++){
            if(matrix.get(i).size()>0){
                for(int j=0;j<matrix.get(i).size();j++) {
                    edages[k] = matrix.get(i).get(j);
                    k++;
                }
            }
        }
        return edages;
    }

    @Override //有权无向图考虑自环边，平行边
    void addEdage(int a, int b, Comparable weight) {
        //if(hasEdge(a,b)) return;
        if (a<0 || a>=V || b<0 || b>=V) {  throw  new IllegalArgumentException(""); }

        matrix.get(a).add(new Edage(a,b,weight));
        if(a!=b && !isDirected)
            matrix.get(b).add(new Edage(b,a,weight));

        E++;
    }

    @Override
    Iterable<Edage> adj(int v) {
        return null;
    }

    public boolean hasEdge(int a, int b){
        if (a<0 || a>=V || b<0 || b>=V) {  throw  new IllegalArgumentException(""); }
        for(int i=0;i<matrix.get(a).size();i++){
            if(matrix.get(a).get(i).getEnd() == b)return true;
        }
        return false;
    }
    public void show(){
        for(int i=0;i<V;i++){
            if(matrix.get(i).size()>0){
                System.out.printf("%d : ",i);
                for(int j=0;j<matrix.get(i).size();j++)
                    System.out.printf("(To:%d,wt:%s) ",matrix.get(i).get(j).getEnd(),matrix.get(i).get(j).getWeight());
                System.out.println();
            }
        }
    }
    public adjIterator getAdjIterater(int v){
        return new adjIterator(v);
    }
    class adjIterator{//遍历器，遍历节点v的所有相邻节点
        //private Graph G;
        private int v;
        private int index;
        public adjIterator(int v){
            //this.G = graph;//////?????
            this.v = v;
            this.index=0;
        }
        public Edage begin(){
            index =0;
            if(matrix.get(v).size() == 0) return null;
            return matrix.get(v).get(index);
        }
        public Edage next(){
            index++;
            if(index<matrix.get(v).size())  return matrix.get(v).get(index);
            return null;
        }
        public boolean end(){
            return index>= matrix.get(v).size();
        }
    }


    public LinkedList<LinkedList<Edage<W>>> getMatrix() {
        return matrix;
    }
}
