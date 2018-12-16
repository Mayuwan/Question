package com.company.MinimumSpanTree;

public class DenseGraph<W extends Comparable> extends Graph {
    private int V;
    private int E;
    //private ArrayList<ArrayList<Boolean>> matrix;
    private Edage<W>[][] matrix;
    private boolean isdirected;

    public DenseGraph(int v, boolean isdirected){
        this.V = v;
        this.E = 0;
        this.isdirected = isdirected;
        matrix = new Edage[v][v];
        for(int i=0;i<v;i++){
            //ArrayList<Boolean> list = new ArrayList(v);
            for(int j = 0 ;j<v;j++)
                matrix[i][j] = null;
            //matrix.add(list);
        }
    }

    @Override
    Edage[] getAllEdages() {
        Edage[] edages = new Edage[E*2];
        int k=0;
        for(int i=0;i<V;i++){
            for(int j=0;j<matrix[i].length;j++){
                if(matrix[i][j] != null){
                    edages[k] = matrix[i][j];
                    k++;
                }
            }
        }
        return edages;
    }

    @Override
    void addEdage(int a, int b, Comparable weight) {
        //if(hasEdge(a,b)) return;
        if (a<0 || a>=V || b<0 || b>=V) {  throw  new IllegalArgumentException(""); }
        matrix[a][b] = new Edage(a,b,weight);
        if(!isdirected)
            matrix[b][a] = new Edage(b,a,weight);
        E++;//注意bug
    }

    public boolean hasEdge(int a, int b){
        if (a<0 || a>=V || b<0 || b>=V) {  throw  new IllegalArgumentException(""); }

        return matrix[a][b] != null ? true:false;
    }
    public void show(){
        for(int i=0;i<V;i++){
            for(int j=0;j<matrix[i].length;j++){
                if(matrix[i][j] == null){
                    System.out.printf("null ");
                }else{System.out.printf("%s ",matrix[i][j].getWeight());}
            }
            System.out.println();
        }
    }

    @Override
    Iterable<Edage> adj(int v) {
        return null;
    }

    public adjIterator getAdjIterater(int v){
        return new adjIterator(v);
    }
    class adjIterator{
        //private Graph G;
        private int v;
        private int index;
        public adjIterator(int v){
            //this.G = graph;//////?????
            this.v = v;
            this.index=0;
        }
        public Edage begin(){
            index =-1;
            return next();
        }
        public Edage next(){
            //index++;
            for(index+=1;index<matrix[v].length;index++)  {//注意index+=1条件
                if(matrix[v][index] != null){
                    return matrix[v][index];
                }
            }
            return null;
        }
        public boolean end(){
            return index>= matrix[v].length;
        }
    }
    public int V() {
        return V;
    }

    public int E() {
        return E;
    }
    public Edage[][] getMatrix() {
        return matrix;
    }

}
