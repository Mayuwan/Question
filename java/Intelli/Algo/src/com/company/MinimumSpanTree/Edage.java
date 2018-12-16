package com.company.MinimumSpanTree;

public class Edage<W extends Comparable> implements Comparable{
	
	private int from;
	private int end;
	private W weight;
	
	public Edage(int a, int b, W weight) {
		this.from = a;
		this.end = b;
		this.weight = weight;
	
	}
	
	public int other(int v) {
		if(!(v == from || v == end)) {throw new IllegalArgumentException("IllegalArgumentException"+v);}
		return v==from ? end : from;
	}

	@Override
	public int compareTo(Object o) {
		Edage edage = (Edage) o;
		if(weight.compareTo(edage.weight) <0 ){ return -1; }
		else if(weight.compareTo(edage.weight) >0 ){return 1;}
		else{return 0;}
	}

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setWeight(W weight) {
		this.weight = weight;
	}

	public W getWeight() {
		return weight;
	}

}
