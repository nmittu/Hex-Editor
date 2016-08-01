package com.nickapps.hexeditor.util;

import java.util.ArrayList;

public class Dictionary3 <X, Y, Z> {
	private final ArrayList<Tuple3<X, Y, Z>> data = new ArrayList<>();
	
	public Tuple3<X, Y, Z> get(int i){
		return data.get(i);
	}
	public X getKey(int i){
		return data.get(i).get0();
	}
	public Tuple<Y, Z> getValues(int i){
		return new Tuple<>(data.get(i).get1(), data.get(i).get2());
	}
	
	public void set(int i, Tuple3<X, Y, Z> tuple){
		data.set(i, tuple);
	}
	public void setValues(int i, Tuple<Y, Z> values){
		data.set(i, new Tuple3<>(data.get(i).get0(), values.get0(), values.get1()));
	}
	
	public void add(Tuple3<X, Y, Z> tuple){
		data.add(tuple);
	}
	
	public void remove(int i){
		data.remove(i);
	}
	
	public int size(){
		return data.size();
	}
	
	public String toString(){
		String ret = "";
		for(Tuple3<X, Y, Z> tuple : data){
			ret += tuple.toString() + "\n";
		}
		return ret;
	}
}
