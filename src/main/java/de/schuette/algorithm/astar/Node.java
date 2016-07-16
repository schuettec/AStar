package de.schuette.algorithm.astar;

public class Node implements Comparable<Node> {

	@Override
	public String toString() {
		return "Node [x=" + x + ", y=" + y + ", priority=" + priority + "]";
	}

	protected int x;
	protected int y;
	protected int priority;
	protected int g;

	protected Node next;

	public Node(int[] coords, int priority) {
		super();
		this.x = coords[0];
		this.y = coords[1];
		this.priority = priority;
	}

	public Node(int x, int y, int priority) {
		super();
		this.x = x;
		this.y = y;
		this.priority = priority;
	}

	public boolean isAt(int[] coords) {
		Util.checkCoords(coords);
		return getX() == coords[0] && getY() == coords[1];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	protected int[] getCoordinates() {
		return new int[] { x, y };
	}

	protected int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = x;
	}

	protected int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = y;
	}

	protected int getPriority() {
		return priority;
	}

	protected void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public int compareTo(Node o) {
		if (o.priority < this.priority) {
			return 1;
		} else if (o.priority == this.priority) {
			return 0;
		} else {
			return -1;
		}
	}

	protected Node getNext() {
		return next;
	}

	protected void setNext(Node next) {
		this.next = next;
	}

	protected int getG() {
		return g;
	}

	protected void setG(int g) {
		this.g = g;
	}

}
