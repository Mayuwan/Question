package offer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class pra5 {

    public static ArrayList<Integer> readFromtail(ListNode listNode){
        if(listNode == null){throw new IllegalArgumentException();}
        Stack<ListNode> stack = new Stack();
        stack.push(listNode);
        ListNode temp = listNode;
        while(temp.m_pNext!=null){
            stack.push(temp.m_pNext);
            temp = temp.m_pNext;
        }
        ArrayList<Integer>  res = new ArrayList();
        while(!stack.isEmpty()){
            res.add(stack.pop().m_nKey);
        }
        return res;
    }



    public static void main(String[] args){
        ListNode head = new ListNode(100);
        for(int i=0; i<5; i++){
            ListNode.addToTail(head,i);
        }
        readFromtail(head);
    }
}
/**链表的插入与删除
 * */
class ListNode{
    int m_nKey;
    ListNode m_pNext;
    public ListNode(int key){
        m_nKey=  key;
        m_pNext  =null;
    }

    public static void addToTail(ListNode head, int key){
        ListNode newNode = new ListNode(key);
        if(head == null){throw new IllegalArgumentException();}

        ListNode temp = head;
        while(temp.m_pNext!=null){
            temp = temp.m_pNext;
        }
        temp.m_pNext = newNode;

    }

    public static void removeNode(ListNode head, int value){//考虑的不全面
        if(head == null){return;}
        ListNode deletNode =null;
        if(head.m_nKey == value ){
            head = head.m_pNext;
        }
        ListNode temp = head;
        while(temp.m_pNext !=null &&  temp.m_pNext.m_nKey!=value){
            temp = temp.m_pNext;
        }
        if(temp.m_pNext != null && temp.m_pNext.m_nKey== value){
            temp.m_pNext = temp.m_pNext.m_pNext;
        }
    }

}
