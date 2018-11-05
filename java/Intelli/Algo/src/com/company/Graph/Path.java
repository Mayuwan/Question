package com.company.Graph;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Stack;

/**利用深度优先遍历寻找两点之间的路径*/
public class Path {
    private SparseGraph G;//稀疏图，稠密图都可以
    private boolean[] visit;
    private int source;
    private int[] from;//记录起始节点

    /**@param s 起始节点
     * */
    public Path(SparseGraph G,int s){
        this.G = G;
        visit = new boolean[G.getV()];
        from = new int[G.getV()];
        for(int i=0;i<G.getV();i++){
            visit[i] = false;
            from[i] = -1;
        }
        source = s;

        DFS(source);
    }
    /**将与v相邻节点全部遍历一遍*/
    private void DFS(int v){
        visit[v] = true;
        SparseGraph.adjIterator adj = G.getAdjIterater(v);
        for(int i=adj.begin();!adj.end(); i=adj.next()){
            if(!visit[i]){
                from[i] = v;
                DFS(i);
            }
        }
    }

    /**@param w 目标节点
     * */
    public boolean hasPath(int w){
        //判断w是否越界
        if(!(w<G.getV() && w>0)) throw new IllegalArgumentException("节点不存在");
        return visit[w];
    }

    public ArrayList path(int w){
        if(!hasPath(w)){
            return null;
        }
        ArrayList path = new ArrayList();
        //倒着找到起始点
        Stack stack = new Stack();
        int temp = w;
        while(temp != -1){//只有起始节点的from值为-1
            stack.push(temp);
            temp = from[temp];
        }
        while(!stack.isEmpty()){
            path.add(stack.pop());
        }
        return path;
    }

    public void showPath(int w){
        ArrayList list = path(w);
        if(list==null){return;}
        for(int i=0;i<list.size();i++){
            System.out.printf(list.get(i)+"->");
            if(i== list.size()-1) {
                System.out.printf("end\n");
            }
        }
    }


}
