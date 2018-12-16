package offer;

public class pra9 {
    /**解法1：使用递归,时间效率很低*/
    public static long Fibonacci(int n) {
        if (n == 0) { return 0; }
        if (n == 1) { return 1; }

        return Fibonacci(n-1)+Fibonacci(n-2);
    }
    /**解法2:使用循环*/
    public static long f(int n){
        if(n==0){return 0;}
        if(n==1){return 1; }

        long first = 0;
        long second = 1;
        long res=0;
        for(int i=2; i<=n; i++){
            res = first+second;
            first = second;
            second = res;
        }
        return res;
    }

    public static void main(String[] args){

        long start = System.currentTimeMillis();
        System.out.println(f(100));
        System.out.println(System.currentTimeMillis()-start);

        start = System.currentTimeMillis();
         System.out.println(Fibonacci(100));
        System.out.println(System.currentTimeMillis()-start);
    }
}
