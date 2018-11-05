package com.company.Graph;
import java.io.*;
public class Edage<T> {
	
	private int a;
	private int b;
	private T weight;
	
	public Edage(int a, int b,T weight) {
		this.a = a;
		this.b = b;
		this.weight = weight;
	
	}
	
	public int other(int v) {
		if(!(v == a || v == b)) {throw new IllegalArgumentException("�����ڸýڵ�"+v);}
		return v==a ? b : a;
	}
	
	public T getWeight() {
		return weight;
	}

}
