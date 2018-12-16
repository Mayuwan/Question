package offer;

import java.util.Arrays;

public class pra6 {

   public static BinaryTreeNode rebulidTree(int[] preOrder, int[] inOrder) {
        if(preOrder.length <=0 ){return null;}
        if(inOrder.length<=0){return null;}

        BinaryTreeNode root = new BinaryTreeNode(preOrder[0]);

        int left_count=0;
        for ( int i = 0; i < inOrder.length; i++) {
            if (inOrder[i] == preOrder[0]) {
                left_count++;
                break;
            }
        }
        int right_count = inOrder.length-left_count;
        if(left_count > 0){
            root.m_pLeft = rebulidTree(Arrays.copyOfRange(preOrder,1,left_count+1),Arrays.copyOfRange(inOrder,0,left_count+1));
        }
        else if (right_count >0 ){
            root.m_pRight = rebulidTree(Arrays.copyOfRange(preOrder,preOrder.length-right_count,preOrder.length),Arrays.copyOfRange(inOrder,inOrder.length-right_count,inOrder.length));
        }
        return root;
    }
    public static void main(String[] args){
        char[] str = new char[]{'a','3','5','f'};

        System.out.println(Arrays.copyOfRange(str,1,3));

    }
}

class BinaryTreeNode{
    int m_nValue;
    BinaryTreeNode m_pLeft;
    BinaryTreeNode m_pRight;
    public BinaryTreeNode(int val){

        this.m_nValue = val;
        this.m_pLeft = null;
        this.m_pRight = null;
    }
}
