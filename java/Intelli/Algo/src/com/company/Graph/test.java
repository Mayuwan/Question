package com.company.Graph;

public class test {
    public static void main(String[] args){
        int v = 7;
        SparseGraph graph2 = new SparseGraph(v,false);
        readGraph readSparse = new readGraph(graph2,"G:\\java\\newCoder\\java\\Intelli\\Algo\\src\\com\\company\\Graph\\testG2.txt");
        graph2.show();
        //DepthFirstSearch dep = new DepthFirstSearch(graph2);
        // System.out.println(dep.getCount());
        Path path = new Path(graph2,0);
        path.showPath(6);

        ShortestPath shortestPath = new ShortestPath(graph2,0);
        shortestPath.showPath(6);
        System.out.println(shortestPath.distance(6));
    }

}
