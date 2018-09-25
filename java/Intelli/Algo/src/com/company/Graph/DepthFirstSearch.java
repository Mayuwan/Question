package com.company.Graph;

public class DepthFirstSearch {
    private boolean[] visit;
    private int count;
    private sparseGraph G;
    public DepthFirstSearch(sparseGraph G){
        this.G = G;
        this.count=0;
        visit = new boolean[G.getV()];
        for(int i=0;i<G.getV();i++){
            this.visit[i] = false;
        }

        for(int i=0;i<G.getV();i++){
            if(!visit[i]){
                DFS(i);//深度遍历第i个点
                count++;
            }
        }
    }
    /**
     * 将与i相连接的所有节点全部遍历一遍*/
    public void DFS(int v){
        visit[v] = true;
        sparseGraph.adjIterator adj = G.getAdjIterater(v);
        for(int i=adj.begin();!adj.end(); i=adj.next()){
            if(!visit[i]){
                DFS(i);
            }
        }
    }
    public int getCount() {
        return count;
    }

}
