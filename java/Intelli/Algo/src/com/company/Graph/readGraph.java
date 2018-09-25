package com.company.Graph;
import java.io.*;
import com.company.Graph.*;
public class readGraph {
    public readGraph (Graph graph, String filename) {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String firstLine = reader.readLine();
            int V,E,a,b;
            String line;
            V = Integer.valueOf(firstLine.split(" ")[0]);
            E = Integer.valueOf(firstLine.split(" ")[1]);
            for(int i=0;i<E;i++){
                line=reader.readLine();
                a = Integer.valueOf(line.split(" ")[0]);
                b = Integer.valueOf(line.split(" ")[1]);
                graph.addEdge(a,b);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
