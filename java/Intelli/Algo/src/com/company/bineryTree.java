package com.company;
import java.util.PriorityQueue;

public class bineryTree<K,V> {

    Node<K,V> root;
    int count;
    public bineryTree(){
        root = null;
        count = 0;
    }
    public int size(){return count;}
    public boolean isEmpty(){return count == 0 ? true:false;}
    public <K extends Comparable> void insert(K key, V value){
        root = insert(root,key,value);
    }
    private <K extends Comparable> Node insert(Node r, K key,V value){
        if(r == null){
            count++;
            return new Node(key,value);
        }
        if(key.compareTo(r.key) == 0) {
            //更新值
            r.value = value;
        }
        else if(key.compareTo(r.key)< 0)   r.left = insert(r.left,key,value);//向左子树插入，成功或失败都返回给root的左节点
        else   r.right = insert(r.right,key,value);
        return r;//返回根节点
    }
    public <K extends Comparable> boolean contains(K key){//搜索是否包含键
        return contains(root,key);
    }
    private <K extends Comparable> boolean contains(Node ro, K key){
        if(ro == null) return false;
        if(key.compareTo(ro.key) == 0) return true;
        else if(key.compareTo(ro.key) < 0) return contains(ro.left,key);
        else return contains(ro.right,key);
    }
    public void preOrder(){
        preOrder(root);
    }
    private void preOrder(Node node){
        if(node == null) return;
        System.out.printf("%s ",node.key.toString());
        preOrder(node.left);
        preOrder(node.right);
    }
    public void inOrder(){
        inOrder(root);
    }
    private void inOrder(Node node){
        if(node == null) return;
        inOrder(node.left);
        System.out.printf("%s ",node.key.toString());
        inOrder(node.right);
    }
    public void postOrder(){
        postOrder(root);
    }
    private void postOrder(Node node){
        if(node == null) return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.printf("%s ",node.key.toString());
    }
    public void levelOrder(){
        levelOrder(root);
    }
    //有问题
    private void levelOrder(Node node){
        PriorityQueue<Node> queue = new PriorityQueue();
        queue.add(node);
        while(true){
            if(queue.isEmpty()) break;
            Node n = queue.poll();
            System.out.printf("%s ",n.key.toString());
            if(n.left != null) queue.add(n.left);
            if(n.right != null)  queue.add(n.right);
        }
    }
    public <K> K minimum(){
        if(root!=null)
            return minimum(root);
        else throw new IllegalArgumentException("空树");
    }
    private <K> K minimum(Node n){
        //if(n == null ) return n.key;
        while(n.left != null)
            n = n.left;
        return (K)n.key;
    }
    public <K> K maximum(){
        if(root!=null)
            return maximum(root);
        else throw new IllegalArgumentException("空树");
    }
    private <K> K maximum(Node n){
        //if(n == null ) return n.key;
        while(n.right != null)
            n = n.right;
        return (K)n.key;
    }
    //不知道对不对,不对
    public void removeMin(){
        removeMin(root);
    }
    private void removeMin(Node n){
        if(n.left==null){
            if(n.right==null) //右子树为空，该节点就是最大值
                n=null;
            else{
                n = n.right;
            }
            count--;
        }
        removeMin(n.left);
    }
}
