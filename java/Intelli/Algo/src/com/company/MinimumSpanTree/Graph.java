package com.company.MinimumSpanTree;

public abstract class Graph<W extends Comparable> {
    abstract int V();
    abstract int E();
    abstract Edage<W>[] getAllEdages();
    abstract void addEdage(int a,int b,W weight );
    abstract Iterable<Edage> adj(int v);
}
