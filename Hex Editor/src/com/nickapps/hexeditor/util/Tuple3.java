package com.nickapps.hexeditor.util;

public class Tuple3<X, Y, Z> {
	private final X x;
	private final Y y;
	private final Z z;

	public Tuple3(X x, Y y, Z z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public X get0() {
		return x;
	}

	public Y get1() {
		return y;
	}

	public Z get2() {
		return z;
	}

	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
