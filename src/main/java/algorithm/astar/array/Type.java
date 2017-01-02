package algorithm.astar.array;

import static algorithm.astar.array.Util.*;

public class Type {
	public static final Type FREE = new Type(utf8(0x2219));
	public static final Type OBSTACLE = new Type(utf8(0x2588));
	public static final Type START = new Type(utf8(0x263a));
	public static final Type END = new Type(utf8(0x263B));
	public static final Type WAY = new Type(utf8(0x2117));
	public static final Type OPTIMAL = new Type(utf8(0x2665));

	protected Object value;

	public Type(char... c) {
		this.value = new String(c);
	}

	public Type(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}

}
