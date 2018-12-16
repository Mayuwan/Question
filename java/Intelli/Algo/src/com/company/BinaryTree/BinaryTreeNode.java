package com.company.BinaryTree;

public class BinaryTreeNode<K,V> {
    K key;
    V value;
    BinaryTreeNode left;
    BinaryTreeNode right;
    public BinaryTreeNode(K key, V val){
        this.key = key;
        this.value = val;
        this.left = null;
        this.right = null;
    }
    public BinaryTreeNode(BinaryTreeNode<K,V> node){
        this.key = node.key;
        this.value = node.value;
        this.left = node.left;
        this.right = node.right;
    }
}

