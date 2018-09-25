package com.company.unionFind;

public class UnionFound4 {
    int[] parent;
    int count;
    int[] rank;//优化，根节点为i的树的高度

    public UnionFound4(int count){
        this.count = count;
        parent = new int[count];
        rank = new int[count];
        for(int i=0;i<count;i++) {
            parent[i] = i;//自己指向自己
            rank[i] =1;
        }
    }
    public int find(int p){
        if(p<0 || p>count) throw new IllegalArgumentException("不存在该节点");
        /*路径压缩方法1
        while(parent[p]!=p){
            parent[p] = parent[parent[p]];//路径压缩，跳了一步
            p = parent[p];
        }*/
        /*路径压缩方法2，缺点：递归有开销*/
        if(parent[p] != p){
            parent[p] = find(parent[p]);
        }
        return parent[p];
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
