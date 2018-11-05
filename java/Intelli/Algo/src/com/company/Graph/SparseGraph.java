package com.company.Graph;

import java.util.LinkedList;

public class SparseGraph implements Graph{
    private int v;
    private int e;
    private LinkedList<LinkedList<Integer>> matrix;
    private boolean isDirected;

    public SparseGraph(int v, boolean isDirected){
        this.v = v;
        this.e =0;
        this.isDirected = isDirected;
        matrix = new LinkedList();
        for(int i=0;i<v;i++) matrix.add(new LinkedList());
    }
    //无向图要考虑自环边，平行边
    public void addEdge(int a, int b){
        if(hasEdge(a,b)) return;

        matrix.get(a).add(b);
        if(a!=b && !isDirected)
            matrix.get(b).add(a);

        e++;
    }
    public boolean hasEdge(int a, int b){

        for(int i=0;i<matrix.get(a).size();i++){
            if(matrix.get(a).get(i) == b)return true;
        }
        return false;
    }
    public void show(){
        for(int i=0;i<v;i++){
            if(matrix.get(i).size()>0){
                System.out.printf("%d : ",i);
                for(int j=0;j<matrix.get(i).size();j++)
                    System.out.printf("%d ",matrix.get(i).get(j));
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
        public int begin(){
            index =0;
            if(matrix.get(v).size() == 0) return -1;
            return matrix.get(v).get(index);
        }
        public int next(){
            index++;
            if(index<matrix.get(v).size())  return matrix.get(v).get(index);
            return -1;
        }
        public boolean end(){
            return index>= matrix.get(v).size();
        }
    }
    public int getV() {
        return v;
    }

    public int getE() {
        return e;
    }
    public LinkedList<LinkedList<Integer>> getMatrix() {
        return matrix;
    }
}
