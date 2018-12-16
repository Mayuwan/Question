package offer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;

public class Solution {
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    public void push(int node) {
        //if(node == null){return;}
        stack1.push(node);

    }

    public int pop() {
        if(stack2.isEmpty()){
            while(!stack1.isEmpty()){
                stack2.push(stack1.pop());
            }
        }

        if(!stack2.isEmpty()){
           return stack2.pop();
        }

        throw new RuntimeException();
    }
    public int minNumberInRotateArray(int [] array) {
        if(array == null){throw new IllegalArgumentException();}
        if(array.length == 0){return 0;}

        int left =0;
        int right = array.length-1;
        if(array[left]<array[right]){return array[left];}

        while(right-left!=1){
            int mid = (left+right)/2;
            if(array[mid] == array[left] && array[mid] == array[right]){
                //special  handle
                if(array[mid-1] == array[mid]){left = mid;}
                if(array[mid+1] == array[mid]){right = mid;}
            }

            if(array[mid] >= array[left] && array[mid] > array[right]){
                left = mid;
            }
            else if(array[mid] < array[left] && array[mid] <= array[right]){
                right = mid;
            }
        }


            return array[right];
    }
    public int NumberOf1(int n) {
        int res=0;
        while(  true){
            if(n == 0) break;
            n=(n-1) & n;
            res++;
        }
        return res;
    }


    public double Power(double base, int exponent) {
        if(compareBaseisZero(base)){//错误
            if(exponent < 0){throw new IllegalArgumentException();}
            else{return 0;}
        }

        if(exponent == 0){return 1;}

        double res =base;
        int positiveExponent = exponent > 0 ? exponent : 0-exponent;

        for(int i=0;i<positiveExponent-1;i++){
            res *= base;
        }

        if(exponent > 0){return res;}
        else {return 1.0/res;}
    }
    public boolean compareBaseisZero(double base){
        if(base - 0.0 < 0.00000001 &&  0.0-base < 0.00000001){
            return true;
        }
        return false;
    }
    public boolean IsPopOrder(int [] pushA,int [] popA) {
        if(pushA == null || popA == null){return false;}
        if(pushA.length != popA.length){return false;}

        Stack<Integer> stack = new Stack();
        int j;
        for(j=0; j<pushA.length && pushA[j]!=popA[0]; j++){
            stack.push(pushA[j]);
        }

        int equalj=j;
        for(int i=1;i<popA.length;){
            int popEle = popA[i];

            if(!stack.isEmpty()){
                if(stack.peek() == popA[i]){
                    stack.pop();
                    i++;
                }
                else{
                    boolean hasPopEle =false;
                    for(j=equalj+1;j<pushA.length;j++){
                        stack.push(pushA[j]);
                        if(pushA[j] == popEle){
                            hasPopEle = true;
                            break;
                        }
                    }
                    if(!hasPopEle){return false;}

                    equalj = j;
                }
            }


        }
        if(stack.isEmpty()){return true;}
        return false;
    }

    TreeNode firstHead= null;
    TreeNode lastHead= null;

    public TreeNode Convert(TreeNode pRootOfTree) {
        if(pRootOfTree==null){return null;}

        Convert(pRootOfTree.left);
        //mid
        if(lastHead == null){
            lastHead = pRootOfTree;
            firstHead = pRootOfTree;
        }else{
            lastHead.right = pRootOfTree;
            pRootOfTree.left = lastHead;
            lastHead = pRootOfTree;

        }

        Convert(pRootOfTree.right);
        return firstHead;
    }

    public ArrayList<String> Permutation(String str) {
        ArrayList<String> res = new ArrayList();
        if(str != null && str.length() != 0){
            Permutation(str.toCharArray(),0,res);
        }
        Collections.sort(res);
        return res;
    }
    public void Permutation(char[] str,int fixedIndex, ArrayList<String> res) {
        if(fixedIndex==str.length-1){
            String string = String.valueOf(str);
            if(!res.contains(string)){
                res.add(string);
            }
            return;
        }


        for(int j=fixedIndex;j<str.length;j++){
            swap(str,fixedIndex,j);
            Permutation(str,fixedIndex+1,res);
            swap(str,fixedIndex,j);
        }
    }
    public void swap(char[] str,int i,int j){
        char temp  = str[i];
        str[i] = str[j];
        str[j] = temp;
    }


    public static void main(String[] args){
        Solution sd = new Solution();
        System.out.println(sd.Permutation("abc"));
    }
}
class TreeNode {
    int val = 0;
    TreeNode left = null;
    TreeNode right = null;

    public TreeNode(int val) {
        this.val = val;

    }

}