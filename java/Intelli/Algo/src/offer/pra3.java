package offer;

public class pra3 {
    /**要有良好的鲁棒性，考虑很多图书情况的测试用例*/
    public static boolean contians(int[][] arr, int target){
        int rows = arr.length;
        int colomus = arr[0].length;
        if(arr == null){return false;}
        if(rows == 0 || colomus == 0){return false;}

        for(int j=colomus-1,i=0; j>=0 && i<rows;){
            if(target > arr[i][j]){
                i++;
            }
            else if(target < arr[i][j]){
                j--;
            }
            else{
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args){
        int[][] arr = {{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
        System.out.println(contians(arr,1));
        System.out.println(contians(arr,15));
        System.out.println(contians(arr,11));

        System.out.println(contians(null,3));

        System.out.println(contians(arr,5));
        System.out.println(contians(arr,-1));
        System.out.println(contians(arr,20));
    }
}
