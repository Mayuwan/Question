package com.company.unionFind;

public class UnionFound1 {
    //使用树的结构，无优化
    int[] parent;
    int count;

    public UnionFound1(int count){
        this.count = count;
        parent = new int[count];
        for(int i=0;i<count;i++) parent[i] = i;//自己指向自己
    }
    public int find(int q){//找到该节点的根
        while(parent[q] != q){
            q = parent[q];
        }
        return q;
    }
    public boolean isConnected(int p, int q){
        return find(p) == find(q);
    }

    public void union(int p , int q){
        int pRoot = find(p);
        int qRoot= find(q);
        if(pRoot == qRoot) return;
        parent[pRoot] = qRoot;//或parent[qRoot] = pRoot;
    }
}
