package com.company.Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class ShortestPath {
    private int source;
    private SparseGraph G;
    private boolean[] visit;
    private int[] from;
    private int[] order;

    public ShortestPath(SparseGraph G, int s){
        this.source = s;
        this.G = G;
        visit = new boolean[G.getV()];
        from = new int[G.getV()];
        order = new int[G.getV()];
        for (int i=0;i<G.getV();i++){
            visit[i] = false;
            from[i] = -1;
            order[i] = -1;
        }

        //使用队列
        Queue queue = new LinkedList();
        queue.offer(source);
        visit[s] = true;
        order[s] = 0;
        //广度优先遍历
        while(!queue.isEmpty()){
            int start = (int)queue.poll();

            SparseGraph.adjIterator adjIterator =  G.getAdjIterater(start);
            for(int j = adjIterator.begin(); !adjIterator.end(); j = adjIterator.next() ){
                if(!visit[j]){
                    visit[j] = true;
                    queue.offer(j);
                    order[j] = order[start]+1;
                    from[j] = start;
                }
            }
        }

    }

    public boolean hasPath(int w){
        if(!(w>0 && w<G.getV())){throw  new IllegalArgumentException("不存在该节点"+w);}
        return visit[w];
    }

    public ArrayList path(int w){
        if(!hasPath(w)){return null;}

        ArrayList path = new ArrayList();
        Stack stack = new Stack();
        int p = w;
        while(p != -1){
            stack.push(p);
            p = from[p];
        }

        while(!stack.isEmpty()){
            path.add(stack.pop());
        }
        return path;
    }

    public void showPath(int w){
        ArrayList path =  path(w);
        if(path == null){return;}

        for(int i=0;i<path.size();i++){
            System.out.printf(path.get(i)+"->");
            if(i== path.size()-1) {
                System.out.printf("end\n");
            }
        }
    }

    public int distance(int w){
        return order[w];
    }
}
