package com.company.unionFind;

public class UnionFound2 {
    int[] parent;
    int count;
    int[] size;//优化，根每个节点拥有的大小,

    public UnionFound2(int count){
        this.count = count;
        parent = new int[count];
        size = new int[count];
        for(int i=0;i<count;i++) {
            parent[i] = i;//自己指向自己
            size[i] =1;
        }
    }

    public int find(int q){//找到该节点的根
        while(parent[q] != q){
            q = parent[q];
        }
        return q;
    }
    public boolean isConnected(int p , int q){
        return find(p)== find(q);
    }
    public void union(int p, int q){
        int pRoot = find(p);
        int qRoot = find(q);
        if(pRoot == qRoot) return;
        if(size[pRoot]<size[qRoot]){
            parent[pRoot] = qRoot;
            size[qRoot] += size[pRoot];
        }

        else{
            parent[qRoot] = pRoot;
            size[pRoot] += size[qRoot];
        }

    }
}
