package com.company.Graph;

import java.util.ArrayList;

public class DenseGraph  implements Graph {
    private int v;
    private int e;
    //private ArrayList<ArrayList<Boolean>> matrix;
    private int[][] matrix;
    private boolean isdirected;
    public DenseGraph(int v, boolean isdirected){
        this.v = v;
        this.e = 0;
        this.isdirected = isdirected;
        matrix = new int[v][v];
        for(int i=0;i<v;i++){
            //ArrayList<Boolean> list = new ArrayList(v);
            for(int j = 0 ;j<v;j++)
                matrix[i][j] = 0;
            //matrix.add(list);
        }
    }

    public void addEdge(int a, int b){
        if(hasEdge(a,b)) return;
        matrix[a][b] = 1;
        if(!isdirected)
            matrix[b][a] = 1;
        e++;//注意bug
    }
    public boolean hasEdge(int a, int b){
        if(a<v && b<v)
            return matrix[a][b] == 1 ? true:false;
        throw new IllegalArgumentException("参数错误");
    }
    public void show(){
        for(int i=0;i<v;i++){
            for(int j=0;j<matrix[i].length;j++)
                System.out.printf("%d ",matrix[i][j]);
            System.out.println();
        }
    }
    public int getV() {
        return v;
    }

    public int getE() {
        return e;
    }
    public int[][] getMatrix() {
        return matrix;
    }

}
