package com.company;

public class Node<K,V> {
    K key;
    V value;
    Node left;
    Node right;
    public Node(K key, V val){
        this.key = key;
        this.value = val;
        this.left = null;
        this.right = null;
    }
}

