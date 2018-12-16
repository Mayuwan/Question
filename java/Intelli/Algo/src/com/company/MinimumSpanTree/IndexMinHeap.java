package com.company.MinimumSpanTree;

import java.lang.reflect.Array;

/**最小反向索引堆，出堆删除索引，不删除数据*/
public class IndexMinHeap<Item extends Comparable> {
    private int capacity;//堆中节点的最大容量
    private int count;//堆中现有节点的个数
    private Item[] data;//堆的数据
    private int[] indexes;//索引堆
    private int[] reverse;//反向索引

    public IndexMinHeap(Class<Item> type,int capacity) {
        this.capacity = capacity;
        data = (Item[]) Array.newInstance(type,capacity+1);
        indexes = new int[capacity+1];
        reverse = new int[capacity+1];
        for( int i = 0 ; i <= capacity ; i ++ ){
            reverse[i] = 0;
        }

        count = 0;//array[0]无用
    }
    public IndexMinHeap(Item[] arr, int capacity) {
        this.capacity = capacity;
        data = (Item[])new Object[capacity+1];
        for(int i=0; i<arr.length; i++) {
            data[i+1] = arr[i];
            count++;
        }
        for(int i=count/2; i>=1; i--)
            shiftDown(i) ;
    }
    public void insert(int index,Item key) {
        assert( count + 1 <= capacity );
        assert( index + 1 >= 1 && index + 1 <= capacity );

        index +=1;
        data[index] = key;
        indexes[count+1] = index;
        reverse[index]=count+1;
        count++;
        shiftUp(count);

    }
    private void shiftUp(int k) {
        //父节点：array[i/2]
        while( k>1 && data[indexes[k/2]].compareTo(data[indexes[k]]) > 0 ) {
            swap(indexes,k/2,k);
            reverse[indexes[k/2]] = k/2;
            reverse[indexes[k]] = k;
            k=k/2;
        }
    }
    public Item removeMin() {
        assert( count > 0 );

        Item min = (Item)data[1];
        swap(indexes,1,count);
        reverse[indexes[count]] = 0;
        reverse[indexes[1]] = 1;
        count--;
        shiftDown(1);
        return min;
    }
    public int extractMinIndex(){
        assert( count > 0 );

        int ret = indexes[1] - 1;
        swap( indexes,1 , count );
        reverse[indexes[count]] = 0;
        reverse[indexes[1]] = 1;
        count--;
        shiftDown(1);
        return ret;
    }
    private void shiftDown(int k) {
        //子节点：左---2i；右---2i+1
        //int maxIndex=0, left = 2*root;
        while(2*k <= count) {
            int left = 2*k,right  =2*k+1;
            if(right<= count && data[indexes[left]].compareTo(data[indexes[right]]) > 0) {
                left=left+1;
            }
            if(data[indexes[k]].compareTo(data[indexes[left]])<= 0) break;
            swap(indexes,k,left);
            reverse[indexes[k]] = k;
            reverse[indexes[left]] = left;
            k = left;
        }
    }
    public Item getMin(){
        assert( count > 0 );
        return data[indexes[1]];
    }
    public boolean contain( int index ){
        return reverse[index+1] != 0;
    }
    public boolean isEmpty() {
        return count == 0;
    }
    public int getSize() {
        return count;
    }
    Item getItem( int index ){
        assert( contain(index) );
        return data[index+1];
    }

    public void swap(int[] arr, int i, int j) {
        int temp;
        temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    void change( int index , Item newItem ){
        assert( contain(index) );
        index += 1;
        data[index] = newItem;

        shiftUp( reverse[index] );
        shiftDown( reverse[index] );
    }
}
