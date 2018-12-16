package com.company.MinimumSpanTree;

import sun.plugin.com.event.COMEventHandler;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReadGraph<W extends Comparable> {
    public ReadGraph(Graph graph, String filename) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String firstLine = reader.readLine();
            int V,E,a,b;
            W weight;
            String line;
            V = Integer.valueOf(firstLine.split(" ")[0]);
            E = Integer.valueOf(firstLine.split(" ")[1]);
            if(V != graph.V()){throw new IllegalArgumentException("顶点数不对");}

            for(int i=0;i<E;i++){
                line=reader.readLine();
                String[] strs = line.split(" ");
                a = Integer.valueOf(strs[0]);
                b = Integer.valueOf(strs[1]);
                weight = (W) Double.valueOf(strs[2]);
                graph.addEdage(a,b,weight);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
