package com.company;

import java.util.LinkedList;

public class bineryTree<K,V> {

    Node<K,V> root;
    int count;
    public bineryTree(){
        root = null;
        count = 0;
    }

    /**
     * 二叉树的主要操作：插入，删除，打印，最大值，最笑值，删除最大值，删除最小值，是否包含某个元素**/
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
        LinkedList<Node> queue = new LinkedList();
        //借助队列
        queue.add(node);
        while(!queue.isEmpty()){
            Node n = queue.poll();
            System.out.printf("%s ",n.key.toString());
            if(n.left != null) queue.offer(n.left);
            if(n.right != null)  queue.offer(n.right);
        }
    }
    public <K> Node minimum(){
        if(root!=null)
            return minimum(root);
        else throw new IllegalArgumentException("空树");
    }
    private <K> Node minimum(Node n){
        //if(n == null ) return n.key;
        while(n.left != null)
            n = n.left;
        return n;
    }
    public <K> Node maximum(){
        if(root!=null)
            return maximum(root);
        else throw new IllegalArgumentException("空树");
    }
    private <K> Node maximum(Node n){
        //if(n == null ) return n.key;
        while(n.right != null)
            n = n.right;
        return n;
    }

    public void removeMin(){
        root = removeMin(root);//删除最小元素并返回新root
    }
    private Node removeMin(Node n){
        if(n.left==null){
            //右子树为空，该节点n就是最小值，返回null，右子树存在，返回右子树的跟
            count--;
            return n.right;
        }
        n.left=removeMin(n.left);
        return n;
    }
    public void removeMax(){
        root = removeMax(root);//删除最大元素并返回新root
    }
    private Node removeMax(Node n){
        if(n.right==null){
            //右子树为空，该节点n就是最大值，左子树存在，返回左子树的跟
            count--;
            return n.left;
        }
        n.right=removeMax(n.right);
        return n;
    }
    public <K extends Comparable> void remove(K key){
        root = remove(root,key);
    }
    private <K extends Comparable> Node remove(Node n, K key){
        if(n==null) throw new IllegalArgumentException("没有该key");
        if(key.compareTo(n.key) > 0) {
             n.right = remove(n.right,key);
             return n;
        }
        else if(key.compareTo(n.key) < 0){
             n.left = remove(n.left,key);
            return n;
        }
        else{
            if(n.right == null){
                count--;
                return n.left;
            }
            if(n.left == null){
                count--;
                return n.right;
            }
            /*方法1：左右子树都不为空，将右子树的最小值作为根返回，但要先将最小值删除；
            方法二：左右子树都不为空，将左子树的最大值作为根返回，但要先将原来书中的最大值删除；
            * */
            //方法一：
            /*Node successor = new Node(maximum(n.right));
            successor.right = removeMin(n.right);
            successor.left = n.left;
            return successor;*/
            //方法二
            Node precursor = new Node(minimum(n.left));
            precursor.left = removeMax(n.left);
            precursor.right = n.right;
            return precursor;
        }
    }
}
