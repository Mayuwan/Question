package com.company;
import com.company.Graph.*;

public class Main {
    public static int binerySearch(int[] arr,int target){
        int l=0,r=arr.length-1;
        while (l<=r){
            int mid = (l+r)/2;
            if(arr[mid] >  target) r = mid-1;
            else if(arr[mid] <  target)  l = mid+1;
            else return mid;
        }
        return  -1;
    }
    public static void main(String[] args) {
	// write your code here
        /*bineryTree tree = new bineryTree();
        Integer[] arr = test.generateAlmostSorted(15,3);
        Arrays.asList(arr);
        for(Integer num:arr){
            System.out.printf("%s ",num.toString());
            tree.insert(num,1);
        }
        System.out.println();
        tree.remove(4);
        tree.levelOrder();
        */

        /*test unionfound
        int n=1000000;
        //test.testUF1(n);
        test.testUF2(n);
        test.testUF3(n);
        test.testUF4(n);
        */
        /*
        DenseGraph graph = new DenseGraph(v,false);
        readGraph readDense = new readGraph(graph,"G:\\java\\Intelli\\algorithm\\java\\Intelli\\Algo\\src\\com\\company\\Graph\\testG1.txt");
        graph.show();
*/
        int v = 7;
        SparseGraph graph2 = new SparseGraph(v,false);
        readGraph readSparse = new readGraph(graph2,"G:\\java\\Intellij\\newCoder\\java\\Intelli\\Algo\\src\\com\\company\\Graph\\testG2.txt");
        graph2.show();
        //DepthFirstSearch dep = new DepthFirstSearch(graph2);
       // System.out.println(dep.getCount());
        Path path = new Path(graph2,0);
        path.showPath(6);

        ShortestPath shortestPath = new ShortestPath(graph2,0);
        shortestPath.showPath(6);
        System.out.println(shortestPath.distance(6));

        /*int n=1000000;
        int[] arr = test.generateRandom(n, 0,n);//不能自动装箱
        long start = System.currentTimeMillis();//栈溢出
        quickSort.partation3Ways(arr);
        System.out.println((double)(System.currentTimeMillis()-start)/1000);
        if(!test.isSorted(arr)) {System.out.println("排序错误");}
        */
    }
}
