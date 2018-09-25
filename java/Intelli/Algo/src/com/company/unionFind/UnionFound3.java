package com.company.unionFind;

public class UnionFound3 {
    int[] parent;
    int count;
    int[] rank;//优化，根节点为i的树的高度

    public UnionFound3(int count){
        this.count = count;
        parent = new int[count];
        rank = new int[count];
        for(int i=0;i<count;i++) {
            parent[i] = i;//自己指向自己
            rank[i] =1;
        }
    }
    public int find(int p){
        while(parent[p]!=p)
            p = parent[p];
        return p;
    }
    public boolean isConnected(int p, int q){
        return find(p) == find(q);
    }
    public void union(int p, int q){
        int pRoot = find(p);
        int qRoot = find(q);
        if(pRoot == qRoot) return;
        if(rank[pRoot]<rank[qRoot]){
            parent[pRoot] = qRoot;
        }
        else if(rank[pRoot]>rank[qRoot]){
            parent[qRoot] = pRoot;
        }
        else if(rank[pRoot]==rank[qRoot]){
            parent[pRoot] = qRoot;
            rank[qRoot] += 1;
        }
    }
}
